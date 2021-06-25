package de.neuefische.backend.controller;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    private final WatchlistService watchlistService;

    @Autowired
    public WatchlistController(WatchlistService watchlistService){
        this.watchlistService = watchlistService;
    }

    @GetMapping
    public List<OmdbOverview> getWatchlistByType(@RequestParam Optional<String> type){
        return watchlistService.getWatchlistByType(type);
    }

    @PostMapping
    public OmdbOverview addToWatchlist(@RequestBody MovieAndSeries itemToAdd){
        return watchlistService.addToWatchlist(itemToAdd);
    }

    @DeleteMapping
    public void removeFromWatchlist(@RequestBody String imdbID){
        watchlistService.removeFromWatchlist(imdbID);
    }

    @GetMapping("/name")
    public String getRecommendedByFromWatchlist(@RequestParam String imdbID){
        return watchlistService.getNameFromWatchlist(imdbID);
    }

    @PostMapping("/name")
    public String addNameToWatchlist(@RequestBody MovieAndSeries itemToAdd){
        return watchlistService.addNameToWatchlist(itemToAdd);
    }

    @DeleteMapping("/name")
    public void removeNameFromWatchlist(@RequestParam String imdbID){
        watchlistService.removeNameFromWatchlist(imdbID);
    }
}
