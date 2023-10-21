package de.groupsethero.backend.models;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GeolocationTest {

    @Test
    void convertToDegreeMinuteSecondFormat_expectDegreeMinuteSecondString() {

        // GIVEN
        Geolocation sampleGeolocation = new Geolocation(51.89, 9.09, 1234);

        // WHEN
        String actual = sampleGeolocation.convertToDegreeMinuteSecondFormat();

        // THEN
        String expected = "Latitude: 51°53'24\"N, Longitude: 9°5'24\"E";
        Assertions.assertEquals(expected, actual);
    }

}