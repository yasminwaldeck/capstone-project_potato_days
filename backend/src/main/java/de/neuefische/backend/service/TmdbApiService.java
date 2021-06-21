package de.neuefische.backend.service;

import de.neuefische.backend.config.TMDbConfig;
import de.neuefische.backend.model.OMDb.OmdbDetails;
import de.neuefische.backend.model.OMDb.OmdbDetailsDto;
import de.neuefische.backend.model.TMDb.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TmdbApiService {

    private final RestTemplate restTemplate;
    private final TMDbConfig tmdbConfig;
    private final String BASE_URL = "https://api.themoviedb.org/3/";

    public TmdbApiService(RestTemplate restTemplate, TMDbConfig tmdbConfig) {
        this.restTemplate = restTemplate;
        this.tmdbConfig = tmdbConfig;
    }

    public String getId(String imdbId) {

        String url = BASE_URL + "find/" + imdbId + "?external_source=imdb_id&api_key=" + tmdbConfig.getKey();
        ResponseEntity<TmdbIdDto> response = restTemplate.getForEntity(url, TmdbIdDto.class);
        TmdbIdDto result = response.getBody();
        if (result.getTv_results().isEmpty() && !result.getMovie_results().isEmpty()){
            return result.getMovie_results().get(0).getId();
        } else if (!result.getTv_results().isEmpty() && result.getMovie_results().isEmpty()){
            return result.getTv_results().get(0).getId();
        }
        return null;
    }

    public TmdbDto getDetails(String id, String type){

        String urlpart = "";

        switch(type) {
            case "movie":
                urlpart = "movie/";
                break;
            case "series":
                urlpart = "tv/";
                break;
        }

        String url = BASE_URL + urlpart + id + "?api_key=" + tmdbConfig.getKey();

        ResponseEntity<TmdbDto> response = restTemplate.getForEntity(url, TmdbDto.class);
        return response.getBody();
    }

    public TmdbOTTbyCountryResponseDto getOTT(String id, String type){

        String urlpart = "";

        switch(type) {
            case "movie":
                urlpart = "movie/";
                break;
            case "series":
                urlpart = "tv/";
                break;
        }

        String url = BASE_URL + urlpart + id + "/watch/providers?api_key=" + tmdbConfig.getKey();

        ResponseEntity<TmdbResponseDto> response = restTemplate.getForEntity(url, TmdbResponseDto.class);
        return response.getBody().getResults();
    }

    public TmdbCreditDto getCredits(String id, String type){

        String urlpart = "";

        switch(type) {
            case "movie":
                urlpart = "movie/";
                break;
            case "series":
                urlpart = "tv/";
                break;
        }

        String url = BASE_URL + urlpart + id + "/credits?api_key=" + tmdbConfig.getKey();

        ResponseEntity<TmdbCreditDto> response = restTemplate.getForEntity(url, TmdbCreditDto.class);
        return response.getBody();
    }


}
