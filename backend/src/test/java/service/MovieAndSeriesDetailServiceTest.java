package service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.config.TMDbConfig;
import de.neuefische.backend.model.MovieAndSeriesDetails;
import de.neuefische.backend.model.OMDb.OmdbDetailsDto;
import de.neuefische.backend.model.OMDb.OmdbRating;
import de.neuefische.backend.model.OMDb.OmdbRatingDto;
import de.neuefische.backend.model.TMDb.*;
import de.neuefische.backend.service.MovieAndSeriesDetailsService;
import de.neuefische.backend.service.OmdbApiService;
import de.neuefische.backend.service.TmdbApiService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.Matchers.is;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieAndSeriesDetailServiceTest {

    private final RestTemplate mockedTemplate = mock(RestTemplate.class);
    private final OMDbConfig omdbConfig = new OMDbConfig();
    private final TMDbConfig tmdbConfig = new TMDbConfig();
    private final OmdbApiService omdbApiService = new OmdbApiService(omdbConfig, mockedTemplate);
    private final TmdbApiService tmdbApiService = new TmdbApiService(mockedTemplate, tmdbConfig);
    private final MovieAndSeriesDetailsService movieAndSeriesDetailsService = new MovieAndSeriesDetailsService(omdbApiService, tmdbApiService);


    @Test
    public void getDetailsShouldReturnDetails(){

        //GIVEN

        //FOR OMDBDETAILS

        OmdbDetailsDto omdbDetailsDto = OmdbDetailsDto.builder()
                .imdbID("imdbID")
                .country("country")
                .poster("poster")
                .ratings(List.of(new OmdbRatingDto("source", "value")))
                .runtime("runtime")
                .title("title")
                .type("movie")
                .year("year")
                .build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omdbConfig.getKey() + "&i=imdbID", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(omdbDetailsDto));

        //FOR GETTING ID

        TmdbDto tmdbDtoForId = TmdbDto.builder()
                .first_air_date("first_air_date")
                .id("id")
                .genres(List.of(Genre.builder().name("name").build()))
                .in_production(true)
                .last_air_date("last_air_date")
                .number_of_episodes(3)
                .number_of_seasons(3)
                .release_date("release_date")
                .seasons(List.of(Season.builder()
                        .name("name")
                        .id("id")
                        .air_date("air_date")
                        .season_number(3)
                        .episode_count(3)
                        .poster_path("poster_path")
                        .overview("overview")
                        .build()))
                .status("status")
                .tagline("tagline")
                .release_date("release_date")
                .build();

        TmdbIdDto response = TmdbIdDto.builder().movie_results(List.of(tmdbDtoForId)).tv_results(List.of()).build();

        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/find/imdbID?external_source=imdb_id&api_key=" + tmdbConfig.getKey(), TmdbIdDto.class))
                .thenReturn(ResponseEntity.ok(response));

        // FOR TMDBDTO

        TmdbDto tmdbDto = TmdbDto.builder()
                .first_air_date("first_air_date")
                .id("id")
                .genres(List.of(Genre.builder().name("name").build()))
                .in_production(true)
                .last_air_date("last_air_date")
                .number_of_episodes(3)
                .number_of_seasons(3)
                .release_date("release_date")
                .seasons(List.of(Season.builder()
                        .name("name")
                        .id("id")
                        .air_date("air_date")
                        .season_number(3)
                        .episode_count(3)
                        .poster_path("poster_path")
                        .overview("overview")
                        .build()))
                .status("status")
                .tagline("tagline")
                .release_date("release_date")
                .build();

        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/movie/id?api_key=" + tmdbConfig.getKey() + "&append_to_response=external_ids", TmdbDto.class))
                .thenReturn(ResponseEntity.ok(tmdbDto));

        //FOR TmdbOTTbyCountryResponseDto

        TmdbResponseDto tmdbResponseDto = TmdbResponseDto.builder()
                .results(TmdbOTTbyCountryResponseDto.builder()
                        .de(TmdbOTTDto.builder()
                                .buy(List.of(Provider.builder()
                                        .provider_name("provider_name")
                                        .logo_path("logo_path").build()))
                                .flatrate(List.of(Provider.builder()
                                        .provider_name("provider_name")
                                        .logo_path("logo_path").build()))
                                .build())
                        .fr(TmdbOTTDto.builder()
                                .buy(List.of(Provider.builder()
                                        .provider_name("provider_name")
                                        .logo_path("logo_path").build()))
                                .flatrate(List.of(Provider.builder()
                                        .provider_name("provider_name")
                                        .logo_path("logo_path").build()))
                                .build())
                        .us(TmdbOTTDto.builder()
                                .buy(List.of(Provider.builder()
                                        .provider_name("provider_name")
                                        .logo_path("logo_path").build()))
                                .flatrate(List.of(Provider.builder()
                                        .provider_name("provider_name")
                                        .logo_path("logo_path").build()))
                                .build())
                        .gb(TmdbOTTDto.builder()
                                .buy(List.of(Provider.builder()
                                        .provider_name("provider_name")
                                        .logo_path("logo_path").build()))
                                .flatrate(List.of(Provider.builder()
                                        .provider_name("provider_name")
                                        .logo_path("logo_path").build()))
                                .build())
                        .build())
                .build();

        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/movie/id/watch/providers?api_key=" + tmdbConfig.getKey(), TmdbResponseDto.class))
                .thenReturn(ResponseEntity.ok(tmdbResponseDto));


        //FOR CREDITS

        TmdbCreditDto tmdbCreditDto = TmdbCreditDto.builder()
                .crew(
                        List.of(Credit.builder().name("name").department("department").profile_path("profile_path").build()))
                .cast(
                        List.of(Credit.builder().name("name").character("character").profile_path("profile_path").build()))
                .build();

        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/movie/id/credits?api_key=" + tmdbConfig.getKey(), TmdbCreditDto.class))
                .thenReturn(ResponseEntity.ok(tmdbCreditDto));


        MovieAndSeriesDetails expected = MovieAndSeriesDetails.builder()
                .imdbID("imdbID")
                .country("country")
                .poster("poster")
                .ratings(List.of(new OmdbRating("source", "value")))
                .runtime("runtime")
                .title("title")
                .type("movie")
                .year("year")
                .first_air_date("first_air_date")
                .id("id")
                .genres(List.of(Genre.builder().name("name").build()))
                .in_production(true)
                .last_air_date("last_air_date")
                .number_of_episodes(3)
                .number_of_seasons(3)
                .release_date("release_date")
                .seasons(List.of(Season.builder()
                        .name("name")
                        .id("id")
                        .air_date("air_date")
                        .season_number(3)
                        .episode_count(3)
                        .poster_path("poster_path")
                        .overview("overview")
                        .build()))
                .status("status")
                .tagline("tagline")
                .release_date("release_date")
                .crew(
                        List.of(Credit.builder().name("name").department("department").profile_path("profile_path").build()))
                .cast(
                        List.of(Credit.builder().name("name").character("character").profile_path("profile_path").build()))
                .de(TmdbOTTDto.builder()
                .buy(List.of(Provider.builder()
                        .provider_name("provider_name")
                        .logo_path("logo_path").build()))
                .flatrate(List.of(Provider.builder()
                        .provider_name("provider_name")
                        .logo_path("logo_path").build()))
                .build())
                .fr(TmdbOTTDto.builder()
                        .buy(List.of(Provider.builder()
                                .provider_name("provider_name")
                                .logo_path("logo_path").build()))
                        .flatrate(List.of(Provider.builder()
                                .provider_name("provider_name")
                                .logo_path("logo_path").build()))
                        .build())
                .us(TmdbOTTDto.builder()
                        .buy(List.of(Provider.builder()
                                .provider_name("provider_name")
                                .logo_path("logo_path").build()))
                        .flatrate(List.of(Provider.builder()
                                .provider_name("provider_name")
                                .logo_path("logo_path").build()))
                        .build())
                .gb(TmdbOTTDto.builder()
                        .buy(List.of(Provider.builder()
                                .provider_name("provider_name")
                                .logo_path("logo_path").build()))
                        .flatrate(List.of(Provider.builder()
                                .provider_name("provider_name")
                                .logo_path("logo_path").build()))
                        .build())
                .build();


        //WHEN

        MovieAndSeriesDetails actual = movieAndSeriesDetailsService.getDetails("imdbID");

        //THEN

        assertThat(actual, is(expected));


    }

}
