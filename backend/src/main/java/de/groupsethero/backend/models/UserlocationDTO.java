package de.groupsethero.backend.models;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class UserlocationDTO {

    private double latitude;
    private double longitude;
    private int radiusInKm;
    private String areaDesignation;
    private String userName;
}
