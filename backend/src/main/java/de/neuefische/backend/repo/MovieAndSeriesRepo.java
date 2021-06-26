package de.neuefische.backend.repo;

import de.neuefische.backend.model.MovieAndSeries;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieAndSeriesRepo extends PagingAndSortingRepository<MovieAndSeries, String> {

    List<MovieAndSeries> findAll();

    List<MovieAndSeries> findByType(String type);

    List<MovieAndSeries> findMovieAndSeriesByWatchHistoryIsTrue();

    List<MovieAndSeries> findMovieAndSeriesByWatchHistoryIsTrueAndType(String type);

    List<MovieAndSeries> findMovieAndSeriesByWatchlistIsTrue();

    List<MovieAndSeries> findMovieAndSeriesByWatchlistIsTrueAndType(String type);

    MovieAndSeries findByImdbID(String imdbID);

    void deleteByImdbID(String imdbID);
}
