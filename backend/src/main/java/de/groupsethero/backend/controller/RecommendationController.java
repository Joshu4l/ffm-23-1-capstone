package de.groupsethero.backend.controller;
import de.groupsethero.backend.models.Recommendation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RecommendationController {

    @PostMapping("/recommendations")
    @ResponseStatus(HttpStatus.OK)
    public Recommendation postRecommendation(@RequestBody Recommendation recommendation) {

        System.out.println("Received value: " + recommendation.toString());
        return recommendation;
    }
}