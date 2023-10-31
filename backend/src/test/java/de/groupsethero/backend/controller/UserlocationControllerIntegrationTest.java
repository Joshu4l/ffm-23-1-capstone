package de.groupsethero.backend.controller;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.models.Userlocation;
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


    // CREATE
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


    // GET ALL
    @Test
    @DirtiesContext
    void getAllUserlocations_givenPopulatedDB_expectEmptyResponseList() throws Exception {

        // GIVEN n.a. in this case

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/userlocations"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                """
                        []
                        """)
                );
    }

    @Test
    @DirtiesContext
    void getAllUserlocations_givenPopulatedDB_expectListOfMultipleUserlocationObjects() throws Exception{

        // GIVEN
        userlocationRepo.save(new Userlocation("my1stId", 53.5653, 9.9527, 51, "my 1st area", "josh", 0.30));
        userlocationRepo.save(new Userlocation("my2ndId", 53.5653, 9.9527, 52, "my 2nd area", "josh", 0.40));
        userlocationRepo.save(new Userlocation("my3rdId", 53.5616, 9.9484, 53, "my 3rd area", "josh", 0.50));

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/userlocations"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                            {
                                "latitude": 53.5653,
                                "longitude": 9.9527,
                                "radiusInKm": 51,
                                "areaDesignation": "my 1st area",
                                "userName": "josh"
                            },
                            {
                                "latitude": 53.5653,
                                "longitude": 9.9527,
                                "radiusInKm": 52,
                                "areaDesignation": "my 2nd area",
                                "userName": "josh"
                            },
                            {
                                "latitude": 53.5616,
                                "longitude": 9.9484,
                                "radiusInKm": 53,
                                "areaDesignation": "my 3rd area",
                                "userName": "josh"
                            }
                        ]
                    """)
                );
    }


}