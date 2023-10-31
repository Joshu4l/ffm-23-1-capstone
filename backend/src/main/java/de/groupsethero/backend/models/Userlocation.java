package de.groupsethero.backend.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
public class Userlocation {

    @Id
    private String id; // additional information in comparison to UserlocationDTO

    private double latitude;
    private double longitude;
    private int radiusInKm;
    private String areaDesignation;
    private String userName;
    
    private double averageElevationInPercent; // additional information in comparison to UserlocationDTO

}
