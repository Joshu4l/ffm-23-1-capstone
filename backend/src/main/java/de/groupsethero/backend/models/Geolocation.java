package de.groupsethero.backend.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@Validated
public class Geolocation {

    //ATTRIBUTES
    @Id
    private String id;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    @NotNull
    private double elevation;


    // CONSTRUCTORS
    public Geolocation() {
        // Default Constructor
    }

    public Geolocation(double latitude, double longitude, double elevation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }


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
