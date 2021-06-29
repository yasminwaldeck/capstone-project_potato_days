package de.neuefische.backend.controller;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.model.Episode;
import de.neuefische.backend.service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<OmdbOverview> getWatchHistoryType(@RequestParam Optional<String> type){
        return watchHistoryService.getWatchHistoryByType(type);
    }

    @PostMapping
    public OmdbOverview addToWatchHistory(@RequestBody MovieAndSeries itemToAdd){
        return watchHistoryService.addToWatchHistory(itemToAdd);
    }

    @DeleteMapping
    public void removeFromWatchHistory(@RequestBody String imdbID){
        watchHistoryService.removeFromWatchHistory(imdbID);
    }

//    @GetMapping
//    public List<OmdbOverview> getWatchHistorySeason(@RequestParam String season){
//        return watchHistoryService.getWatchHistoryByType(type);
//    }
//
//    @PostMapping
//    public OmdbOverview addToWatchHistorySeason(@RequestBody MovieAndSeries itemToAdd){
//        return watchHistoryService.addToWatchHistory(itemToAdd);
//    }
//
//    @DeleteMapping
//    public void removeFromWatchHistorySeason(@RequestBody String imdbID){
//        watchHistoryService.removeFromWatchHistory(imdbID);
//    }


    @GetMapping
    public List<Episode> getWatchHistoryEpisodes(@RequestParam String imdbId, @RequestParam String season){
        return watchHistoryService.getWatchHistoryEpisodes(imdbId, season);
    }

    @PostMapping
    public Episode addToWatchHistoryEpisodes(@RequestBody Episode episode){
        return watchHistoryService.addToWatchHistoryEpisodes(episode);
    }

    @DeleteMapping
    public void removeFromWatchHistoryEpisodes(@RequestBody Episode episode){
        watchHistoryService.removeFromWatchHistoryEpisodes(episode);
    }
}
