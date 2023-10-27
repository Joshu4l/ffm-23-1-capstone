package de.groupsethero.backend.service;
import de.groupsethero.backend.exceptions.GeolocationRetrievalException;
import de.groupsethero.backend.models.Geolocation;
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


    public Double calculateAverageElevationInPercent(List<Geolocation> geolocationSubset) {

        // case geolocationSubset is null -> potential NullPointerException

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
        if (totalDistance == 0 || geolocationSubset.isEmpty()) {
            return null;
        }
        return (averageElevation / totalDistance) * 100;
    }



}


