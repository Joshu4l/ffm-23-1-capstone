package de.groupsethero.backend.controller;
import de.groupsethero.backend.models.Geolocation;
import de.groupsethero.backend.repository.GeolocationRepo;
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
class GeolocationControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    GeolocationRepo geolocationRepo;


    // GET ALL
    @Test
    @DirtiesContext
    void getAllGeolocationsGivenEmptyDB_expectEmptyList() throws Exception {

        // GIVEN -> n.a. here

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/geolocations"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                    []
                """));
    }

    @Test
    @DirtiesContext
    void getAllGeolocationsGivenSingleEntry_expectListOfOneElement() throws Exception {

        // GIVEN
        Geolocation singleEntry = new Geolocation(47.3, 5.9, 238.71);
        geolocationRepo.save(singleEntry);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/geolocations"))
                // THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                    [
                        {
                            "latitude": 47.3,
                            "longitude": 5.9,
                            "elevation": 238.71
                        }
                    ]
                """));
    }

    @Test
    @DirtiesContext
    void getAllGeolocationsGivenMultipleEntries_expectListOfMultipleElements() throws Exception {

        // GIVEN
        Geolocation entry1 = new Geolocation(47.3, 5.9, 238.71);
        Geolocation entry2 = new Geolocation(47.3, 5.91, 240.03);
        Geolocation entry3 = new Geolocation(47.3, 5.92, 239.68);
        geolocationRepo.save(entry1);
        geolocationRepo.save(entry2);
        geolocationRepo.save(entry3);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/geolocations"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                    [
                        {
                            "latitude": 47.3,
                            "longitude": 5.9,
                            "elevation": 238.71
                        },
                        {
                            "latitude": 47.3,
                            "longitude": 5.91,
                            "elevation": 240.03
                        },
                        {
                            "latitude": 47.3,
                            "longitude": 5.92,
                            "elevation": 239.68
                        }
                    ]
                """));
    }

    // TODO: Exceptionfall "GeolocationRetrievalException" !



    // GET BY ID
    @Test
    @DirtiesContext
    void getGeolocationById_expectOneValidReturnObject() throws Exception {

        // GIVEN
        Geolocation singleSavedObject = geolocationRepo.save(
                new Geolocation(47.3, 5.9, 238.71)
        );

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/geolocations/" + singleSavedObject.getId()))
                // THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                    {
                        "latitude": 47.3,
                        "longitude": 5.9,
                        "elevation": 238.71
                    }
                """));
    }

    // TODO: GetById-Exceptionfall "GeolocationRetrievalException" !



    // CREATE
/*    @Test
    @DirtiesContext
    void createGeolocationGivenValidObject_expectOneValidReturnObject() throws Exception {

        // GIVEN
        *//* Preparation steps n.a. in this scenario *//*

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/geolocations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                                "latitude": 47.3,
                                "longitude": 6.11,
                                "elevation": 362.39
                            }
                        """)
                )
                // THEN
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("""
                    {
                        "latitude": 47.3,
                        "longitude": 6.11,
                        "elevation": 362.39
                    }
                """));
    }*/



/*    @Test
    @DirtiesContext
    //TODO: CreateGeolocation-Exceptionfall "GeolocationInsertException"
    void createGeolocationGivenInvalidObject_expectException() throws Exception {

        // GIVEN
        *//* Object preparation n.a. in this scenario *//*

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/geolocations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "latitude": null,
                                "longitude": null
                            }
                        """)
                )
                // THEN
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(
                    "We're sorry - The object cannot not be created at this time."
                ));
    }*/




//DONE
}