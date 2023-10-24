package de.groupsethero.backend.models;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserlocationDTO {

    private String userName;
    private String locationDescription;
    private double latitude;
    private double longitude;
    private int radiusInKM;
}
