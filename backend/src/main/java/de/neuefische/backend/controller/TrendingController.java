package de.neuefische.backend.controller;

import de.neuefische.backend.model.OMDb.OmdbDetails;
import de.neuefische.backend.service.TrendingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trending")
public class TrendingController {
    private TrendingService trendingService;

    public TrendingController(TrendingService trendingService) {
        this.trendingService = trendingService;
    }

    @GetMapping
    public List<OmdbDetails> findTrending(String timewindow, String type){
        return trendingService.findTrending(timewindow, type);
    }
}
