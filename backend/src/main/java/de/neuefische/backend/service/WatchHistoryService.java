package de.neuefische.backend.service;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.model.Episode;
import de.neuefische.backend.repo.MovieAndSeriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchHistoryService {

    private final MovieAndSeriesRepo movieAndSeriesRepo;
    private final OmdbApiService omdbApiService;
    private final TmdbApiService tmdbApiService;

    @Autowired
    public WatchHistoryService(MovieAndSeriesRepo movieAndSeriesRepo, OmdbApiService omdbApiService, TmdbApiService tmdbApiService) {
        this.movieAndSeriesRepo = movieAndSeriesRepo;
        this.omdbApiService = omdbApiService;
        this.tmdbApiService = tmdbApiService;
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

    public List<Episode> getWatchHistoryEpisodes(String imdbID, String season){
        if(!movieAndSeriesRepo.existsById(imdbID)) {
            return List.of();
        }
        return movieAndSeriesRepo.findByImdbID(imdbID)
                .getWatchedEpisodes()
                .stream()
                .filter((episode) -> String.valueOf(episode.getSeason_number()).equals(season))
                .collect(Collectors.toList());
    }

    public List<Episode> getWatchHistoryAllEpisodes(String imdbID){
        if(!movieAndSeriesRepo.existsById(imdbID)) {
            return List.of();
        }
        return movieAndSeriesRepo.findByImdbID(imdbID).getWatchedEpisodes();
    }

    public float getWatchHistorySeasonProgress(String imdbId, String tmdbId, String season){
        if (getWatchHistoryEpisodes(imdbId, season) == null){
            return 0;
        }
        float watched = getWatchHistoryEpisodes(imdbId, season).size();
        float total = tmdbApiService.getEpisodes(tmdbId, season).getEpisodes().size();
        return watched/total * 100;
    }

    public float getWatchHistoryTotalProgress(String imdbId, String tmdbId){
        List<Episode> list = getWatchHistoryAllEpisodes(imdbId);
        if (list == null){
            return 0;
        }
        float watched = list.size();
        float total = tmdbApiService.getDetails(tmdbId, "series").getNumber_of_episodes();
        if (total == watched){
            MovieAndSeries movieAndSeries = movieAndSeriesRepo.findByImdbID(imdbId);
            movieAndSeries.setWatchHistory(true);
            movieAndSeries.setWatching(false);
            movieAndSeriesRepo.save(movieAndSeries);
        }
        return watched/total * 100;
    }

    public Episode addToWatchHistoryEpisodes(Episode episode){
        MovieAndSeries movieAndSeries = movieAndSeriesRepo.findByImdbID(episode.getImdbId());
        List<Episode> list = movieAndSeries.getWatchedEpisodes();
        Episode itemToAdd = Episode.builder()
                .season_number(episode.getSeason_number())
                .episode_number(episode.getEpisode_number())
                .build();
        if(list != null) {
            if(list.contains(itemToAdd)){
                return null;
            }
            list.add(itemToAdd);
        } else {
            list = List.of(itemToAdd);
        }
        movieAndSeries.setWatchedEpisodes(list);
        movieAndSeries.setWatching(true);
        movieAndSeries.setType("series");
        movieAndSeriesRepo.save(movieAndSeries);
        return itemToAdd;
    }

    public void removeFromWatchHistoryEpisodes(Episode episode){
        if(movieAndSeriesRepo.existsById(episode.getImdbId())) {
            MovieAndSeries movieAndSeries = movieAndSeriesRepo.findByImdbID(episode.getImdbId());
            List<Episode> list = movieAndSeries.getWatchedEpisodes();
            list = list.stream()
                    .filter((item) ->
                            !(item.getEpisode_number() == episode.getEpisode_number() &&
                                    item.getSeason_number() == episode.getSeason_number()))
            .collect(Collectors.toList());

            if (list.size() == 0) {
                movieAndSeries.setWatching(false);
            }
            movieAndSeries.setWatchedEpisodes(list);
            movieAndSeriesRepo.save(movieAndSeries);
        }
    }
}
