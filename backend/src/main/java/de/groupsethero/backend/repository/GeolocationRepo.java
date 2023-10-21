package de.groupsethero.backend.repository;
import de.groupsethero.backend.models.Geolocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeolocationRepo extends MongoRepository<Geolocation, String> {

}
