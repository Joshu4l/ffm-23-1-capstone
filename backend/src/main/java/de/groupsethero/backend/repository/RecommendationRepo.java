package de.groupsethero.backend.repository;

import de.groupsethero.backend.models.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendationRepo extends MongoRepository<Recommendation, String> {


}
