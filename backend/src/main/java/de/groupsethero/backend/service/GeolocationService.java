package de.groupsethero.backend.service;
import de.groupsethero.backend.exceptions.GeolocationRetrievalException;
import de.groupsethero.backend.exceptions.RadiusInKmTooSmallException;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.models.UserlocationDTO;
import de.groupsethero.backend.repository.GeolocationRepo;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class GeolocationService {

    private final GeolocationRepo geolocationRepo;
    private static final double EARTH_RADIUS = 6371;


    // ALL
    public List<Geolocation> getAllGeolocations() throws GeolocationRetrievalException {
        try {
            return geolocationRepo.findAll();
        } catch (Exception e) {
            throw new GeolocationRetrievalException(e.getMessage());
        }
    }

    // BY ID
    public Geolocation getGeolocationById(String id) throws NoSuchElementException {
            return geolocationRepo.findById(id).orElseThrow();
    }


    // QUERY BOUNDARIES
    public List<Double> defineQueryBoundaries(UserlocationDTO userlocationDTO) {

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


    // DB COLLECTION SUBSET
    public List<Geolocation> getGeolocationSubsetToBeQueried
            (double lowerLatitudeBoundary, double upperLatitudeBoundary, double lowerLongitudeBoundary, double upperLongitudeBoundary)
        throws GeolocationRetrievalException
    {
        try {
            return geolocationRepo.findByLatitudeBetweenAndLongitudeBetween(
                    lowerLatitudeBoundary, upperLatitudeBoundary,
                    lowerLongitudeBoundary, upperLongitudeBoundary
            );
        } catch (Exception e) {
            throw new GeolocationRetrievalException(e.getMessage());
        }

    }


    // HAVERSINE
    public double haversineDistance(double lat1, double lon1, double lat2, double lon2) throws IllegalArgumentException {

        if (Math.abs(lat1) > 90 || Math.abs(lat2) > 90 || Math.abs(lon1) > 180 || Math.abs(lon2) > 180) {
            throw new IllegalArgumentException(
                "Invalid coordinates. Longitude values must be between -90 and 90. Latitude values must be between -180 and 180."
            );
        }

        double latDelta = Math.toRadians(lat2 - lat1);
        double lngDelta = Math.toRadians(lon2 - lon1);

        double intermediateResult =
                Math.pow(Math.sin(latDelta / 2), 2)    // sinus of half of the latDelta -> to the power of two
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.pow(Math.sin(lngDelta / 2), 2); // sinus of half of the lngDelta -> to the power of two

        double centralAngle = 2 * Math.atan2(Math.sqrt(intermediateResult), Math.sqrt(1 - intermediateResult));

        return EARTH_RADIUS * centralAngle;
    }


    // AVERAGE ELEVATION
    public Double calculateAverageElevationInPercent(List<Geolocation> geolocationSubset) throws RadiusInKmTooSmallException{

            if (geolocationSubset.size() <= 1 || geolocationSubset.isEmpty()) {
                throw new RadiusInKmTooSmallException(
                    "Request cannot be served at this time.\n" +
                    "The specified radius for your location seems to be too small for calculating plausible results."
                );
            }
            double totalElevation = 0;
            for (Geolocation point : geolocationSubset) {
                totalElevation += point.getElevation();
            }

            double averageElevation = totalElevation / geolocationSubset.size(); // Potential ArithmeticException
            double totalDistance = 0;

            for (int i = 1; i < geolocationSubset.size(); i++) {
                Geolocation prevPoint = geolocationSubset.get(i - 1);
                Geolocation currentPoint = geolocationSubset.get(i);

                double dist = haversineDistance(
                        prevPoint.getLatitude(),
                        prevPoint.getLongitude(),
                        currentPoint.getLatitude(),
                        currentPoint.getLongitude()
                );
                totalDistance += dist;
            }

            if (totalDistance == 0) {
                throw new RadiusInKmTooSmallException(
                        "Request cannot be served at this time.\n" +
                                "The specified radius for your location seems to be too small for calculating plausible results."
                );
            } else {
                return (averageElevation / totalDistance) * 100;
            }
    }

}


