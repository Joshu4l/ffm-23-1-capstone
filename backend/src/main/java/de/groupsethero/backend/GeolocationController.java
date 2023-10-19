package de.groupsethero.backend;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
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
    public List<Geolocation> getAllGeolocations() throws GeolocationException {
        return geolocationService.getAllGeolocations();
    }

    @GetMapping("/geolocations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Geolocation getGeolocationById(@PathVariable String id) throws NoSuchElementException{
        return geolocationService.getGeolocationById(id);
    }

    @PostMapping("/geolocations")
    @ResponseStatus(HttpStatus.CREATED)
    public Geolocation createGeolocation(@RequestBody Geolocation geolocation) throws DataAccessException {
        return geolocationService.createGeolocation(geolocation);
    }



    // EXCEPTION HANDLING
    @ExceptionHandler(GeolocationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeolocationException() {
        return "Request cannot be served at this time.\n" +
                "You may try to narrow down either your request criteria or the desired number of results";
    }
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException() {
        return "Nothing here - The geolocation specified doesn't seem to exist";
    }
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDataAccessException() {
        return "We're sorry - The object cannot not be created appropriately at this time.";
    }

}
