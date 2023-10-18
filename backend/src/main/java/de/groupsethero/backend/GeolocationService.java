package de.groupsethero.backend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeolocationService {

    private final GeolocationRepo geolocationRepo;

    public Geolocation createGeolocation(Geolocation geolocation) {
        return geolocationRepo.save(geolocation);
    }

    public List<Geolocation> findAllGeolocations() {

        return geolocationRepo.findAll();
    }
}
