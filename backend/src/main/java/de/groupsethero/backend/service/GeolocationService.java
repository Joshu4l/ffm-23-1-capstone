package de.groupsethero.backend.service;
import de.groupsethero.backend.GeolocationException;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.repository.GeolocationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GeolocationService {

    private final GeolocationRepo geolocationRepo;


    public List<Geolocation> getAllGeolocations() throws GeolocationException {
        try {
            return geolocationRepo.findAll();
        } catch (Exception e) {
            throw new GeolocationException(e.getMessage());
        }
    }

    public Geolocation getGeolocationById(String id) throws NoSuchElementException{
            return geolocationRepo.findById(id).orElseThrow();
    }

    public Geolocation createGeolocation(Geolocation geolocation) throws DataAccessException {
        return geolocationRepo.save(geolocation);
    }
}


