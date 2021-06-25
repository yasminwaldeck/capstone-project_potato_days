package de.neuefische.backend.service;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.repo.MovieAndSeriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchHistoryService {

    private final MovieAndSeriesRepo movieAndSeriesRepo;
    private final OmdbApiService omdbApiService;

    @Autowired
    public WatchHistoryService(MovieAndSeriesRepo movieAndSeriesRepo, OmdbApiService omdbApiService) {
        this.movieAndSeriesRepo = movieAndSeriesRepo;
        this.omdbApiService = omdbApiService;
    }

    public OmdbOverview addToWatchHistory(MovieAndSeries itemToAdd){
        MovieAndSeries movieAndSeries = movieAndSeriesRepo.findByImdbID(itemToAdd.getImdbID());
        if(movieAndSeries == null) {
            itemToAdd.setWatchHistory(true);
            movieAndSeriesRepo.save(itemToAdd);
        } else {
            movieAndSeries.setWatchHistory(true);
            movieAndSeriesRepo.save(movieAndSeries);
        }
        return omdbApiService.getOverview(movieAndSeriesRepo.findByImdbID(itemToAdd.getImdbID()));
    }

    public void removeFromWatchHistory(String imdbId){
        MovieAndSeries movieAndSeries = movieAndSeriesRepo.findByImdbID(imdbId);
        if(movieAndSeries != null) {
            if (!movieAndSeries.isWatchlist()) {
                movieAndSeriesRepo.deleteByImdbID(imdbId);
            } else {
                movieAndSeries.setWatchHistory(false);
                movieAndSeriesRepo.save(movieAndSeries);
            }
        }
    }

    public List<OmdbOverview> getWatchHistoryByType(Optional<String> type){
        if(type.isEmpty()){
            return movieAndSeriesRepo.findMovieAndSeriesByWatchHistoryIsTrue().stream()
                    .map(item -> omdbApiService.getOverview(item))
                    .collect(Collectors.toList());
        }
        return movieAndSeriesRepo.findMovieAndSeriesByWatchHistoryIsTrueAndType(type.get())
                .stream()
                .map(item -> omdbApiService.getOverview(item))
                .collect(Collectors.toList());
    }

}
