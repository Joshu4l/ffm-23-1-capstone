package de.groupsethero.backend.controller;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.repository.GeolocationRepo;
import de.groupsethero.backend.repository.UserlocationRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserlocationControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserlocationRepo userlocationRepo;

    @Autowired
    GeolocationRepo geolocationRepo;



    @Test
    @DirtiesContext
    void createUserlocation_givenUserlocationDTO_expectCompleteUserlocation() throws Exception{
    // Happy Path :)
        // GIVEN
        geolocationRepo.save(new Geolocation(47.3, 6.1, 380.01));
        geolocationRepo.save(new Geolocation(47.3, 6.11, 362.39));
        geolocationRepo.save(new Geolocation(47.3, 6.12, 340.44));
        geolocationRepo.save(new Geolocation(47.31, 6.1, 351.22));
        geolocationRepo.save(new Geolocation(47.31, 6.11, 364.52));
        geolocationRepo.save(new Geolocation(47.31, 6.12, 430.18));


        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/userlocations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                    {
                        "latitude": 47.3,
                        "longitude": 6.11,
                        "radiusInKm": 2,
                        "areaDesignation": "area 51",
                        "userName": "josh"
                    }
                    """)
                )
                // THEN
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(
                    """         
                    {

                        "latitude": 47.3,
                        "longitude": 6.11,
                        "radiusInKm": 2,
                        "areaDesignation": "area 51",
                        "userName": "josh",
                        "averageElevationInPercent": 7596.823213198377
                    }
                    """)
                );
    }


    @Test
    @DirtiesContext
    void createUserlocation_givenUserlocationDTOHavingRadiusOfOneOrLessKm_expectRadiusInKmTooSmallException() throws Exception{

        // GIVEN n.a. in this case

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/userlocations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "latitude": 47.3,
                    "longitude": 6.11,
                    "radiusInKm": 0,
                    "areaDesignation": "area 51",
                    "userName": "josh"
                }
                """)
                )
                // THEN
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(
                    "Request cannot be served at this time.\n" +
                    "The specified radius for your location seems to be too small for calculating plausible results."
                    )
                );
    }

}