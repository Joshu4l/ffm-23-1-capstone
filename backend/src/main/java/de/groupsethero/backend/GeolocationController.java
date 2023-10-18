package de.groupsethero.backend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class GeolocationController {

    private final GeolocationService geolocationService;

    public GeolocationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GetMapping("/geolocations")
    @ResponseStatus(HttpStatus.OK)
    public List<Geolocation> getAllGeolocations() {
        return geolocationService.findAllGeolocations();
    }

    @PostMapping("/geolocations")
    @ResponseStatus(HttpStatus.CREATED)
    public Geolocation createGeolocation(@RequestBody Geolocation geolocation) {
        return geolocationService.createGeolocation(geolocation);
    }

    @GetMapping("/populate")
    @ResponseStatus(HttpStatus.CREATED)
    public void populate () {
        geolocationService.createGeolocationsFromJsonFile();
    }


}
