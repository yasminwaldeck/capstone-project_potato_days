package de.neuefische.backend.service;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.repo.WatchlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchlistService {
    private WatchlistRepo watchlistRepo;
    private OmdbApiService omdbApiService;

    @Autowired
    public WatchlistService(WatchlistRepo watchlistRepo, OmdbApiService omdbApiService) {
        this.watchlistRepo = watchlistRepo;
        this.omdbApiService = omdbApiService;
    }

    public List<OmdbOverview> getWatchlistByType(Optional<String> type){
        if(type.isEmpty()){
            return watchlistRepo.findAll().stream()
                    .map(item -> omdbApiService.getOverviewById(item.getImdbID()))
                    .collect(Collectors.toList());
        }
        return watchlistRepo.findByType(type.get())
                .stream()
                .map(item -> omdbApiService.getOverviewById(item.getImdbID()))
                .collect(Collectors.toList());
    }

    public OmdbOverview addToWatchlist(MovieAndSeries itemToAdd){
        watchlistRepo.save(itemToAdd);
        return omdbApiService.getOverviewById(itemToAdd.getImdbID());
    }

    public void removeFromWatchlist(String imdbId){
        watchlistRepo.deleteByImdbID(imdbId);
    }
}
