package de.groupsethero.backend;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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

    public void createGeolocationsFromJsonFile(String jsonFilePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Geolocation[] geolocationsArray = objectMapper.readValue(new File(jsonFilePath), Geolocation[].class);

            List<Geolocation> geolocations = Arrays.asList(geolocationsArray);

            // Save each geolocation to the database
            for (Geolocation geolocation : geolocations) {
                createGeolocation(geolocation);
            }

            System.out.println("Geolocations created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }


}
