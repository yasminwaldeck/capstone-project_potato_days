package service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.model.OmdbOverview;
import de.neuefische.backend.model.OmdbOverviewDto;
import de.neuefische.backend.model.OmdbResponseDto;
import de.neuefische.backend.service.OmdbApiService;
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
    private final OmdbApiService movieApiService = new OmdbApiService(omDbConfig, mockedTemplate);

    @Test
    public void searchMoviesShouldReturnAListWithMovies(){

        //GIVEN
        List<OmdbOverviewDto> movies = List.of(
                new OmdbOverviewDto("title", "year", "imdb", "url"),
                new OmdbOverviewDto("title2", "year2", "imdb2", "url2"),
                new OmdbOverviewDto("title3", "year3", "imdb3", "url3")
        );

        List<OmdbOverview> expected = List.of(
                new OmdbOverview("title", "year", "imdb", "url"),
                new OmdbOverview("title2", "year2", "imdb2", "url2"),
                new OmdbOverview("title3", "year3", "imdb3", "url3")
        );

        OmdbResponseDto omdbResponseDto = new OmdbResponseDto(movies, 3);

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&s=title&type=movie", OmdbResponseDto.class))
                .thenReturn(ResponseEntity.ok(omdbResponseDto));

        //WHEN

        List<OmdbOverview> actual = movieApiService.searchByString("title", "movie");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&s=title&type=movie"),
                OmdbResponseDto.class);

    }

    @Test
    public void searchMoviesShouldReturnAnEmptyList(){

        //GIVEN

        OmdbResponseDto omdbResponseDto = new OmdbResponseDto(null, 3);

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&s=title&type=movie", OmdbResponseDto.class))
                .thenReturn(ResponseEntity.ok(omdbResponseDto));

        //WHEN

        List<OmdbOverview> actual = movieApiService.searchByString("title", "movie");

        //THEN

        assertThat(actual, is(List.of()));
        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&s=title&type=movie"),
                OmdbResponseDto.class);

    }


}
