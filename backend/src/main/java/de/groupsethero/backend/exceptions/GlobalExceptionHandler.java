package de.groupsethero.backend.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // EXCEPTION HANDLING
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException() {
        return "IllegalArgumentException: Invalid coordinates. Longitude values must be between -90 and 90. Latitude values must be between -180 and 180.";
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException() {
        return "Nothing here - The location specified doesn't seem to exist";
    }

    @ExceptionHandler(GeolocationRetrievalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeolocationRetrievalException() {
        return "Request cannot be served at this time.\n" +
                "You may try to narrow down either your request criteria or the desired number of results";
    }

    @ExceptionHandler(RadiusInKmTooSmallException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRadiusInKmTooSmallException() {
        return "Request cannot be served at this time.\n" +
               "The specified radius for your location seems to be too small for calculating plausible results.";
    }


}
