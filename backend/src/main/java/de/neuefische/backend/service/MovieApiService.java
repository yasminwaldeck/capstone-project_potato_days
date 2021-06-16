package de.neuefische.backend.service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.model.MovieOmdbOverview;
import de.neuefische.backend.model.MovieOmdbOverviewDto;
import de.neuefische.backend.model.OmdbResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

    public List<MovieOmdbOverview> searchMovies(String searchString){

        String url = BASE_URL_WITH_KEY_ANNOTATION + omDbConfig.getOmdbKey() + "&s=" + searchString;
        ResponseEntity<OmdbResponseDto> response = restTemplate.getForEntity(url, OmdbResponseDto.class);
        List<MovieOmdbOverview> list = new ArrayList<>();

        if(response.getBody() != null && response.getBody().getMovieList() != null){
            List<MovieOmdbOverviewDto> responseList = response.getBody().getMovieList();
            for(MovieOmdbOverviewDto movie : responseList){
                list.add(
                        MovieOmdbOverview.builder()
                                .title(movie.getTitle())
                                .year(movie.getYear())
                                .imdbId(movie.getImdbID())
                                .poster(movie.getPoster()).build()
                );
            }
        }

        return list;
    }
}
