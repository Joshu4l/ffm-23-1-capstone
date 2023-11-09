package de.groupsethero.backend.controller;
import de.groupsethero.backend.models.Recommendation;
import de.groupsethero.backend.models.RecommendationRequestDTO;
import de.groupsethero.backend.service.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class RecommendationController {

    private final RecommendationService recommendationService;


    @PostMapping("/recommendations/calculate")
    @ResponseStatus(HttpStatus.CREATED)
    public Recommendation calculateGroupsetRecommendation(@RequestBody RecommendationRequestDTO recommendationRequestDTO) {

        return recommendationService.calculateGroupsetRecommendation(
                recommendationRequestDTO.getUserlocationId(),
                recommendationRequestDTO.getAreaDesignation(),
                recommendationRequestDTO.getAverageElevationInPercent()
        );
    }

    @GetMapping("/recommendations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Recommendation getGroupsetRecommendationById(@PathVariable String id) {

        return recommendationService.getGroupsetRecommendationById(id);
    }


}