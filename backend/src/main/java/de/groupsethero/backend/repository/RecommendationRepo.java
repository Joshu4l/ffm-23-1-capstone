package de.groupsethero.backend.repository;

import de.groupsethero.backend.models.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RecommendationRepo extends MongoRepository<Recommendation, String> {

    Optional<Recommendation> findByUserlocationIdAndAreaDesignationAndAverageElevationInPercent(
            String userlocationId,
            String areaDesignation,
            double averageElevationInPercent
    );

}
