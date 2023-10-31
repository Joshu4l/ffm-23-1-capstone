package de.groupsethero.backend.service;
import de.groupsethero.backend.exceptions.RadiusInKmTooSmallException;
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

    public Userlocation createUserlocation(UserlocationDTO userlocationDTO) throws RadiusInKmTooSmallException {

        List<Double> queryBoundaries = geolocationService.defineQueryBoundaries(userlocationDTO);
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

    public List<Userlocation> getAllUserlocations() {
        return userlocationRepo.findAll();
    }
}
