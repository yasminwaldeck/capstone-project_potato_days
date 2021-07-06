package de.neuefische.backend.controller;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.OmdbDetails;
import de.neuefische.backend.model.Episode;
import de.neuefische.backend.model.Random;
import de.neuefische.backend.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/watchhistory")
public class WatchHistoryController {
    private final WatchHistoryService watchHistoryService;

    @Autowired
    public WatchHistoryController(WatchHistoryService watchHistoryService) {
        this.watchHistoryService = watchHistoryService;
    }

    @GetMapping
    public List<OmdbDetails> getWatchHistoryByType(Principal principal, @RequestParam Optional<String> type){
        String username = principal.getName();
        return watchHistoryService.getWatchHistoryByType(username, type);
    }

    @PostMapping
    public OmdbDetails addToWatchHistory(Principal principal, @RequestBody MovieAndSeries itemToAdd){
        String username = principal.getName();
        return watchHistoryService.addToWatchHistory(username, itemToAdd);
    }

    @DeleteMapping
    public void removeFromWatchHistory(Principal principal, @RequestBody String imdbID){
        String username = principal.getName();
        watchHistoryService.removeFromWatchHistory(username, imdbID);
    }

    @GetMapping("/episode/{imdbId}/season/{season}")
    public List<Episode> getWatchHistoryEpisodes(Principal principal, @PathVariable String imdbId, @PathVariable String season){
        String username = principal.getName();
        return watchHistoryService.getWatchHistoryEpisodes(username, imdbId, season);
    }

    @GetMapping("/episode/{imdbId}/{id}/season/{season}/progress")
    public float getWatchHistorySeasonProgress(Principal principal, @PathVariable String imdbId, @PathVariable String id, @PathVariable String season){
        String username = principal.getName();
        return watchHistoryService.getWatchHistorySeasonProgress(username, imdbId, id, season);
    }

    @GetMapping("/episode/{imdbId}/{id}/progress")
    public float getWatchHistoryTotalProgress(Principal principal, @PathVariable String imdbId, @PathVariable String id){
        String username = principal.getName();
        return watchHistoryService.getWatchHistoryTotalProgress(username, imdbId, id);
    }

    @PostMapping("/episode")
    public Episode addToWatchHistoryEpisodes(Principal principal, @RequestBody Episode episode){
        String username = principal.getName();
        return watchHistoryService.addToWatchHistoryEpisodes(username, episode);
    }

    @DeleteMapping("/episode")
    public void removeFromWatchHistoryEpisodes(Principal principal, @RequestBody Episode episode){
        String username = principal.getName();
        watchHistoryService.removeFromWatchHistoryEpisodes(username, episode);
    }

    @GetMapping("/random")
    public Random getRandomWatchingEntry(Principal principal){
        String username = principal.getName();
        return watchHistoryService.getRandomWatchingEntry(username);
    }
}
