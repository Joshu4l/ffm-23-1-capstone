package de.groupsethero.backend.service;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.models.UserlocationDTO;
import java.util.ArrayList;
import java.util.List;

class SlopeCalculator {

    private static final double EARTH_RADIUS = 6371; // Radius der Erde in Kilometern

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dlat = Math.toRadians(lat2 - lat1);
        double dlon = Math.toRadians(lon2 - lon1);

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public static Double calculateAverageSlope(double latitude, double longitude, double radius, List<Geolocation> pointList) {

        List<Geolocation> pointsInRange = new ArrayList<>();

        for (Geolocation point : pointList) {
            double dist = haversine(latitude, longitude, point.getLatitude(), point.getLongitude());

            if (dist <= radius) {
                pointsInRange.add(point);
            }
        }

        if (pointsInRange.isEmpty()) {
            return null;
        }

        double totalElevation = 0;

        for (Geolocation point : pointsInRange) {
            totalElevation += point.getElevation();
        }

        double averageElevation = totalElevation / pointsInRange.size();
        double totalDistance = 0;

        for (int i = 1; i < pointsInRange.size(); i++) {
            Geolocation prevPoint = pointsInRange.get(i - 1);
            Geolocation currentPoint = pointsInRange.get(i);

            double dist = haversine(prevPoint.getLatitude(), prevPoint.getLongitude(),
                    currentPoint.getLatitude(), currentPoint.getLongitude());

            totalDistance += dist;
        }
        if (totalDistance == 0) {
            return null;
        }

        return (averageElevation / totalDistance) * 100;
    }


    public static void main(String[] args) {
        UserlocationDTO inputPoint = UserlocationDTO.builder()
                .userName("josh")
                .locationDescription("my first test location")
                .latitude(49.83)
                .longitude(10.73)
                .radiusInKM(50)
                .build();

        List<Geolocation> pointList = new ArrayList<>();
        //Geolocation-Liste mittels 'getAllGeolocationsWithinSpecifiedRadius'-Methode heranziehen

        SlopeCalculator.calculateAverageSlope(
                inputPoint.getLatitude(),
                inputPoint.getLongitude(),
                inputPoint.getRadiusInKM(),

                pointList
        );

    }

}
