package de.neuefische.backend.controller;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.OmdbDetails;
import de.neuefische.backend.model.Random;
import de.neuefische.backend.model.Stats;
import de.neuefische.backend.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public List<OmdbDetails> getWatchlistByType(Principal principal, @RequestParam Optional<String> type){
        String username = principal.getName();
        return watchlistService.getWatchlistByType(username, type);
    }

    @PostMapping
    public OmdbDetails addToWatchlist(Principal principal, @RequestBody MovieAndSeries itemToAdd){
        String username = principal.getName();
        return watchlistService.addToWatchlist(username, itemToAdd);
    }

    @DeleteMapping
    public void removeFromWatchlist(Principal principal, @RequestBody String imdbID){
        String username = principal.getName();
        watchlistService.removeFromWatchlist(username, imdbID);
    }

    @GetMapping("/name")
    public String getRecommendedByFromWatchlist(Principal principal, @RequestParam String imdbID){
        String username = principal.getName();
        return watchlistService.getNameFromWatchlist(username, imdbID);
    }

    @GetMapping("/name/stats")
    public List<Stats> getRecommendedByStats(Principal principal){
        String username = principal.getName();
        return watchlistService.getRecommendedByStats(username);
    }

    @PostMapping("/name")
    public String addNameToWatchlist(Principal principal, @RequestBody MovieAndSeries itemToAdd){
        String username = principal.getName();
        return watchlistService.addNameToWatchlist(username, itemToAdd);
    }

    @DeleteMapping("/name")
    public void removeNameFromWatchlist(Principal principal, @RequestParam String imdbID){
        String username = principal.getName();
        watchlistService.removeNameFromWatchlist(username, imdbID);
    }

    @GetMapping("/random")
    public Random getRandomWatchlistEntry(Principal principal){
        String username = principal.getName();
        return watchlistService.getRandomWatchlistEntry(username);
    }

}
