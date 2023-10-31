package de.groupsethero.backend.service;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.models.Userlocation;
import de.groupsethero.backend.models.UserlocationDTO;
import de.groupsethero.backend.repository.UserlocationRepo;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.List;

class UserlocationServiceUnitTest {

    UserlocationRepo userlocationRepo = mock(UserlocationRepo.class);
    GeolocationService geolocationService = mock(GeolocationService.class);
    UserlocationService userlocationService = new UserlocationService(userlocationRepo, geolocationService);

    UserlocationDTO userlocationDTO = new UserlocationDTO(47.3, 6.11, 0, "area 51", "josh");


    // CREATE
    @Test
    void createUserlocation_givenUserlocationDTO_expectCompleteUserlocation() throws Exception{

        // GIVEN (Mocking the GeolocationService's and UserlocationRepo's behaviour)
        when(geolocationService.defineQueryBoundaries(
                userlocationDTO
        )).thenReturn(List.of(47.3, 47.3, 6.11, 6.11));

        when(geolocationService.getGeolocationSubsetToBeQueried(
                anyDouble(), anyDouble(), anyDouble(), anyDouble()
        )).thenReturn(
                List.of(new Geolocation(47.3, 6.11, 362.39)));

        when(geolocationService.calculateAverageElevationInPercent(
                anyList()
        )).thenReturn(666.00);

        when(userlocationRepo.save(
                any(Userlocation.class)
        )).thenReturn(new Userlocation(
                "myId",
                47.3,
                6.11,
                0,
                "area 51",
                "josh",
                666.00
                )
            );

        // WHEN
        Userlocation actual = userlocationService.createUserlocation(userlocationDTO);
        // THEN
        Userlocation expected = new Userlocation(
                "myId",
                // TODO: hier Werte nochmal explizit händisch
                userlocationDTO.getLatitude(),
                userlocationDTO.getLongitude(),
                userlocationDTO.getRadiusInKm(),
                userlocationDTO.getAreaDesignation(),
                userlocationDTO.getUserName(),
                666.00
        );
        verify(geolocationService).defineQueryBoundaries(userlocationDTO);
        // TODO: hier nochmal alle gemockten Aufrufe verifyen
        assertEquals(expected, actual);
    }


    // GET ALL
    @Test
    void getAllUserlocations_givenEmptyDB_expectEmptyResultList() {
        // GIVEN
        List<Userlocation> userlocationList = List.of();
        when(userlocationRepo.findAll()
        ).thenReturn(userlocationList);

        // WHEN
        List<Userlocation> actual = userlocationService.getAllUserlocations();

        // WHEN
        List<Userlocation> expected = List.of();
        verify(userlocationRepo).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getAllUserlocations_givenPopulatedDB_expectListOfMultipleUserlocationObjects() {
        // GIVEN
        List<Userlocation> userlocationList = List.of(
                new Userlocation("my1stId", 53.5653, 9.9527, 51, "my 1st area", "josh", 0.30),
                new Userlocation("my2ndId", 53.5653, 9.9527, 52, "my 2nd area", "josh", 0.40),
                new Userlocation("my3rdId", 53.5616, 9.9484, 53, "my 3rd area", "josh", 0.50)
        );
        when(userlocationRepo.findAll()
        ).thenReturn(userlocationList);

        // WHEN
        List<Userlocation> actual = userlocationService.getAllUserlocations();

        // WHEN
        List<Userlocation> expected = List.of(
                new Userlocation("my1stId", 53.5653, 9.9527, 51, "my 1st area", "josh", 0.30),
                new Userlocation("my2ndId", 53.5653, 9.9527, 52, "my 2nd area", "josh", 0.40),
                new Userlocation("my3rdId", 53.5616, 9.9484, 53, "my 3rd area", "josh", 0.50)
        );
        verify(userlocationRepo).findAll();
        assertEquals(expected, actual);
    }

}










