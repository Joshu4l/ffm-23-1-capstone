package de.groupsethero.backend.service;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.models.Userlocation;
import de.groupsethero.backend.models.UserlocationDTO;
import de.groupsethero.backend.repository.UserlocationRepo;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserlocationService {

    private final UserlocationRepo userlocationRepo;
    private final GeolocationService geolocationService;

    public Userlocation createUserlocation(UserlocationDTO userlocationDTO) {

        List<Double> queryBoundaries = defineBoundaries(userlocationDTO);
        List<Geolocation> databaseSubset = geolocationService.getGeolocationSubsetToBeQueried(
                queryBoundaries.get(0),
                queryBoundaries.get(1),
                queryBoundaries.get(2),
                queryBoundaries.get(3)
        );
        double calculatedAverageElevationInPercent = geolocationService.calculateAverageElevationInPercent(
                databaseSubset
        );

        Userlocation newUserlocation = Userlocation.builder()

                .latitude( userlocationDTO.getLatitude() )
                .longitude( userlocationDTO.getLongitude() )
                .radiusInKm( userlocationDTO.getRadiusInKm() )
                .averageElevationInPercent( calculatedAverageElevationInPercent )
                .areaDesignation( userlocationDTO.getAreaDesignation() )
                .userName( userlocationDTO.getUserName() )
                .build();

        return userlocationRepo.save(newUserlocation);
    }


    public List<Double> defineBoundaries (UserlocationDTO userlocationDTO) {

        // User's input location parameters
        double inputLatitude = userlocationDTO.getLatitude();
        double inputLongitude = userlocationDTO.getLongitude();
        double inputDistance = userlocationDTO.getRadiusInKm();
        // necessary constant value
        double earthRadius = 6371;

        // Angles in degrees for the diagonal crossing the input point by cardinal direction
        double angleNortheast = 45;  // upper right end on the map grid (North-East)
        double angleSouthwest = 225; // lower left end on the map grid (South-West)

        // Resulting boundary points (numeric)
        double latRadian = Math.toRadians(inputLatitude);
        double lngRadian = Math.toRadians(inputLongitude);
        double angleNortheastRad = Math.toRadians(angleNortheast);
        double angleSouthwestRad = Math.toRadians(angleSouthwest);

        // Farthest North-Eastern coordinate
        double latNE = Math.asin(
                                 Math.sin(latRadian) * Math.cos(inputDistance / earthRadius)
                                 + Math.cos(latRadian) * Math.sin(inputDistance / earthRadius) * Math.cos(angleNortheastRad)
                                 );

        double lngNE = lngRadian + Math.atan2(
                                              Math.sin(angleNortheastRad) * Math.sin(inputDistance / earthRadius) * Math.cos(latRadian),
                                              Math.cos(inputDistance / earthRadius) - Math.sin(latRadian) * Math.sin(latNE)
                                              );
        // Farthest South-Western coordinate
        double latSW = Math.asin(
                                 Math.sin(latRadian) * Math.cos(inputDistance / earthRadius)
                                 + Math.cos(latRadian) * Math.sin(inputDistance / earthRadius) * Math.cos(angleSouthwestRad)
                                 );
        double lngSW = lngRadian + Math.atan2(
                                              Math.sin(angleSouthwestRad) * Math.sin(inputDistance / earthRadius) * Math.cos(latRadian),
                                              Math.cos(inputDistance / earthRadius) - Math.sin(latRadian) * Math.sin(latSW)
                                              );

        // converted back into degrees
        double lowerLatitudeBoundary = Math.toDegrees(latSW);
        double upperLatitudeBoundary = Math.toDegrees(latNE);
        double lowerLongitudeBoundary = Math.toDegrees(lngSW);
        double upperLongitudeBoundary = Math.toDegrees(lngNE);

        return List.of(
                lowerLatitudeBoundary,
                upperLatitudeBoundary,
                lowerLongitudeBoundary,
                upperLongitudeBoundary
        );
    }

}
