package de.groupsethero.backend.repository;
import de.groupsethero.backend.models.Userlocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserlocationRepo extends MongoRepository<Userlocation, String> {
    // DEFAULT
}
