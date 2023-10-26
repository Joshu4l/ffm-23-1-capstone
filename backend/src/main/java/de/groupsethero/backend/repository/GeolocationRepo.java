package de.groupsethero.backend.repository;
import de.groupsethero.backend.models.Geolocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface GeolocationRepo extends MongoRepository<Geolocation, String> {

    List<Geolocation> findByLatitudeBetweenAndLongitudeBetween(
            double lowerLatitudeBoundary, double upperLatitudeBoundary,
            double lowerLongitudeBoundary, double upperLongitudeBoundary
    );

}
