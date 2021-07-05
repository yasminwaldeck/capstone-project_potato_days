package de.neuefische.backend.repo;

import de.neuefische.backend.model.MovieAndSeries;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieAndSeriesRepo extends PagingAndSortingRepository<MovieAndSeries, String> {

    List<MovieAndSeries> findByUsername(String username);

    List<MovieAndSeries> findByUsernameAndWatchHistoryIsFalseAndWatchingIsFalse(String username);

    List<MovieAndSeries> findByUsernameAndWatchingIsTrueAndWatchHistoryIsFalseAndWatchlistIsTrue(String username);

    List<MovieAndSeries> findMovieAndSeriesByWatchHistoryIsTrueAndUsername(String username);

    List<MovieAndSeries> findMovieAndSeriesByWatchHistoryIsTrueAndTypeAndUsername(String type, String username);

    List<MovieAndSeries> findMovieAndSeriesByWatchlistIsTrueAndUsername(String username);

    List<MovieAndSeries> findMovieAndSeriesByWatchlistIsTrueAndTypeAndUsername(String type, String username);

}
