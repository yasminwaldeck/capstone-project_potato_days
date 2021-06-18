package de.neuefische.backend.service;

import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OmdbOverview;
import de.neuefische.backend.repo.WatchlistRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchlistService {
    private WatchlistRepo watchlistRepo;
    private OmdbApiService omdbApiService;

    public List<OmdbOverview> getWatchlistByType(String type){
        return watchlistRepo.findByType(type)
                .stream()
                .map(item -> omdbApiService.getOverviewById(item.getImdbID()))
                .collect(Collectors.toList());
    }
}
