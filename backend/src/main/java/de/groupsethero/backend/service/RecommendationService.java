package de.groupsethero.backend.service;
import de.groupsethero.backend.models.CranksetDimensionsEnum;
import de.groupsethero.backend.models.LargestSprocketEnum;
import de.groupsethero.backend.models.Recommendation;
import de.groupsethero.backend.repository.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static java.lang.Math.abs;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepo recommendationRepo;


    public Recommendation calculateGroupsetRecommendation(String userlocationId, String areaDesignation, double averageElevationInPercent) {

        Recommendation newRecommendation = Recommendation.builder()
                .userlocationId(
                        userlocationId
                )
                .areaDesignation(
                        areaDesignation
                )
                .averageElevationInPercent(
                        averageElevationInPercent
                )
                .elevationInterpretation(
                        getElevationInterpretation(averageElevationInPercent)
                )
                .cranksetDimensions(
                        getCranksetDimensionsRecommendation(averageElevationInPercent)
                )
                .smallestSprocket(
                        11
                )
                .largestSprocket(
                        getLargestSprocketRecommendation(averageElevationInPercent)
                )
                .build();
        return recommendationRepo.save(newRecommendation);
    }


    public String getElevationInterpretation(double averageElevationInPercent) {

        if (abs(averageElevationInPercent) <= 3.00) {
            return "flat";
        } else if (abs(averageElevationInPercent) > 3.00 && abs(averageElevationInPercent) <= 6.00) {
            return "moderate";
        } else if (abs(averageElevationInPercent) > 6.00 && abs(averageElevationInPercent) <= 10.00) {
            return "relatively steep";
        } else if (abs(averageElevationInPercent) > 10.00 && abs(averageElevationInPercent) <= 15.00) {
            return "steep";
        } else {
            return "very steep";
        }
    }
    public List<Integer> getCranksetDimensionsRecommendation(double averageElevationInPercent) {

        if (abs(averageElevationInPercent) <= 3.00) {
            return List.of(CranksetDimensionsEnum.VALUE_53_39.getOuterChainringTeeth(), CranksetDimensionsEnum.VALUE_53_39.getInnerChainringTeeth());

        } else if (abs(averageElevationInPercent) > 3.00 && abs(averageElevationInPercent) <= 6.00) {
            return List.of(CranksetDimensionsEnum.VALUE_53_39.getOuterChainringTeeth(), CranksetDimensionsEnum.VALUE_53_39.getInnerChainringTeeth());

        } else if (abs(averageElevationInPercent) > 6.00 && abs(averageElevationInPercent) <= 10.00) {
            return List.of(CranksetDimensionsEnum.VALUE_52_36.getOuterChainringTeeth(), CranksetDimensionsEnum.VALUE_52_36.getInnerChainringTeeth());

        } else if (abs(averageElevationInPercent) > 10.00 && abs(averageElevationInPercent) <= 15.00) {
            return List.of(CranksetDimensionsEnum.VALUE_52_36.getOuterChainringTeeth(), CranksetDimensionsEnum.VALUE_52_36.getInnerChainringTeeth());

        } else {
            return List.of(CranksetDimensionsEnum.VALUE_50_34.getOuterChainringTeeth(), CranksetDimensionsEnum.VALUE_50_34.getInnerChainringTeeth());
        }
    }
    public int getLargestSprocketRecommendation(double averageElevationInPercent) {

        if (abs(averageElevationInPercent) <= 3.00) {
            return LargestSprocketEnum.VALUE_28.getValue();

        } else if (abs(averageElevationInPercent) > 3.00 && abs(averageElevationInPercent) <= 6.00) {
            return LargestSprocketEnum.VALUE_32.getValue();

        } else if (abs(averageElevationInPercent) > 6.00 && abs(averageElevationInPercent) <= 10.00) {
            return LargestSprocketEnum.VALUE_34.getValue();

        } else if (abs(averageElevationInPercent) > 10.00 && abs(averageElevationInPercent) <= 15.00) {
            return LargestSprocketEnum.VALUE_36.getValue();

        } else {
            return LargestSprocketEnum.VALUE_40.getValue();
        }

    }


    public Recommendation getGroupsetRecommendationById(String id) {
        return recommendationRepo.findById(id).orElseThrow();
    }

}

