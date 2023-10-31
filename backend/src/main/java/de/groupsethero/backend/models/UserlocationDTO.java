package de.groupsethero.backend.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@AllArgsConstructor
@With
public class UserlocationDTO {

    private double latitude;
    private double longitude;
    private int radiusInKm;
    private String areaDesignation;
    private String userName;

}

