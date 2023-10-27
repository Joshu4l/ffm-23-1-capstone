package de.groupsethero.backend.controller;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.exceptions.GeolocationRetrievalException;
import de.groupsethero.backend.service.GeolocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GeolocationController {

    // SERVICE CLASS'S DEPENDENCY INJECTION
    private final GeolocationService geolocationService;


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


}
