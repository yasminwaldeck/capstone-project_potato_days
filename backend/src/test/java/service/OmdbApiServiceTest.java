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
    private final OMDbConfig omdbconfig = new OMDbConfig();
    private final OmdbApiService omdbApiService = new OmdbApiService(omdbconfig, mockedTemplate);

    @Test
    public void searchMoviesShouldReturnAListWithMovies(){

        //GIVEN
        List<OmdbDetailsDto> movies = List.of(
                OmdbDetailsDto.builder().title("title1").build(),
                OmdbDetailsDto.builder().title("title2").build(),
                OmdbDetailsDto.builder().title("title3").build()
        );

        List<OmdbDetails> expected = List.of(
                OmdbDetails.builder().title("title1").build(),
                OmdbDetails.builder().title("title2").build(),
                OmdbDetails.builder().title("title3").build()
        );

        OmdbResponseDto omdbResponseDto = new OmdbResponseDto(movies, 3);

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omdbconfig.getKey() + "&s=title&type=movie", OmdbResponseDto.class))
                .thenReturn(ResponseEntity.ok(omdbResponseDto));

        //WHEN

        List<OmdbDetails> actual = omdbApiService.searchByString("title", "movie");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?apikey=" + omdbconfig.getKey() + "&s=title&type=movie"),
                OmdbResponseDto.class);

    }

    @Test
    public void searchMoviesShouldReturnAnEmptyList(){

        //GIVEN

        OmdbResponseDto omdbResponseDto = new OmdbResponseDto(null, 3);

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omdbconfig.getKey() + "&s=title&type=movie", OmdbResponseDto.class))
                .thenReturn(ResponseEntity.ok(omdbResponseDto));

        //WHEN

        List<OmdbDetails> actual = omdbApiService.searchByString("title", "movie");

        //THEN

        assertThat(actual, is(List.of()));
        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?apikey=" + omdbconfig.getKey() + "&s=title&type=movie"),
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
                "https://www.omdbapi.com/?apikey=" + omdbconfig.getKey() + "&i=someId", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(omdbDetailsDto));

        //WHEN

        OmdbDetails actual = omdbApiService.getDetails("someId");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?apikey=" + omdbconfig.getKey() + "&i=someId"),
                OmdbDetailsDto.class);

    }


    @Test
    public void getOverviewByIdShouldReturnOverview(){

        //GIVEN
        OmdbDetailsDto omdbDetailsDto = OmdbDetailsDto.builder().title("title1").build();

        OmdbDetails expected = OmdbDetails.builder().title("title1").build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omdbconfig.getKey() + "&i=someId", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(omdbDetailsDto));

        //WHEN

        OmdbDetails actual = omdbApiService.getOverviewById("someId");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?apikey=" + omdbconfig.getKey() + "&i=someId"),
                OmdbDetailsDto.class);

    }


}
