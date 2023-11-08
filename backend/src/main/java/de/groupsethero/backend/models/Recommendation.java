package de.groupsethero.backend.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {

/*    @Id
    private String id;*/
    private int slidervalue;
}
