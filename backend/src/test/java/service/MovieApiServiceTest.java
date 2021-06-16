package service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.model.MovieOmdbOverview;
import de.neuefische.backend.model.MovieOmdbOverviewDto;
import de.neuefische.backend.model.OmdbResponseDto;
import de.neuefische.backend.service.MovieApiService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.Matchers.is;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;

public class MovieApiServiceTest {

    private final RestTemplate mockedTemplate = mock(RestTemplate.class);
    private final OMDbConfig omDbConfig = new OMDbConfig();
    private final MovieApiService movieApiService = new MovieApiService(omDbConfig, mockedTemplate);

    @Test
    public void searchMoviesShouldReturnAListWithMovies(){

        //GIVEN
        List<MovieOmdbOverviewDto> movies = List.of(
                new MovieOmdbOverviewDto("title", "year", "imdb", "url"),
                new MovieOmdbOverviewDto("title2", "year2", "imdb2", "url2"),
                new MovieOmdbOverviewDto("title3", "year3", "imdb3", "url3")
        );

        List<MovieOmdbOverview> expected = List.of(
                new MovieOmdbOverview("title", "year", "imdb", "url"),
                new MovieOmdbOverview("title2", "year2", "imdb2", "url2"),
                new MovieOmdbOverview("title3", "year3", "imdb3", "url3")
        );

        OmdbResponseDto omdbResponseDto = new OmdbResponseDto(movies, 3);

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?type=movie&apikey=" + omDbConfig.getOmdbKey() + "&s=title", OmdbResponseDto.class))
                .thenReturn(ResponseEntity.ok(omdbResponseDto));

        //WHEN

        List<MovieOmdbOverview> actual = movieApiService.searchMovies("title");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?type=movie&apikey=" + omDbConfig.getOmdbKey() + "&s=title"),
                OmdbResponseDto.class);

    }

    @Test
    public void searchMoviesShouldReturnAnEmptyList(){

        //GIVEN

        OmdbResponseDto omdbResponseDto = new OmdbResponseDto(null, 3);

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?type=movie&apikey=" + omDbConfig.getOmdbKey() + "&s=title", OmdbResponseDto.class))
                .thenReturn(ResponseEntity.ok(omdbResponseDto));

        //WHEN

        List<MovieOmdbOverview> actual = movieApiService.searchMovies("title");

        //THEN

        assertThat(actual, is(List.of()));
        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?type=movie&apikey=" + omDbConfig.getOmdbKey() + "&s=title"),
                OmdbResponseDto.class);

    }


}
