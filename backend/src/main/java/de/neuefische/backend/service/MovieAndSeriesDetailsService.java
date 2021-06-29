package de.neuefische.backend.service;

import de.neuefische.backend.model.MovieAndSeriesDetails;
import de.neuefische.backend.model.OMDb.OmdbDetails;
import de.neuefische.backend.model.TMDb.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


@Service
public class MovieAndSeriesDetailsService {


    private OmdbApiService omdbApiService;
    private TmdbApiService tmdbApiService;

    @Autowired
    public MovieAndSeriesDetailsService(OmdbApiService omdbApiService, TmdbApiService tmdbApiService) {
        this.omdbApiService = omdbApiService;
        this.tmdbApiService = tmdbApiService;
    }

    public MovieAndSeriesDetails getDetails(String imdbId){

        if (imdbId == null){
            return null;
        }

        OmdbDetails omdbDetails = omdbApiService.getDetails(imdbId);
        String tmdbId = tmdbApiService.getId(imdbId);
        TmdbDto tmdbDto = tmdbApiService.getDetails(tmdbId, omdbDetails.getType());
        TmdbOTTbyCountryResponseDto tmdbOTTbyCountryResponseDto = tmdbApiService.getOTT(tmdbId, omdbDetails.getType());
        TmdbCreditDto tmdbCreditDto = tmdbApiService.getCredits(tmdbId, omdbDetails.getType());
        MovieAndSeriesDetails movieAndSeriesDetails = new MovieAndSeriesDetails();

        BeanUtils.copyProperties(omdbDetails, movieAndSeriesDetails);
        BeanUtils.copyProperties(tmdbDto, movieAndSeriesDetails);
        BeanUtils.copyProperties(tmdbOTTbyCountryResponseDto, movieAndSeriesDetails);
        BeanUtils.copyProperties(tmdbCreditDto, movieAndSeriesDetails);

        return movieAndSeriesDetails;

    }

    public Season getEpisodes(@PathVariable String id, String season){
        return tmdbApiService.getEpisodes(id, season);
    }
}
