package de.neuefische.backend.service;

import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.model.TMDb.TmdbDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrendingService {

    private TmdbApiService tmdbApiService;
    private OmdbApiService omdbApiService;

    public TrendingService(TmdbApiService tmdbApiService, OmdbApiService omdbApiService) {
        this.tmdbApiService = tmdbApiService;
        this.omdbApiService = omdbApiService;
    }

    public List<OmdbOverview> findTrending(String timewindow, String type){
        if(timewindow == null || type == null){
            return null;
        }
        List<String> trendingTmdbIds = tmdbApiService.getTrending(timewindow, type);
        List<String> trendingImdbIds = trendingTmdbIds.stream().map(tmdbId -> tmdbApiService.getImdbId(tmdbId, type)).collect(Collectors.toList());
        return trendingImdbIds.stream().map(imdbId -> omdbApiService.getOverviewById(imdbId)).collect(Collectors.toList());
    }
}
