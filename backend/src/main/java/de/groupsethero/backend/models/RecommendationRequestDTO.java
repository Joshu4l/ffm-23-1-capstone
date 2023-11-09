package de.groupsethero.backend.models;
import lombok.Data;

@Data
public class RecommendationRequestDTO {
    private String userlocationId;
    private double averageElevationInPercent;
}