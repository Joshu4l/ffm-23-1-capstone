package de.groupsethero.backend.service;
import de.groupsethero.backend.models.CranksetDimensionsEnum;
import de.groupsethero.backend.models.LargestSprocketEnum;
import de.groupsethero.backend.models.Recomm;
import de.groupsethero.backend.models.Recommendation;
import de.groupsethero.backend.repository.RecommRepo;
import de.groupsethero.backend.repository.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.abs;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepo recommendationRepo;
    private final RecommRepo recommRepo;

    public Recommendation createRecommendation(Recommendation recommendation) {
        return recommendationRepo.save(recommendation);
    }



    public String getElevationInterpretation(double averageElevationInPercent) {

        if ( abs(averageElevationInPercent ) <= 3.00 ) {
            return "flat";
        } else if ( abs(averageElevationInPercent) > 3.00 && abs(averageElevationInPercent) <= 6.00 ) {
            return "moderate";
        } else if ( abs(averageElevationInPercent) > 6.00 && abs(averageElevationInPercent) <= 10.00 ) {
            return "relatively steep";
        } else if ( abs(averageElevationInPercent) > 10.00 && abs(averageElevationInPercent) <= 15.00 ) {
            return "steep";
        } else {
            return "very steep";
        }
    }

    public List<Integer> getCranksetDimensionsRecommendation(double averageElevationInPercent) {

        if ( abs(averageElevationInPercent ) <= 3.00 ) {
            System.out.println("crankset recommendation for terrain evaluation 'flat': \n" +
                    "outer chainring teeth: " + CranksetDimensionsEnum.VALUE_53_39.getOuterChainringTeeth() +
                    ", inner chainring teeth: " + CranksetDimensionsEnum.VALUE_53_39.getInnerChainringTeeth()
            );
            return List.of( CranksetDimensionsEnum.VALUE_53_39.getOuterChainringTeeth(), CranksetDimensionsEnum.VALUE_50_34.getInnerChainringTeeth());

        } else if ( abs(averageElevationInPercent) > 3.00 && abs(averageElevationInPercent) <= 6.00 ) {
            System.out.println("crankset recommendation for terrain evaluation 'moderate': \n" +
                    "outer chainring teeth: " + CranksetDimensionsEnum.VALUE_53_39.getOuterChainringTeeth() +
                    ", inner chainring teeth: " + CranksetDimensionsEnum.VALUE_53_39.getInnerChainringTeeth()
            );
            return List.of(CranksetDimensionsEnum.VALUE_53_39.getOuterChainringTeeth(), CranksetDimensionsEnum.VALUE_53_39.getInnerChainringTeeth());

        } else if ( abs(averageElevationInPercent) > 6.00 && abs(averageElevationInPercent) <= 10.00 ) {
            System.out.println("crankset recommendation for terrain evaluation 'relatively steep': \n" +
                    "outer chainring teeth: " + CranksetDimensionsEnum.VALUE_52_36.getOuterChainringTeeth() +
                    ", inner chainring teeth: " + CranksetDimensionsEnum.VALUE_52_36.getInnerChainringTeeth()
            );
            return List.of(CranksetDimensionsEnum.VALUE_52_36.getOuterChainringTeeth(), CranksetDimensionsEnum.VALUE_52_36.getInnerChainringTeeth());

        } else if ( abs(averageElevationInPercent) > 10.00 && abs(averageElevationInPercent) <= 15.00 ) {
            System.out.println("crankset recommendation for terrain evaluation 'steep': \n" +
                    "outer chainring teeth: " + CranksetDimensionsEnum.VALUE_52_36.getOuterChainringTeeth() +
                    ", inner chainring teeth: " + CranksetDimensionsEnum.VALUE_52_36.getInnerChainringTeeth()
            );
            return List.of(CranksetDimensionsEnum.VALUE_52_36.getOuterChainringTeeth(), CranksetDimensionsEnum.VALUE_52_36.getInnerChainringTeeth());

        } else {
            System.out.println("crankset recommendation for terrain evaluation 'very steep': \n" +
                    "outer chainring teeth: " + CranksetDimensionsEnum.VALUE_50_34.getOuterChainringTeeth() +
                    ", inner chainring teeth: " + CranksetDimensionsEnum.VALUE_50_34.getInnerChainringTeeth()
            );
            return List.of(CranksetDimensionsEnum.VALUE_50_34.getOuterChainringTeeth(), CranksetDimensionsEnum.VALUE_50_34.getInnerChainringTeeth());
        }
    }

    public int getLargestSprocketRecommendation(double averageElevationInPercent) {

        if ( abs(averageElevationInPercent ) <= 3.00 ) {
            System.out.println("sprocket options for terrain evaluation 'flat': " + LargestSprocketEnum.VALUE_28.getValue());
            return LargestSprocketEnum.VALUE_28.getValue();

        } else if ( abs(averageElevationInPercent) > 3.00 && abs(averageElevationInPercent) <= 6.00 ) {
            System.out.println("sprocket options for terrain evaluation 'moderate': " + LargestSprocketEnum.VALUE_32.getValue());
            return LargestSprocketEnum.VALUE_32.getValue();

        } else if ( abs(averageElevationInPercent) > 6.00 && abs(averageElevationInPercent) <= 10.00 ) {
            System.out.println("sprocket options for terrain evaluation 'relatively steep': " + LargestSprocketEnum.VALUE_34.getValue());
            return LargestSprocketEnum.VALUE_34.getValue();

        } else if ( abs(averageElevationInPercent) > 10.00 && abs(averageElevationInPercent) <= 15.00 ) {
            System.out.println("sprocket options for terrain evaluation 'steep': " + LargestSprocketEnum.VALUE_36.getValue());
            return LargestSprocketEnum.VALUE_36.getValue();

        } else {
            System.out.println("sprocket options for terrain evaluation 'very steep': " + LargestSprocketEnum.VALUE_40.getValue());
            return LargestSprocketEnum.VALUE_40.getValue();
        }

    }

    public Recomm getGroupsetRecommendation(String userlocationId, double averageElevationInPercent) {

        Recomm newRecomm = Recomm.builder()

                .userlocationId(
                        userlocationId
                )
                .averageElevationInPercent(
                        averageElevationInPercent
                )
                .cranksetDimensions(
                        getCranksetDimensionsRecommendation(averageElevationInPercent)
                )
                .largestSprocket(
                        getLargestSprocketRecommendation(averageElevationInPercent)
                )
                .build();

        return recommRepo.save(newRecomm);
    }
}