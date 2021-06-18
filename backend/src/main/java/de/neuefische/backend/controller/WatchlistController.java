package de.neuefische.backend.controller;

import de.neuefische.backend.model.OmdbOverview;
import de.neuefische.backend.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    private final WatchlistService watchlistService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService){
        this.watchlistService = watchlistService;
    }

    @GetMapping
    public List<OmdbOverview> getWatchlistByType(String type){
        return watchlistService.getWatchlistByType(type);
    }
}
