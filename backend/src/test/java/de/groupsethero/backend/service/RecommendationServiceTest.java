package de.groupsethero.backend.service;
import de.groupsethero.backend.models.Recommendation;
import de.groupsethero.backend.models.RecommendationRequestDTO;
import de.groupsethero.backend.repository.RecommendationRepo;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecommendationServiceTest {

    RecommendationRepo recommendationRepo = mock(RecommendationRepo.class);
    RecommendationService recommendationService = new RecommendationService(recommendationRepo);


    // CALCULATE GROUPSET RECOMMENDATIONS
    @Test
    void calculateGroupsetRecommendation_givenRecommendationRequestDTO_expectCompleteRecommendation() throws Exception{

        // GIVEN
        RecommendationRequestDTO recommendationRequestDTO = new RecommendationRequestDTO();
        recommendationRequestDTO.setUserlocationId("myUserlocationId");
        recommendationRequestDTO.setAreaDesignation("myAreaDesignation");
        recommendationRequestDTO.setAverageElevationInPercent(4.00);

        when(recommendationRepo.save(
                any(Recommendation.class)
        )).thenReturn(new Recommendation(
                "1234",
                "myUserlocationId",
                "myArea",
                4.00,
                "moderate",
                List.of(53,39),
                11,
                32
                )
        );

        // WHEN
        String actualElevationInterpretation = recommendationService.getElevationInterpretation(
                recommendationRequestDTO.getAverageElevationInPercent()
                );

        List<Integer> actualCranksetDimensions = recommendationService.getCranksetDimensionsRecommendation(
                recommendationRequestDTO.getAverageElevationInPercent()
                );

        int actualLargestSprocket = recommendationService.getLargestSprocketRecommendation(
                recommendationRequestDTO.getAverageElevationInPercent()
                );

        Recommendation actualRecommendation = recommendationService.calculateGroupsetRecommendation(
                recommendationRequestDTO.getUserlocationId(),
                recommendationRequestDTO.getAreaDesignation(),
                recommendationRequestDTO.getAverageElevationInPercent()
                );


        // THEN
        String expectedElevationInterpretation = "moderate";
        List<Integer> expectedCranksetDimensions = List.of(53, 39);
        int expectedLargestSprocket = 32;
        Recommendation expectedRecommendation = new Recommendation(
                "1234",
                "myUserlocationId",
                "myArea",
                4.00,
                "moderate",
                List.of(53,39),
                11,
                32
        );

        verify(recommendationRepo).save(any());
        assertEquals(expectedElevationInterpretation, actualElevationInterpretation);
        assertEquals(expectedCranksetDimensions, actualCranksetDimensions);
        assertEquals(expectedLargestSprocket, actualLargestSprocket);
        assertEquals(expectedRecommendation, actualRecommendation);
    }


    // GET ELEVATION INTERPRETATION
    @Test
    void getElevationInterpretation_givenAverageElevationOf1Percent_expectFlat() throws Exception {
        // GIVEN
        double inputAverageElevationInPercent = 1.00;

        // WHEN
        String actualElevationInterpretation = recommendationService.getElevationInterpretation(
                inputAverageElevationInPercent
        );

        // THEN
        String expectedElevationInterpretation = "flat";
        assertEquals(expectedElevationInterpretation, actualElevationInterpretation);
    }
    @Test
    void getElevationInterpretation_givenAverageElevationOf4Percent_expectModerate() throws Exception {
        // GIVEN
        double inputAverageElevationInPercent = 4.00;

        // WHEN
        String actualElevationInterpretation = recommendationService.getElevationInterpretation(
                inputAverageElevationInPercent
        );

        // THEN
        String expectedElevationInterpretation = "moderate";
        assertEquals(expectedElevationInterpretation, actualElevationInterpretation);
    }
    @Test
    void getElevationInterpretation_givenAverageElevationOf7Percent_expectRelativelySteep() throws Exception {
        // GIVEN
        double inputAverageElevationInPercent = 7.00;

        // WHEN
        String actualElevationInterpretation = recommendationService.getElevationInterpretation(
                inputAverageElevationInPercent
        );

        // THEN
        String expectedElevationInterpretation = "relatively steep";
        assertEquals(expectedElevationInterpretation, actualElevationInterpretation);
    }
    @Test
    void getElevationInterpretation_givenAverageElevationOf11Percent_expectSteep() throws Exception {
        // GIVEN
        double inputAverageElevationInPercent = 11.00;

        // WHEN
        String actualElevationInterpretation = recommendationService.getElevationInterpretation(
                inputAverageElevationInPercent
        );

        // THEN
        String expectedElevationInterpretation = "steep";
        assertEquals(expectedElevationInterpretation, actualElevationInterpretation);
    }
    @Test
    void getElevationInterpretation_givenAverageElevationOf16Percent_expectVerySteep() throws Exception {
        // GIVEN
        double inputAverageElevationInPercent = 16.00;

        // WHEN
        String actualElevationInterpretation = recommendationService.getElevationInterpretation(
                inputAverageElevationInPercent
        );

        // THEN
        String expectedElevationInterpretation = "very steep";
        assertEquals(expectedElevationInterpretation, actualElevationInterpretation);
    }


    // GET CRANKSET DIMENSIONS
    @Test
    void getCranksetDimensionsRecommendation_givenAverageElevationOf1Percent_expect53And39() throws Exception {

        // GIVEN
        double inputAverageElevationInPercent = 1.00;

        // WHEN
        List<Integer> actualCranksetDimensions = recommendationService.getCranksetDimensionsRecommendation(
                inputAverageElevationInPercent
        );

        // THEN
        List<Integer> expectedCranksetDimensions = List.of(53, 39);
        assertEquals(expectedCranksetDimensions, actualCranksetDimensions);
    }
    @Test
    void getCranksetDimensionsRecommendation_givenAverageElevationOf4Percent_expect53And39() throws Exception {

        // GIVEN
        double inputAverageElevationInPercent = 4.00;

        // WHEN
        List<Integer> actualCranksetDimensions = recommendationService.getCranksetDimensionsRecommendation(
                inputAverageElevationInPercent
        );

        // THEN
        List<Integer> expectedCranksetDimensions = List.of(53, 39);
        assertEquals(expectedCranksetDimensions, actualCranksetDimensions);
    }
    @Test
    void getCranksetDimensionsRecommendation_givenAverageElevationOf7Percent_expect52And36() throws Exception {

        // GIVEN
        double inputAverageElevationInPercent = 7.00;

        // WHEN
        List<Integer> actualCranksetDimensions = recommendationService.getCranksetDimensionsRecommendation(
                inputAverageElevationInPercent
        );

        // THEN
        List<Integer> expectedCranksetDimensions = List.of(52, 36);
        assertEquals(expectedCranksetDimensions, actualCranksetDimensions);
    }
    @Test
    void getCranksetDimensionsRecommendation_givenAverageElevationOf11Percent_expect52And36() throws Exception {

        // GIVEN
        double inputAverageElevationInPercent = 11.00;

        // WHEN
        List<Integer> actualCranksetDimensions = recommendationService.getCranksetDimensionsRecommendation(
                inputAverageElevationInPercent
        );

        // THEN
        List<Integer> expectedCranksetDimensions = List.of(52, 36);
        assertEquals(expectedCranksetDimensions, actualCranksetDimensions);
    }
    @Test
    void getCranksetDimensionsRecommendation_givenAverageElevationOf16Percent_expect50And34() throws Exception {

        // GIVEN
        double inputAverageElevationInPercent = 16.00;

        // WHEN
        List<Integer> actualCranksetDimensions = recommendationService.getCranksetDimensionsRecommendation(
                inputAverageElevationInPercent
        );

        // THEN
        List<Integer> expectedCranksetDimensions = List.of(50, 34);
        assertEquals(expectedCranksetDimensions, actualCranksetDimensions);
    }


    // GET LARGEST SPROCKET
    @Test
    void getLargestSprocketRecommendation_givenAverageElevationOf1Percent_expect28() throws Exception {

        // GIVEN
        double inputAverageElevationInPercent = 1.00;

        // WHEN
        int actualLargestSprocket = recommendationService.getLargestSprocketRecommendation(
                inputAverageElevationInPercent
        );

        // THEN
        int expectedLargestSprocket = 28;
        assertEquals(expectedLargestSprocket, actualLargestSprocket);

    }
    @Test
    void getLargestSprocketRecommendation_givenAverageElevationOf4Percent_expect32() throws Exception {

        // GIVEN
        double inputAverageElevationInPercent = 4.00;

        // WHEN
        int actualLargestSprocket = recommendationService.getLargestSprocketRecommendation(
                inputAverageElevationInPercent
        );

        // THEN
        int expectedLargestSprocket = 32;
        assertEquals(expectedLargestSprocket, actualLargestSprocket);

    }
    @Test
    void getLargestSprocketRecommendation_givenAverageElevationOf7Percent_expect34() throws Exception {

        // GIVEN
        double inputAverageElevationInPercent = 7.00;

        // WHEN
        int actualLargestSprocket = recommendationService.getLargestSprocketRecommendation(
                inputAverageElevationInPercent
        );

        // THEN
        int expectedLargestSprocket = 34;
        assertEquals(expectedLargestSprocket, actualLargestSprocket);

    }
    @Test
    void getLargestSprocketRecommendation_givenAverageElevationOf11Percent_expect36() throws Exception {

        // GIVEN
        double inputAverageElevationInPercent = 11.00;

        // WHEN
        int actualLargestSprocket = recommendationService.getLargestSprocketRecommendation(
                inputAverageElevationInPercent
        );

        // THEN
        int expectedLargestSprocket = 36;
        assertEquals(expectedLargestSprocket, actualLargestSprocket);

    }
    @Test
    void getLargestSprocketRecommendation_givenAverageElevationOf16Percent_expect40() throws Exception {

        // GIVEN
        double inputAverageElevationInPercent = 16.00;

        // WHEN
        int actualLargestSprocket = recommendationService.getLargestSprocketRecommendation(
                inputAverageElevationInPercent
        );

        // THEN
        int expectedLargestSprocket = 40;
        assertEquals(expectedLargestSprocket, actualLargestSprocket);

    }


    // GET RECOMMENDATION BY ID
    @Test
    void getGroupsetRecommendationById_givenValidId_expectRecommendationObject() throws Exception {

        // GIVEN
        String validId = "1234";

        when(recommendationRepo.findById(validId)).thenReturn(
                Optional.of(new Recommendation(
                    "1234",
                    "myUserlocationId",
                    "myArea",
                    4.00,
                    "moderate",
                    List.of(53, 39),
                    11,
                    32
                ))
        );

        // WHEN
        Recommendation actualRecommendation = recommendationService.getGroupsetRecommendationById(validId);

        // THEN
        Recommendation expectedRecommendation = new Recommendation(
                "1234",
                "myUserlocationId",
                "myArea",
                4.00,
                "moderate",
                List.of(53, 39),
                11,
                32
        );
        verify(recommendationRepo).findById(validId);
        assertEquals(expectedRecommendation, actualRecommendation);
    }

}