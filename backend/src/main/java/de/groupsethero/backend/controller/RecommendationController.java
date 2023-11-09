package de.groupsethero.backend.controller;
import de.groupsethero.backend.models.Recomm;
import de.groupsethero.backend.models.Recommendation;
import de.groupsethero.backend.models.RecommendationRequestDTO;
import de.groupsethero.backend.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/recommendations")
    @ResponseStatus(HttpStatus.CREATED)
    public Recommendation postRecommendation(@RequestBody Recommendation recommendation) {
        return recommendationService.createRecommendation(recommendation);
    }

    @GetMapping("/recommendations/initial")
    @ResponseStatus(HttpStatus.CREATED)
    public Recomm getGroupsetRecommendation(@RequestBody RecommendationRequestDTO recommendationRequestDTO) {
        return recommendationService.getGroupsetRecommendation(
                recommendationRequestDTO.getUserlocationId(),
                recommendationRequestDTO.getAverageElevationInPercent()
        );
    }

    @PostMapping("/recommendations/sprocket")
    @ResponseStatus(HttpStatus.OK)
    public Integer getLargestSprocketRecommendation (@RequestBody double averageElevationInPercent) {
        return recommendationService.getLargestSprocketRecommendation(averageElevationInPercent);
    }

    @PostMapping("/recommendations/crankset")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getCranksetDimensionsRecommendation (@RequestBody double averageElevationInPercent) {
        return recommendationService.getCranksetDimensionsRecommendation(averageElevationInPercent);
    }

    /*    @PostMapping("/recommendations/optimum")
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getOptimalConfiguration(@RequestBody double averageElevationInPercent) {
        return recommendationService.getLargestSprocketOptions(averageElevationInPercent);
    }*/

}