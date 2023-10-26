package de.groupsethero.backend.controller;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.exceptions.GeolocationRetrievalException;
import de.groupsethero.backend.service.GeolocationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@ControllerAdvice
@RequestMapping("/api")
public class GeolocationController {

    // SERVICE CLASS'S DEPENDENCY INJECTION
    private final GeolocationService geolocationService;
    public GeolocationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    // APIs & REQUEST MAPPINGS
    @GetMapping("/geolocations")
    @ResponseStatus(HttpStatus.OK)
    public List<Geolocation> getAllGeolocations() throws GeolocationRetrievalException {
        return geolocationService.getAllGeolocations();
    }

    @GetMapping("/geolocations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Geolocation getGeolocationById(@PathVariable String id) throws NoSuchElementException {
        return geolocationService.getGeolocationById(id);
    }



    // EXCEPTION HANDLING
    @ExceptionHandler(GeolocationRetrievalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeolocationRetrievalException() {
        return "Request cannot be served at this time.\n" +
                "You may try to narrow down either your request criteria or the desired number of results";
    }
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException() {
        return "Nothing here - The geolocation specified doesn't seem to exist";
    }

}
