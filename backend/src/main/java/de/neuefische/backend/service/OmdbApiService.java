package de.neuefische.backend.service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.*;
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
    private final String BASE_URL_WITH_KEY_ANNOTATION = "https://www.omdbapi.com/?apikey=";

    public OmdbApiService(OMDbConfig omdbConfig, RestTemplate restTemplate){
        this.omdbConfig = omdbConfig;
        this.restTemplate = restTemplate;
    }

    public List<OmdbDetails> searchByString(String searchString, String type){

        if(searchString.isEmpty() || type.isEmpty()){
            return null;
        }

        String url = BASE_URL_WITH_KEY_ANNOTATION + omdbConfig.getKey() + "&s=" + searchString + "&type=" + type;
        ResponseEntity<OmdbResponseDto> response = restTemplate.getForEntity(url, OmdbResponseDto.class);

        if(response.getBody() == null || response.getBody().getList() == null) {
            return List.of();
        }

        List<OmdbDetailsDto> responseList = response.getBody().getList();
        return responseList.stream()
                    .map(movie -> OmdbDetails.builder()
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

        if(!response.hasBody()){
            return null;
        }

        OmdbDetailsDto movie = response.getBody();

        return OmdbDetails.builder()
                .title(movie.getTitle())
                .year(movie.getYear())
                .imdbID(movie.getImdbID())
                .poster(movie.getPoster())
                .runtime(movie.getRuntime())
                .country(movie.getCountry())
                .ratings(movie.getRatings()
                        .stream()
                        .map(rating -> OmdbRating.builder()
                                .source(rating.getSource())
                                .value(rating.getValue())
                                .build())
                        .collect(Collectors.toList()))
                .type(movie.getType())
                .build();
    }

    public OmdbDetails getOverview(MovieAndSeries movieAndSeries){

        String url = BASE_URL_WITH_KEY_ANNOTATION + omdbConfig.getKey() + "&i=" + movieAndSeries.getImdbID();
        ResponseEntity<OmdbDetailsDto> response = restTemplate.getForEntity(url, OmdbDetailsDto.class);

        if(response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            OmdbDetailsDto responseBody = response.getBody();
            return OmdbDetails.builder()
                    .title(responseBody.getTitle())
                    .year(responseBody.getYear())
                    .imdbID(responseBody.getImdbID())
                    .poster(responseBody.getPoster())
                    .type(responseBody.getType())
                    .build();
        }
    }

    public OmdbDetails getOverviewById(String imdbID){

        String url = BASE_URL_WITH_KEY_ANNOTATION + omdbConfig.getKey() + "&i=" + imdbID;
        ResponseEntity<OmdbDetailsDto> response = restTemplate.getForEntity(url, OmdbDetailsDto.class);

        if(response.getBody() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        OmdbDetailsDto responseBody = response.getBody();
        return OmdbDetails.builder()
                .title(responseBody.getTitle())
                .year(responseBody.getYear())
                .imdbID(responseBody.getImdbID())
                .poster(responseBody.getPoster())
                .type(responseBody.getType())
                .build();
    }
}
