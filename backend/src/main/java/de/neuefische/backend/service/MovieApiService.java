package de.neuefische.backend.service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.model.Movie;
import de.neuefische.backend.model.OmdbResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieApiService {

    private final OMDbConfig omDbConfig;
    private final RestTemplate restTemplate;
    private final String BASE_URL_WITH_KEY_ANNOTATION = "https://www.omdbapi.com/" + "?type=movie" + "&apikey=";

    public MovieApiService(OMDbConfig omDbConfig, RestTemplate restTemplate){
        this.omDbConfig = omDbConfig;
        this.restTemplate = restTemplate;
    }

    public List<Movie> searchMovies(String searchString){
        String url = BASE_URL_WITH_KEY_ANNOTATION + omDbConfig.getOmdbKey() + "&s=" + searchString;
        ResponseEntity<OmdbResponseDto> response = restTemplate.getForEntity(url, OmdbResponseDto.class);
        if(response.getBody() != null && response.getBody().getMovieList() != null){
            return response.getBody().getMovieList();
        }
        return List.of();
    }
}
