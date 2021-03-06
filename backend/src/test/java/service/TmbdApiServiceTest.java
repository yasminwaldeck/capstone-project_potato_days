package service;

import de.neuefische.backend.config.TMDbConfig;
import de.neuefische.backend.model.Episode;
import de.neuefische.backend.model.TMDb.*;
import de.neuefische.backend.service.TmdbApiService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.hamcrest.Matchers.is;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                "https://api.themoviedb.org/3/movie/id?api_key=" + tmdbConfig.getKey() + "&append_to_response=external_ids", TmdbDto.class))
                .thenReturn(ResponseEntity.ok(response));

        //WHEN

        TmdbDto actual = tmdbApiService.getDetails("id", "movie");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/movie/id?api_key=" + tmdbConfig.getKey() + "&append_to_response=external_ids"),
                TmdbDto.class);

    }

    @Test
    public void getDetailsShouldReturnDetailsForSeries(){

        //GIVEN
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
                "https://api.themoviedb.org/3/tv/id?api_key=" + tmdbConfig.getKey() + "&append_to_response=external_ids", TmdbDto.class))
                .thenReturn(ResponseEntity.ok(tmdbDto));

        //WHEN

        TmdbDto actual = tmdbApiService.getDetails("id", "series");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/tv/id?api_key=" + tmdbConfig.getKey() + "&append_to_response=external_ids"),
                TmdbDto.class);

    }

    @Test
    public void getDetailsShouldReturnNullIfIdIsEmpty(){
        //GIVEN
        //WHEN
        TmdbDto actual = tmdbApiService.getDetails("", "series");
        //THEN
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void getDetailsShouldThrowIllegalArgumentException(){
        //GIVEN
        String expected = "Unexpected value: bla";
        //WHEN
        Exception exception = assertThrows(IllegalStateException.class, () ->
            tmdbApiService.getDetails("id", "bla"));
        String actual = exception.getMessage();

        //THEN
        assertThat(actual, is(expected));
    }

    @Test
    public void getIDshouldgettheIdwhenImdbIdisEnteredForMovie(){

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
    public void getIDshouldgettheIdwhenImdbIdisEnteredForShows(){

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

        TmdbIdDto response = TmdbIdDto.builder().tv_results(List.of(tmdbDto)).movie_results(List.of()).build();

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
    public void getIDshouldReturnNullIfTVandMovieIsEmpty(){

        //GIVEN
        TmdbIdDto response = TmdbIdDto.builder().movie_results(List.of()).tv_results(List.of()).build();
        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/find/imdbid?external_source=imdb_id&api_key=" + tmdbConfig.getKey(), TmdbIdDto.class))
                .thenReturn(ResponseEntity.ok(response));

        //WHEN
        String actual = tmdbApiService.getId("imdbid");

        //THEN
        assertThat(actual, is(nullValue()));
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


    @Test
    public void getCreditsShouldGetCreduts() {

        //GIVEN

        TmdbCreditDto response = TmdbCreditDto.builder()
                .crew(
                        List.of(Credit.builder().name("name").department("department").profile_path("profile_path").build()))
                .cast(
                        List.of(Credit.builder().name("name").character("character").profile_path("profile_path").build()))
                .build();

        TmdbCreditDto expected = TmdbCreditDto.builder()
                .crew(
                        List.of(Credit.builder().name("name").department("department").profile_path("profile_path").build()))
                .cast(
                        List.of(Credit.builder().name("name").character("character").profile_path("profile_path").build()))
                .build();

        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/movie/id/credits?api_key=" + tmdbConfig.getKey(), TmdbCreditDto.class))
                .thenReturn(ResponseEntity.ok(response));

        //WHEN

        TmdbCreditDto actual = tmdbApiService.getCredits("id", "movie");

        //THEN

        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/movie/id/credits?api_key=" + tmdbConfig.getKey()),
                TmdbCreditDto.class);
    }

    @Test
    public void getTrendingShouldReturnAListOfIds(){
        //GIVEN
        TmdbTrendingResponseDto response = TmdbTrendingResponseDto
                .builder()
                .results(List.of(TmdbDto.builder().id("id1").build(),
                        TmdbDto.builder().id("id2").build()))
                .build();

        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/trending/movie/day?api_key=" + tmdbConfig.getKey(), TmdbTrendingResponseDto.class))
                .thenReturn(ResponseEntity.ok(response));

        List<String> expected = List.of("id1", "id2");
        //WHEN
        List<String> actual = tmdbApiService.getTrending("day", "movie");

        //THEN
        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/trending/movie/day?api_key=" + tmdbConfig.getKey()),
                TmdbTrendingResponseDto.class);
    }

    @Test
    public void getImdbIdShouldReturnTheImdbId(){
        //GIVEN
        TmdbDto tmdbDto = TmdbDto.builder()
                .external_ids(TmdbExternalIds.builder().imdb_id("imdbid").build())
                .build();

        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/tv/id?api_key=" + tmdbConfig.getKey() + "&append_to_response=external_ids", TmdbDto.class))
                .thenReturn(ResponseEntity.ok(tmdbDto));

        String expected = "imdbid";

        //WHEN
        String actual = tmdbApiService.getImdbId("id","series");

        //THEN
        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/tv/id?api_key=" + tmdbConfig.getKey()+ "&append_to_response=external_ids"),
                TmdbDto.class);
    }

    @Test
    public void getEpisodesShouldReturnTheEpisodes(){
        //GIVEN
        Season expected = Season.builder().episodes(List.of(Episode.builder().build())).build();
        Season response = Season.builder().episodes(List.of(Episode.builder().build())).build();
        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/tv/id/season/1?api_key=" + tmdbConfig.getKey(), Season.class))
                .thenReturn(ResponseEntity.ok(response));

        //WHEN
        Season actual = tmdbApiService.getEpisodes("id", "1");
        //THEN
        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/tv/id/season/1?api_key=" + tmdbConfig.getKey()),
                Season.class);
    }

    @Test
    public void getUrlPartForMovie(){
        //GIVEN
        String expected = "movie/";
        //WHEN
        String actual = tmdbApiService.getUrlPart("movie");
        //THEN
        assertThat(actual, is(expected));
    }

    @Test
    public void getUrlPartForSeries(){
        //GIVEN
        String expected = "tv/";
        //WHEN
        String actual = tmdbApiService.getUrlPart("series");
        //THEN
        assertThat(actual, is(expected));
    }

    @Test
    public void getUrlPartShouldThrowException(){
        //GIVEN
        String expected = "Unexpected value: bla";
        //WHEN
        Exception exception = assertThrows(IllegalStateException.class, () ->
                tmdbApiService.getUrlPart("bla"));
        String actual = exception.getMessage();

        //THEN
        assertThat(actual, is(expected));
    }

}
