package de.groupsethero.backend.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geolocation {

    //ATTRIBUTES
    private double latitude;
    private double longitude;
    private double elevation;

    //CUSTOM METHODS
    public String convertToDegreeMinuteSecondFormat() {

        String inputLatitudeCardinalDirection = (latitude >= 0) ? "N" : "S";
        String inputLongitudeCardinalDirection = (longitude >= 0) ? "E" : "W";

        double inputLatitude = Math.abs(latitude);
        double inputLongitude = Math.abs(longitude);

        long latDegrees = (long) inputLatitude;
        long latMinutes = (long) ((inputLatitude - latDegrees) * 60);
        long latSeconds = Math.round((inputLatitude - latDegrees - latMinutes / 60.0) * 3600);

        long lonDegrees = (long) inputLongitude;
        long lonMinutes = (long) ((inputLongitude - lonDegrees) * 60);
        long lonSeconds = Math.round((inputLongitude - lonDegrees - lonMinutes / 60.0) * 3600);

        return String.format("Latitude: %d°%d'%d\"%s, Longitude: %d°%d'%d\"%s",
                latDegrees, latMinutes, latSeconds, inputLatitudeCardinalDirection,
                lonDegrees, lonMinutes, lonSeconds, inputLongitudeCardinalDirection);
    }

    // GETTERS, SETTERS AND OVERRIDE METHODS AUTOMATICALLY CREATED BY LOMBOK'S @Data ANNOTATION

}
