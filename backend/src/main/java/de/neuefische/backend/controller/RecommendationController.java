package de.neuefische.backend.controller;

import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trending")
public class RecommendationController {
    private RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public List<OmdbOverview> findTrending(String timewindow, String type){
        return recommendationService.findTrending(timewindow, type);
    }
}
