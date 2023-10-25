package de.groupsethero.backend.models;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Userlocation {

    @Id
    private String id;
    private String userName;
    private String areaDesignation;
    private double latitude;
    private double longitude;
    private int radiusInKM;
    private double elevationInPercent;
}
