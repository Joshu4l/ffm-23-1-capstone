package de.groupsethero.backend.service;
import de.groupsethero.backend.exceptions.GeolocationInsertException;
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


    public List<Geolocation> getAllGeolocations() throws GeolocationRetrievalException {
        try {
            return geolocationRepo.findAll();
        } catch (Exception e) {
            throw new GeolocationRetrievalException(e.getMessage());
        }
    }

    public Geolocation getGeolocationById(String id) throws NoSuchElementException{
            return geolocationRepo.findById(id).orElseThrow();
    }

    public Geolocation createGeolocation(Geolocation geolocation) throws GeolocationInsertException {
        return geolocationRepo.save(geolocation);
    }
}


