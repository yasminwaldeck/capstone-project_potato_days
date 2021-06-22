package service;

import de.neuefische.backend.config.TMDbConfig;
import de.neuefische.backend.model.OMDb.OmdbResponseDto;
import de.neuefische.backend.model.TMDb.*;
import de.neuefische.backend.service.OmdbApiService;
import de.neuefische.backend.service.TmdbApiService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.Matchers.is;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class TmbdApiServiceTest {

    private final RestTemplate mockedTemplate = mock(RestTemplate.class);
    private final TMDbConfig tmdbConfig = new TMDbConfig();
    private final TmdbApiService tmdbApiService = new TmdbApiService(mockedTemplate, tmdbConfig);

    @Test
    public void getDetailsShouldReturnDetailsForMovie(){

        //GIVEN
        TmdbDto response = TmdbDto.builder()
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

        TmdbDto expected = TmdbDto.builder()
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
                "https://api.themoviedb.org/3/movie/id?api_key=" + tmdbConfig.getKey(), TmdbDto.class))
                .thenReturn(ResponseEntity.ok(response));

        //WHEN

        TmdbDto actual = tmdbApiService.getDetails("id", "movie");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/movie/id?api_key=" + tmdbConfig.getKey()),
                TmdbDto.class);

    }

    @Test
    public void getDetailsShouldReturnDetailsForSeries(){

        //GIVEN
        TmdbDto response = TmdbDto.builder()
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

        TmdbDto expected = TmdbDto.builder()
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
                "https://api.themoviedb.org/3/tv/id?api_key=" + tmdbConfig.getKey(), TmdbDto.class))
                .thenReturn(ResponseEntity.ok(response));

        //WHEN

        TmdbDto actual = tmdbApiService.getDetails("id", "series");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/tv/id?api_key=" + tmdbConfig.getKey()),
                TmdbDto.class);

    }

    @Test
    public void getIDshouldgettheIdwhenImdbIdisEntered(){

        //GIVEN
        TmdbDto tmdbDto = TmdbDto.builder()
                .first_air_date("first_air_date")
                .id("tmdbid")
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

        TmdbIdDto response = TmdbIdDto.builder().movie_results(List.of(tmdbDto)).tv_results(List.of()).build();

        String expected = "tmdbid";

        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/find/imdbid?external_source=imdb_id&api_key=" + tmdbConfig.getKey(), TmdbIdDto.class))
                .thenReturn(ResponseEntity.ok(response));

        //WHEN

        String actual = tmdbApiService.getId("imdbid");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/find/imdbid?external_source=imdb_id&api_key=" + tmdbConfig.getKey()),
                TmdbIdDto.class);
    }

    @Test
    public void getOTTshouldgetOTTinfo(){

        //GIVEN

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

        TmdbOTTbyCountryResponseDto expected = TmdbOTTbyCountryResponseDto.builder()
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

        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/movie/id/watch/providers?api_key=" + tmdbConfig.getKey(), TmdbResponseDto.class))
                .thenReturn(ResponseEntity.ok(tmdbResponseDto));


        //WHEN

        TmdbOTTbyCountryResponseDto actual = tmdbApiService.getOTT("id", "movie");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/movie/id/watch/providers?api_key=" + tmdbConfig.getKey()),
                TmdbResponseDto.class);
    }

}
