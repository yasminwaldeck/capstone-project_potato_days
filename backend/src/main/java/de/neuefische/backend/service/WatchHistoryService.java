package de.neuefische.backend.service;

import de.neuefische.backend.model.MovieAndSeriesWatched;
import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.repo.WatchHistoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchHistoryService {

    private final WatchHistoryRepo watchHistoryRepo;
    private final OmdbApiService omdbApiService;

    public WatchHistoryService(WatchHistoryRepo watchHistoryRepo, OmdbApiService omdbApiService) {
        this.watchHistoryRepo = watchHistoryRepo;
        this.omdbApiService = omdbApiService;
    }

    public OmdbOverview addToWatchHistory(MovieAndSeriesWatched itemToAdd){
        watchHistoryRepo.save(itemToAdd);
        return omdbApiService.getOverviewById(itemToAdd.getImdbID());
    }

    public void removeFromWatchHistory(String imdbId){
        watchHistoryRepo.deleteByImdbID(imdbId);
    }

    public List<OmdbOverview> getWatchHistoryByType(Optional<String> type){
        if(type.isEmpty()){
            return watchHistoryRepo.findAll().stream()
                    .map(item -> omdbApiService.getOverviewById(item.getImdbID()))
                    .collect(Collectors.toList());
        }
        return watchHistoryRepo.findByType(type.get())
                .stream()
                .map(item -> omdbApiService.getOverviewById(item.getImdbID()))
                .collect(Collectors.toList());
    }

}
