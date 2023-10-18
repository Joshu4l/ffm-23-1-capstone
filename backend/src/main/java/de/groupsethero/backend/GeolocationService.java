package de.groupsethero.backend;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeolocationService {

    private final GeolocationRepo geolocationRepo;

    public List<Geolocation> findAllGeolocations() {
        return geolocationRepo.findAll();
    }

    public Geolocation createGeolocation(Geolocation geolocation) {
        return geolocationRepo.save(geolocation);
    }

    public void createGeolocationsFromJsonFile() {
        try {
            Path path = Paths.get("/Users/joshuaalbert/Desktop/testDbInput.json");
            String jsonFilePath = path.toFile().getAbsolutePath();

            ObjectMapper objectMapper = new ObjectMapper();
            Geolocation[] geolocationsArray = objectMapper.readValue(new File(jsonFilePath), Geolocation[].class);

            List<Geolocation> geolocations = Arrays.asList(geolocationsArray);

            // Save each geolocation to the database
            for (Geolocation geolocation : geolocations) {
                createGeolocation(geolocation);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }

}
