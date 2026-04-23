package br.com.fiap.recommendationservice.controller;

import br.com.fiap.recommendationservice.dto.RecommendationDTO;
import br.com.fiap.recommendationservice.service.AIRecommendationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final AIRecommendationService recommendationService;

    public RecommendationController(AIRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/user/{userId}")
    public RecommendationDTO getRecommendations(@PathVariable Long userId) {
        return recommendationService.getAIRecommendations(userId);
    }
}
