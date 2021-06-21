package de.neuefische.backend.service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmdbApiService {

    private final OMDbConfig omdbConfig;
    private final RestTemplate restTemplate;
    private final String BASE_URL_WITH_KEY_ANNOTATION = "https://www.omdbapi.com/" +"?apikey=";

    public OmdbApiService(OMDbConfig omdbConfig, RestTemplate restTemplate){
        this.omdbConfig = omdbConfig;
        this.restTemplate = restTemplate;
    }

    public List<OmdbOverview> searchByString(String searchString, String type){

        if(searchString.isEmpty() || type.isEmpty()){
            return null;
        }

        String url = BASE_URL_WITH_KEY_ANNOTATION + omdbConfig.getKey() + "&s=" + searchString + "&type=" + type;
        ResponseEntity<OmdbResponseDto> response = restTemplate.getForEntity(url, OmdbResponseDto.class);

        if(response.getBody() == null || response.getBody().getList() == null) {
            return List.of();
        }

        List<OmdbOverviewDto> responseList = response.getBody().getList();
        return responseList.stream()
                    .map(movie -> OmdbOverview.builder()
                    .title(movie.getTitle())
                    .year(movie.getYear())
                    .imdbID(movie.getImdbID())
                    .poster(movie.getPoster())
                    .type(movie.getType()).build())
                    .collect(Collectors.toList());
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
                .type(movie.getType())
                .totalSeasons(movie.getTotalSeasons())
                .build();
    }

    public OmdbOverview getOverviewById(String imdbID){

        String url = BASE_URL_WITH_KEY_ANNOTATION + omdbConfig.getKey() + "&i=" + imdbID;
        ResponseEntity<OmdbOverviewDto> response = restTemplate.getForEntity(url, OmdbOverviewDto.class);

        if(response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        OmdbOverviewDto responseBody = response.getBody();
        return OmdbOverview.builder()
                        .title(responseBody.getTitle())
                        .year(responseBody.getYear())
                        .imdbID(responseBody.getImdbID())
                        .poster(responseBody.getPoster())
                        .type(responseBody.getType())
                        .build();
    }
}
