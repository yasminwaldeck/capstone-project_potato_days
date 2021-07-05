package de.neuefische.backend.service;

import de.neuefische.backend.config.TMDbConfig;
import de.neuefische.backend.model.OMDb.OmdbDetails;
import de.neuefische.backend.model.OMDb.OmdbDetailsDto;
import de.neuefische.backend.model.TMDb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TmdbApiService {

    private final RestTemplate restTemplate;
    private final TMDbConfig tmdbConfig;
    private final String BASE_URL = "https://api.themoviedb.org/3/";

    @Autowired
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
        } else if (!result.getTv_results().isEmpty()){
            return result.getTv_results().get(0).getId();
        }
        return null;
    }

    public TmdbDto getDetails(String id, String type){
        if(id.isEmpty() || type.isBlank()){
            return null;
        }

        String urlpart = "";

        switch(type) {
            case "movie":
                urlpart = "movie/";
                break;
            case "series":
                urlpart = "tv/";
                break;
        }

        String url = BASE_URL + urlpart + id + "?api_key=" + tmdbConfig.getKey() + "&append_to_response=external_ids";
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

    public List<String> getTrending(String timewindow, String type){

        String urlpart = "";

        switch(type) {
            case "movie":
                urlpart = "movie/";
                break;
            case "series":
                urlpart = "tv/";
                break;
        }
        String url = BASE_URL + "trending/" + urlpart + timewindow + "?api_key=" + tmdbConfig.getKey();

        ResponseEntity<TmdbTrendingResponseDto> response = restTemplate.getForEntity(url, TmdbTrendingResponseDto.class);
        return response.getBody().getResults().stream().map(result -> result.getId()).collect(Collectors.toList());
    }

    public String getImdbId(String tmdbId, String type){
        return getDetails(tmdbId, type).getExternal_ids().getImdb_id();
    }

    public Season getEpisodes(String id, String season){
        String url = BASE_URL + "tv/" + id + "/season/" + season + "?api_key=" + tmdbConfig.getKey();
        ResponseEntity<Season> response = restTemplate.getForEntity(url, Season.class);
        return response.getBody();
    }
}
