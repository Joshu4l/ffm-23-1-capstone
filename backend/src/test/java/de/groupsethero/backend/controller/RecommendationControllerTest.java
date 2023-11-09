package de.groupsethero.backend.controller;
import de.groupsethero.backend.models.Recommendation;
import de.groupsethero.backend.models.RecommendationRequestDTO;
import de.groupsethero.backend.repository.RecommendationRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RecommendationControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    RecommendationRepo recommendationRepo;


    @Test
    @DirtiesContext
    void calculateGroupsetRecommendation() throws Exception{

        // GIVEN
        RecommendationRequestDTO recommendationRequestDTO = new RecommendationRequestDTO();
        recommendationRequestDTO.setUserlocationId("myUserlocationId");
        recommendationRequestDTO.setAreaDesignation("myAreaDesignation");
        recommendationRequestDTO.setAverageElevationInPercent(4.00);

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/api/recommendations/calculate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("""
                    {
                        "userlocationId": "myUserlocationId",
                        "areaDesignation": "myAreaDesignation",
                        "averageElevationInPercent": 4.00
                    }
                    """)
                )
                // THEN
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(
                        """         
                        {
                        "userlocationId": "myUserlocationId",
                        "areaDesignation": "myAreaDesignation",
                        "averageElevationInPercent": 4.00,
                        "elevationInterpretation": "moderate",
                        "cranksetDimensions": [53, 39],
                        "smallestSprocket": 11,
                        "largestSprocket": 32
                        }
                        """)
                );
    }

    @Test
    @DirtiesContext
    void getGroupsetRecommendationById() throws Exception {

        // GIVEN
        String validId = "1234";

        Recommendation singleEntry = recommendationRepo.save(new Recommendation(
                "1234",
                "myUserlocationId",
                "myAreaDesignation",
                4.00,
                "moderate",
                List.of(53, 39),
                11,
                32
        ));

        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/recommendations/" + validId))
                // THEN
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """         
                        {
                        "userlocationId": "myUserlocationId",
                        "areaDesignation": "myAreaDesignation",
                        "averageElevationInPercent": 4.00,
                        "elevationInterpretation": "moderate",
                        "cranksetDimensions": [53, 39],
                        "smallestSprocket": 11,
                        "largestSprocket": 32
                        }
                        """)
                );
    }

}