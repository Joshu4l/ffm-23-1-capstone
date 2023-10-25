package de.groupsethero.backend.models;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserlocationDTO {

    private double latitude;
    private double longitude;
    private int radiusInKM;
    private String areaDesignation;
    private String userName;
}
