package de.neuefische.backend.controller;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.MovieAndSeriesWatched;
import de.neuefische.backend.model.OMDb.OmdbOverview;
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
}
