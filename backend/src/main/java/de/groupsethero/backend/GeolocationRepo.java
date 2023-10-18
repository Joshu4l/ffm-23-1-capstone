package de.groupsethero.backend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeolocationRepo extends MongoRepository<Geolocation, String> {

}
