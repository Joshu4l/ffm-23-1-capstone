package de.groupsethero.backend.repository;

import de.groupsethero.backend.models.Recomm;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommRepo extends MongoRepository<Recomm, String> {
}
