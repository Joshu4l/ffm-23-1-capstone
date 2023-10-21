package de.groupsethero.backend.models;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputGeolocation {

    private double latitude;
    private double longitude;

}
