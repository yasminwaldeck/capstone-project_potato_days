package de.neuefische.backend.service;

import de.neuefische.backend.model.ImdbID;
import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OmdbOverview;
import de.neuefische.backend.repo.WatchlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<OmdbOverview> getWatchlistByType(String type){
        return watchlistRepo.findByType(type)
                .stream()
                .map(item -> omdbApiService.getOverviewById(item.getImdbID()))
                .collect(Collectors.toList());
    }

    public MovieAndSeries addToWatchlist(MovieAndSeries itemToAdd){
        return watchlistRepo.save(itemToAdd);
    }

    public void removeFromWatchlist(String imdbId){
        watchlistRepo.deleteByImdbID(imdbId);
    }
}
