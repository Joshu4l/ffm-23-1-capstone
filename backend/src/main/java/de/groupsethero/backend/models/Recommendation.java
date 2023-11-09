package de.groupsethero.backend.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {

    @Id
    private String id;
    private String userlocationId;
    private String areaDesignation;

    private double averageElevationInPercent;
    private String elevationInterpretation;

    private List<Integer> cranksetDimensions;

    private int smallestSprocket = 11;
    private int largestSprocket;
}
