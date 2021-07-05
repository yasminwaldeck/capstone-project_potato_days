package de.neuefische.backend.service;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.model.Episode;
import de.neuefische.backend.model.Random;
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

    public OmdbOverview addToWatchHistory(String username, MovieAndSeries itemToAdd){
        String id = itemToAdd.getImdbID() + "_" + username;
        if(movieAndSeriesRepo.findById(id).isEmpty()) {
            itemToAdd.setWatchHistory(true);
            itemToAdd.setUsername(username);
            itemToAdd.setId(id);
            movieAndSeriesRepo.save(itemToAdd);
        } else {
            MovieAndSeries movieAndSeries = movieAndSeriesRepo.findById(id).get();
            movieAndSeries.setWatchHistory(true);
            movieAndSeriesRepo.save(movieAndSeries);
        }
        return omdbApiService.getOverview(movieAndSeriesRepo.findById(id).get());
    }

    public void removeFromWatchHistory(String username, String imdbId){
        String id = imdbId + "_" + username;
        if(movieAndSeriesRepo.findById(id).isPresent()) {
            MovieAndSeries movieAndSeries = movieAndSeriesRepo.findById(id).get();
            if (!(movieAndSeries.isWatchlist() || movieAndSeries.isWatching())) {
                movieAndSeriesRepo.deleteById(id);
            } else {
                movieAndSeries.setWatchHistory(false);
                movieAndSeriesRepo.save(movieAndSeries);
            }
        }
    }

    public List<OmdbOverview> getWatchHistoryByType(String username, Optional<String> type){
        if(type.isEmpty()){
            return movieAndSeriesRepo.findMovieAndSeriesByWatchHistoryIsTrueAndUsername(username).stream()
                    .map(item -> omdbApiService.getOverview(item))
                    .collect(Collectors.toList());
        }
        return movieAndSeriesRepo.findMovieAndSeriesByWatchHistoryIsTrueAndTypeAndUsername(type.get(), username)
                .stream()
                .map(item -> omdbApiService.getOverview(item))
                .collect(Collectors.toList());
    }

    public List<Episode> getWatchHistoryEpisodes(String username, String imdbID, String season){
        String id = imdbID + "_" + username;
        if(!movieAndSeriesRepo.existsById(id)) {
            return List.of();
        }
        return movieAndSeriesRepo.findById(id)
                .get()
                .getWatchedEpisodes()
                .stream()
                .filter((episode) -> String.valueOf(episode.getSeason_number()).equals(season))
                .collect(Collectors.toList());
    }

    public List<Episode> getWatchHistoryAllEpisodes(String id){
        if(!movieAndSeriesRepo.existsById(id)) {
            return List.of();
        }
        return movieAndSeriesRepo.findById(id).get().getWatchedEpisodes();
    }

    public float getWatchHistorySeasonProgress(String username, String imdbId, String tmdbId, String season){
        if (getWatchHistoryEpisodes(username, imdbId, season) == null){
            return 0;
        }
        float watched = getWatchHistoryEpisodes(username, imdbId, season).size();
        float total = tmdbApiService.getEpisodes(tmdbId, season).getEpisodes().size();
        return watched/total * 100;
    }

    public float getWatchHistoryTotalProgress(String username, String imdbId, String tmdbId){
        String id = imdbId + "_" + username;
        List<Episode> list = getWatchHistoryAllEpisodes(id);
        if (list == null){
            return 0;
        }
        float watched = list.size();
        float total = tmdbApiService.getDetails(tmdbId, "series").getNumber_of_episodes();
        if (total == watched){
            MovieAndSeries movieAndSeries = movieAndSeriesRepo.findById(id).get();
            movieAndSeries.setWatchHistory(true);
            movieAndSeries.setWatching(false);
            movieAndSeriesRepo.save(movieAndSeries);
        }
        return watched/total * 100;
    }

    public Episode addToWatchHistoryEpisodes(String username, Episode episode){
        String id = episode.getImdbId() + "_" + username;
        Episode itemToAdd = Episode.builder()
                .season_number(episode.getSeason_number())
                .episode_number(episode.getEpisode_number())
                .build();
        MovieAndSeries movieAndSeries;
        if(movieAndSeriesRepo.existsById(id)) {
            movieAndSeries = movieAndSeriesRepo.findById(id).get();
            List<Episode> list = movieAndSeries.getWatchedEpisodes();
            if(list.contains(itemToAdd)){
                return null;
            }
            list.add(itemToAdd);
            movieAndSeries.setWatchedEpisodes(list);
        } else {
            List<Episode> list = List.of(itemToAdd);
            movieAndSeries = MovieAndSeries.builder().watchedEpisodes(list).build();
        }
        movieAndSeries.setWatching(true);
        movieAndSeries.setWatchlist(true);
        movieAndSeries.setType("series");
        movieAndSeries.setUsername(username);
        movieAndSeries.setId(id);
        movieAndSeries.setImdbID(episode.getImdbId());
        movieAndSeriesRepo.save(movieAndSeries);
        return itemToAdd;
    }

    public void removeFromWatchHistoryEpisodes(String username, Episode episode){
        String id = episode.getImdbId() + "_" + username;
        if(movieAndSeriesRepo.existsById(id)) {
            MovieAndSeries movieAndSeries = movieAndSeriesRepo.findById(id).get();
            List<Episode> list = movieAndSeries.getWatchedEpisodes();
            list = list.stream()
                    .filter((item) ->
                            !(item.getEpisode_number() == episode.getEpisode_number() &&
                                    item.getSeason_number() == episode.getSeason_number()))
            .collect(Collectors.toList());

            if (list.size() == 0) {
                if(! (movieAndSeries.isWatchlist() || movieAndSeries.isWatchHistory())){
                    movieAndSeriesRepo.deleteById(id);
                    return;
                }
                movieAndSeries.setWatching(false);
            }
            movieAndSeries.setWatchedEpisodes(list);
            movieAndSeriesRepo.save(movieAndSeries);
        }
    }

    public Random getRandomWatchingEntry(String username){
        List<MovieAndSeries> fullList = movieAndSeriesRepo.findByUsernameAndWatchingIsTrueAndWatchHistoryIsFalseAndWatchlistIsTrue(username);
        int length = fullList.size();
        if (length == 0){
            return null;
        }
        int random =  (int)(Math.random() * length);
        OmdbOverview omdbOverview = omdbApiService.getOverviewById(fullList.get(random).getImdbID());
        float progress = getWatchHistoryTotalProgress(username, omdbOverview.getImdbID(), tmdbApiService.getId(omdbOverview.getImdbID()));

        return Random.builder().title(omdbOverview.getTitle()).year(omdbOverview.getYear()).poster_path(omdbOverview.getPoster())
                .imdbId(omdbOverview.getImdbID()).progress(progress).build();
    }
}
