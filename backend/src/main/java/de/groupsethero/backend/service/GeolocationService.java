package de.groupsethero.backend.service;
import de.groupsethero.backend.exceptions.GeolocationRetrievalException;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.repository.GeolocationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class GeolocationService {

    private final GeolocationRepo geolocationRepo;
    private static final double EARTH_RADIUS = 6371;


    public List<Geolocation> getAllGeolocations() throws GeolocationRetrievalException {
        try {
            return geolocationRepo.findAll();
        } catch (Exception e) {
            throw new GeolocationRetrievalException(e.getMessage());
        }
    }

    public Geolocation getGeolocationById(String id) throws NoSuchElementException {
            return geolocationRepo.findById(id).orElseThrow();
    }


    public List<Geolocation> getGeolocationSubsetToBeQueried
            (double lowerLatitudeBoundary, double upperLatitudeBoundary, double lowerLongitudeBoundary, double upperLongitudeBoundary)
    {
        return geolocationRepo.findByLatitudeBetweenAndLongitudeBetween(
                lowerLatitudeBoundary, upperLatitudeBoundary,
                lowerLongitudeBoundary, upperLongitudeBoundary
        );
    }


    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dlat = Math.toRadians(lat2 - lat1);
        double dlon = Math.toRadians(lon2 - lon1);

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public Double calculateAverageElevationInPercent(double latitude, double longitude, double radius, List<Geolocation> geolocationSubset) {

        double totalElevation = 0;
        for (Geolocation point : geolocationSubset) {
            totalElevation += point.getElevation();
        }

        double averageElevation = totalElevation / geolocationSubset.size();
        double totalDistance = 0;

        for (int i = 1; i < geolocationSubset.size(); i++) {
            Geolocation prevPoint = geolocationSubset.get(i - 1);
            Geolocation currentPoint = geolocationSubset.get(i);

            double dist = haversine(prevPoint.getLatitude(), prevPoint.getLongitude(),
                    currentPoint.getLatitude(), currentPoint.getLongitude());

            totalDistance += dist;
        }
        if (totalDistance == 0) {
            return null;
        }
        return (averageElevation / totalDistance) * 100;
    }

}


