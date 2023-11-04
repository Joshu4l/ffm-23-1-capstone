package de.groupsethero.backend.service;
import de.groupsethero.backend.exceptions.RadiusInKmTooSmallException;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.models.Userlocation;
import de.groupsethero.backend.models.UserlocationDTO;
import de.groupsethero.backend.repository.UserlocationRepo;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class UserlocationService {

    private final UserlocationRepo userlocationRepo;
    private final GeolocationService geolocationService;


    // CREATE
    public Userlocation createUserlocation(UserlocationDTO userlocationDTO) throws RadiusInKmTooSmallException {

        List<Double> queryBoundaries = geolocationService.defineQueryBoundaries(userlocationDTO);
        List<Geolocation> databaseSubset = geolocationService.getGeolocationSubsetToBeQueried(
                queryBoundaries.get(0),
                queryBoundaries.get(1),
                queryBoundaries.get(2),
                queryBoundaries.get(3)
        );
        double calculatedAverageElevationInPercent = geolocationService.calculateAverageElevationInPercent(
                databaseSubset
        );

        Userlocation newUserlocation = Userlocation.builder()

                .latitude( userlocationDTO.getLatitude() )
                .longitude( userlocationDTO.getLongitude() )
                .radiusInKm( userlocationDTO.getRadiusInKm() )
                .averageElevationInPercent( calculatedAverageElevationInPercent )
                .areaDesignation( userlocationDTO.getAreaDesignation() )
                .userName( userlocationDTO.getUserName() )
                .build();

        return userlocationRepo.save(newUserlocation);
    }


    // GET ALL
    public List<Userlocation> getAllUserlocations() {
        return userlocationRepo.findAll();
    }


    // GET BY ID
    public Userlocation getUserlocationById(String id) throws NoSuchElementException {
        return userlocationRepo.findById(id).orElseThrow();
    }


    // DELETE BY ID
    public String deleteUserlocationById(String id) throws NoSuchElementException{
        if (userlocationRepo.existsById(id)) {
            userlocationRepo.deleteById(id);
            return "userlocation with id " + id + " successfully deleted.";
        } else {
            throw new NoSuchElementException("Non-existent id");
        }
    }

}
