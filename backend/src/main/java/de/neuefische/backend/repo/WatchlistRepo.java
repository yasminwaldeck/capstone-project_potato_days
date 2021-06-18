package de.neuefische.backend.repo;

import de.neuefische.backend.model.MovieAndSeries;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepo extends PagingAndSortingRepository<MovieAndSeries, String> {

    List<MovieAndSeries> findAll();

    List<MovieAndSeries> findByType(String type);

    void deleteByImdbID(String imdbID);
}
