package de.neuefische.backend.service;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.model.Stats;
import de.neuefische.backend.repo.MovieAndSeriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WatchlistService {
    private MovieAndSeriesRepo movieAndSeriesRepo;
    private OmdbApiService omdbApiService;

    @Autowired
    public WatchlistService(MovieAndSeriesRepo movieAndSeriesRepo, OmdbApiService omdbApiService) {
        this.movieAndSeriesRepo = movieAndSeriesRepo;
        this.omdbApiService = omdbApiService;
    }

    public List<OmdbOverview> getWatchlistByType(Optional<String> type){
        if(type.isEmpty()){
            return movieAndSeriesRepo.findMovieAndSeriesByWatchlistIsTrue().stream()
                    .map(item -> omdbApiService.getOverview(item)
                    )
                    .collect(Collectors.toList());
        }
        return movieAndSeriesRepo.findMovieAndSeriesByWatchlistIsTrueAndType(type.get())
                .stream()
                .map(item -> omdbApiService.getOverview(item))
                .collect(Collectors.toList());
    }

    public String getNameFromWatchlist(String imdbId){
        MovieAndSeries movieAndSeries = movieAndSeriesRepo.findByImdbID(imdbId);
        if(movieAndSeries == null) {
            return null;
        }
        return movieAndSeriesRepo.findByImdbID(imdbId).getRecommendedBy();
    }

    public OmdbOverview addToWatchlist(MovieAndSeries itemToAdd){
        MovieAndSeries movieAndSeries = movieAndSeriesRepo.findByImdbID(itemToAdd.getImdbID());
        if(movieAndSeries == null) {
            itemToAdd.setWatchlist(true);
            movieAndSeriesRepo.save(itemToAdd);
        } else {
            movieAndSeries.setWatchlist(true);
            movieAndSeriesRepo.save(movieAndSeries);
        }
        return omdbApiService.getOverview(movieAndSeriesRepo.findByImdbID(itemToAdd.getImdbID()));
    }

    public String addNameToWatchlist(MovieAndSeries itemToAdd){
        MovieAndSeries movieAndSeries = movieAndSeriesRepo.findByImdbID(itemToAdd.getImdbID());
        if(movieAndSeries == null) {
            return null;
        }
        movieAndSeries.setRecommendedBy(itemToAdd.getRecommendedBy());
        movieAndSeriesRepo.save(movieAndSeries);
        return movieAndSeriesRepo.findByImdbID(itemToAdd.getImdbID()).getRecommendedBy();
    }

    public void removeFromWatchlist(String imdbId){
        MovieAndSeries movieAndSeries = movieAndSeriesRepo.findByImdbID(imdbId);
        if(movieAndSeries != null) {
            if (!movieAndSeries.isWatchHistory()) {
                movieAndSeriesRepo.deleteByImdbID(imdbId);
            } else {
                movieAndSeries.setWatchlist(false);
                movieAndSeriesRepo.save(movieAndSeries);
            }
        }
    }

    public void removeNameFromWatchlist(String imdbId){
        MovieAndSeries movieAndSeries = movieAndSeriesRepo.findByImdbID(imdbId);
        movieAndSeries.setRecommendedBy(null);
        movieAndSeriesRepo.save(movieAndSeries);
    }

    public List<Stats> getRecommendedByStats(){
        List<MovieAndSeries> movieAndSeriesList = movieAndSeriesRepo.findAll();
        Map<String, Stats> stats = new HashMap();
        for(MovieAndSeries item : movieAndSeriesList){
            if(item.getRecommendedBy() != null){
                String name = item.getRecommendedBy();
                if (stats.containsKey(name)){
                    List<String> imdbIDs = new ArrayList<>(stats.get(name).getRecommendations());
                    imdbIDs.add(item.getImdbID());
                    stats.put(name, Stats.builder().name(name).recommendations(imdbIDs).number(imdbIDs.size()).build());
                } else {
                    stats.put(name, Stats.builder().name(name).recommendations(List.of(item.getImdbID())).number(1).build());
                }
            }
        }
        System.out.println(new ArrayList<>(stats.values()));
        return new ArrayList<>(stats.values());
    }
}
