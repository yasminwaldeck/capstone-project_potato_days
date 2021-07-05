package de.neuefische.backend.service;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.OmdbDetails;
import de.neuefische.backend.model.Random;
import de.neuefische.backend.model.Stats;
import de.neuefische.backend.repo.MovieAndSeriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<OmdbDetails> getWatchlistByType(String username, Optional<String> type){
        if(type.isEmpty()){
            return movieAndSeriesRepo.findMovieAndSeriesByWatchlistIsTrueAndUsername(username).stream()
                    .map(item -> omdbApiService.getOverview(item)
                    )
                    .collect(Collectors.toList());
        }
        return movieAndSeriesRepo.findMovieAndSeriesByWatchlistIsTrueAndTypeAndUsername(type.get(), username)
                .stream()
                .map(item -> omdbApiService.getOverview(item))
                .collect(Collectors.toList());
    }

    public String getNameFromWatchlist(String username, String imdbId){
        String id = imdbId + "_" + username;
        if(movieAndSeriesRepo.findById(id).isEmpty()) {
            return null;
        }
        return movieAndSeriesRepo.findById(id).get().getRecommendedBy();
    }

    public OmdbDetails addToWatchlist(String username, MovieAndSeries itemToAdd){
        String id = itemToAdd.getImdbID() + "_" + username;
        if(movieAndSeriesRepo.findById(id).isEmpty()) {
            itemToAdd.setWatchlist(true);
            itemToAdd.setUsername(username);
            itemToAdd.setId(id);
            movieAndSeriesRepo.save(itemToAdd);
        } else {
            MovieAndSeries movieAndSeries = movieAndSeriesRepo.findById(id).get();
            movieAndSeries.setWatchlist(true);
            movieAndSeriesRepo.save(movieAndSeries);
        }
        return omdbApiService.getOverview(movieAndSeriesRepo.findById(id).get());
    }

    public String addNameToWatchlist(String username, MovieAndSeries itemToAdd){
        String id = itemToAdd.getImdbID() + "_" + username;
        if(movieAndSeriesRepo.findById(id).isEmpty()) {
            return null;
        }
        MovieAndSeries movieAndSeries = movieAndSeriesRepo.findById(id).get();
        movieAndSeries.setRecommendedBy(itemToAdd.getRecommendedBy());
        movieAndSeriesRepo.save(movieAndSeries);
        return movieAndSeries.getRecommendedBy();
    }

    public void removeFromWatchlist(String username, String imdbId){
        String id = imdbId + "_" + username;
        if(movieAndSeriesRepo.findById(id).isPresent()) {
            MovieAndSeries movieAndSeries = movieAndSeriesRepo.findById(id).get();
            if (!(movieAndSeries.isWatchHistory() || movieAndSeries.isWatching())) {
                movieAndSeriesRepo.deleteById(id);
            } else {
                movieAndSeries.setWatchlist(false);
                movieAndSeriesRepo.save(movieAndSeries);
            }
        }
    }

    public void removeNameFromWatchlist(String username, String imdbId){
        String id = imdbId + "_" + username;
        MovieAndSeries movieAndSeries = movieAndSeriesRepo.findById(id).get();
        movieAndSeries.setRecommendedBy(null);
        movieAndSeriesRepo.save(movieAndSeries);
    }

    public List<Stats> getRecommendedByStats(String username){
        List<MovieAndSeries> movieAndSeriesList = movieAndSeriesRepo.findByUsername(username);
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
        List<Stats> result = new ArrayList<>(stats.values());
        result.sort(Comparator.comparing(Stats::getNumber));
        Collections.reverse(result);
        return result;
    }

    public Random getRandomWatchlistEntry(String username){
        List<MovieAndSeries> fullList = movieAndSeriesRepo.findByUsernameAndWatchHistoryIsFalseAndWatchingIsFalse(username);
        int length = fullList.size();
        if (length == 0){
            return null;
        }
        int random =  (int)(Math.random() * length);
        OmdbDetails omdbOverview = omdbApiService.getOverviewById(fullList.get(random).getImdbID());
        return Random.builder().title(omdbOverview.getTitle()).year(omdbOverview.getYear()).poster_path(omdbOverview.getPoster())
                .imdbId(omdbOverview.getImdbID()).build();
    }
}
