package de.neuefische.backend.service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmdbDetailsApiService {
    private final OMDbConfig omdbConfig;
    private final RestTemplate restTemplate;
    private final String BASE_URL_WITH_KEY_ANNOTATION = "https://www.omdbapi.com/" + "?apikey=";

    public OmdbDetailsApiService(OMDbConfig omdbConfig, RestTemplate restTemplate){
        this.omdbConfig = omdbConfig;
        this.restTemplate = restTemplate;
    }

    public OmdbDetails getDetails(String id){

        String url = BASE_URL_WITH_KEY_ANNOTATION + omdbConfig.getKey() + "&i=" + id;
        ResponseEntity<OmdbDetailsDto> response = restTemplate.getForEntity(url, OmdbDetailsDto.class);
        OmdbDetailsDto movie = response.getBody();

        return OmdbDetails.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .imdbID(movie.getImdbID())
                .poster(movie.getPoster())
                .runtime(movie.getRuntime())
                .genre(movie.getGenre())
                .director(movie.getDirector())
                .writer(movie.getWriter())
                .actors(movie.getActors())
                .country(movie.getCountry())
                .ratings(movie.getRatings()
                        .stream()
                        .map(rating -> OmdbRating.builder()
                                .source(rating.getSource())
                                .value(rating.getValue())
                                .build())
                        .collect(Collectors.toList()))
                .totalSeasons(movie.getTotalSeasons())
                .build();
    }
}
