package de.neuefische.backend.repo;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.MovieAndSeriesWatched;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchHistoryRepo extends PagingAndSortingRepository<MovieAndSeriesWatched, String> {

    List<MovieAndSeriesWatched> findAll();

    List<MovieAndSeriesWatched> findByType(String type);

    void deleteByImdbID(String imdbID);

}
