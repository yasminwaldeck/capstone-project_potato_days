package service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.model.OMDb.*;
import de.neuefische.backend.service.OmdbApiService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.Matchers.is;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class OmdbApiServiceTest {

    private final RestTemplate mockedTemplate = mock(RestTemplate.class);
    private final OMDbConfig omDbConfig = new OMDbConfig();
    private final OmdbApiService omdbApiService = new OmdbApiService(omDbConfig, mockedTemplate);

    @Test
    public void searchMoviesShouldReturnAListWithMovies(){

        //GIVEN
        List<OmdbOverviewDto> movies = List.of(
                new OmdbOverviewDto("title", "year", "imdb", "url", "movie"),
                new OmdbOverviewDto("title2", "year2", "imdb2", "url2", "movie"),
                new OmdbOverviewDto("title3", "year3", "imdb3", "url3", "movie")
        );

        List<OmdbOverview> expected = List.of(
                new OmdbOverview("title", "year", "imdb", "url", "movie"),
                new OmdbOverview("title2", "year2", "imdb2", "url2", "movie"),
                new OmdbOverview("title3", "year3", "imdb3", "url3", "movie")
        );

        OmdbResponseDto omdbResponseDto = new OmdbResponseDto(movies, 3);

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&s=title&type=movie", OmdbResponseDto.class))
                .thenReturn(ResponseEntity.ok(omdbResponseDto));

        //WHEN

        List<OmdbOverview> actual = omdbApiService.searchByString("title", "movie");

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

        List<OmdbOverview> actual = omdbApiService.searchByString("title", "movie");

        //THEN

        assertThat(actual, is(List.of()));
        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&s=title&type=movie"),
                OmdbResponseDto.class);

    }

    @Test
    public void getDetailsShouldReturnAllTheDetails(){

        //GIVEN

        OmdbDetailsDto omdbDetailsDto = OmdbDetailsDto.builder()
                .imdbID("imdbID")
                .country("country")
                .poster("poster")
                .ratings(List.of(new OmdbRatingDto("source", "value")))
                .runtime("runtime")
                .title("title")
                .type("type")
                .year("year")
                .build();

        OmdbDetails expected = OmdbDetails.builder()
                .imdbID("imdbID")
                .country("country")
                .poster("poster")
                .ratings(List.of(new OmdbRating("source", "value")))
                .runtime("runtime")
                .title("title")
                .type("type")
                .year("year")
                .build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=someId", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(omdbDetailsDto));

        //WHEN

        OmdbDetails actual = omdbApiService.getDetails("someId");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=someId"),
                OmdbDetailsDto.class);

    }


    @Test
    public void getOverviewByIdShouldReturnOverview(){

        //GIVEN
        OmdbOverviewDto omdbOverviewDto = new OmdbOverviewDto(
                "title", "year", "imdbId", "poster", "type"
        );

        OmdbOverview expected = new OmdbOverview(
                "title", "year", "imdbId", "poster", "type"
        );

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=someId", OmdbOverviewDto.class))
                .thenReturn(ResponseEntity.ok(omdbOverviewDto));

        //WHEN

        OmdbOverview actual = omdbApiService.getOverviewById("someId");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=someId"),
                OmdbOverviewDto.class);

    }


}
