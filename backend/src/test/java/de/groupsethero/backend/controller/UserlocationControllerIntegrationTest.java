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


    // GET BY ID
    @Test
    @DirtiesContext
    void getUserlocationById_givenValidId_expectValidUserlocationObject() throws Exception {
        // GIVEN
        Userlocation singleEntry = new Userlocation(
            "1234",
            53.56542916701823,
            9.952696889811424,
            50,
            "my area",
            "josh",
            0.31170052270624937
        );
        userlocationRepo.save(singleEntry);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/userlocations/" + "1234"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                    {
                        "id": "1234",
                        "latitude": 53.56542916701823,
                        "longitude": 9.952696889811424,
                        "radiusInKm": 50,
                        "areaDesignation": "my area",
                        "userName": "josh",
                        "averageElevationInPercent": 0.31170052270624937
                    }
                    """)
                );
    }
    @Test
    @DirtiesContext
    void getUserlocationById_givenInvalidId_expectNoSuchElementException() throws Exception {
        // GIVEN n.a. in this case

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/userlocations/" + "quatschId"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(
                        "Nothing here - The location specified doesn't seem to exist"
                ));

    }


    // DELETE BY ID
    @Test
    @DirtiesContext
    void deleteUserlocationById_givenValidId_expectDeleteSuccessMessage() throws Exception {

        // GIVEN
        String validId = "1234";
        Userlocation singleEntry = new Userlocation(
                "1234",
                53.56542916701823,
                9.952696889811424,
                50,
                "my area",
                "josh",
                0.31170052270624937
        );
        userlocationRepo.save(singleEntry);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/userlocations/" + validId))
                // THEN
                .andExpect(status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().string(
                    "userlocation with id 1234 successfully deleted."
                ));
    }

    @Test
    @DirtiesContext
    void deleteUserlocationById_givenInvalidId_expectNoSuchElementException() throws Exception {
        // GIVEN
        String invalidId = "quatschId";
        Userlocation singleEntry = new Userlocation(
                "1234",
                53.56542916701823,
                9.952696889811424,
                50,
                "my area",
                "josh",
                0.31170052270624937
        );
        userlocationRepo.save(singleEntry);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/userlocations/" + invalidId))
            // THEN
            .andExpect(status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string(
                "Nothing here - The location specified doesn't seem to exist"
            ));
    }


    // UPDATE BY ID
    @Test
    @DirtiesContext
    void updateUserlocationById_givenValidIdAndBody_expectUpdatedUserlocationObject() throws Exception {
        // GIVEN
        geolocationRepo.save(new Geolocation(47.3, 6.1, 380.01));
        geolocationRepo.save(new Geolocation(47.3, 6.11, 362.39));
        geolocationRepo.save(new Geolocation(47.3, 6.12, 340.44));
        geolocationRepo.save(new Geolocation(47.31, 6.1, 351.22));
        geolocationRepo.save(new Geolocation(47.31, 6.11, 364.52));
        geolocationRepo.save(new Geolocation(47.31, 6.12, 430.18));

        String validId = "1234";
        Userlocation prevUserlocation = new Userlocation("1234",
                47.3,
                6.11,
                3,
                "area 2",
                "josh",
                6330.767975035719
        );
        userlocationRepo.save(prevUserlocation);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/userlocations/" + validId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "latitude": 47.3,
                            "longitude": 6.11,
                            "radiusInKm": 2,
                            "areaDesignation": "area 2 renamed",
                            "userName": "josh"
                        }
                        """)
                )
                // THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """         
                        {
                            "latitude": 47.3,
                            "longitude": 6.11,
                            "radiusInKm": 2,
                            "areaDesignation": "area 2 renamed",
                            "userName": "josh",
                            "averageElevationInPercent": 7596.823213198377
                        }
                        """)
                );
    }
}