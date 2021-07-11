package service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.config.TMDbConfig;
import de.neuefische.backend.model.OMDb.OmdbDetails;
import de.neuefische.backend.model.OMDb.OmdbDetailsDto;
import de.neuefische.backend.model.TMDb.TmdbDto;
import de.neuefische.backend.model.TMDb.TmdbExternalIds;
import de.neuefische.backend.model.TMDb.TmdbTrendingResponseDto;
import de.neuefische.backend.service.OmdbApiService;
import de.neuefische.backend.service.TmdbApiService;
import de.neuefische.backend.service.TrendingService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;

public class TrendingServiceTest {
    private final RestTemplate mockedTemplate = mock(RestTemplate.class);
    private final OMDbConfig omdbConfig = new OMDbConfig();
    private final TMDbConfig tmdbConfig = new TMDbConfig();
    private final OmdbApiService omdbApiService = new OmdbApiService(omdbConfig, mockedTemplate);
    private final TmdbApiService tmdbApiService = new TmdbApiService(mockedTemplate, tmdbConfig);
    private final TrendingService trendingService = new TrendingService(tmdbApiService, omdbApiService);

    @Test
    public void findTrendingShouldReturnTrendingMoviesForTheDay(){
        //GIVEN
        List<OmdbDetails> expected = List.of(OmdbDetails.builder().imdbID("imdbid").build());

        //getTrending
        TmdbTrendingResponseDto response = TmdbTrendingResponseDto
                .builder()
                .results(List.of(TmdbDto.builder().id("id1").build()))
                .build();
        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/trending/movie/day?api_key=" + tmdbConfig.getKey(), TmdbTrendingResponseDto.class))
                .thenReturn(ResponseEntity.ok(response));

        //getImdbId

        TmdbDto tmdbDto = TmdbDto.builder()
                .external_ids(TmdbExternalIds.builder().imdb_id("imdbid").build())
                .build();

        when(mockedTemplate.getForEntity(
                "https://api.themoviedb.org/3/movie/id1?api_key=" + tmdbConfig.getKey() + "&append_to_response=external_ids", TmdbDto.class))
                .thenReturn(ResponseEntity.ok(tmdbDto));

        //getOverviewById

        OmdbDetailsDto omdbDetailsDto = OmdbDetailsDto.builder().imdbID("imdbid").build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omdbConfig.getKey() + "&i=imdbid", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(omdbDetailsDto));

        //WHEN
        List<OmdbDetails> actual = trendingService.findTrending("day", "movie");

        //THEN
        assertThat(actual, is(expected));
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/movie/id1?api_key=" + tmdbConfig.getKey() + "&append_to_response=external_ids"),
                TmdbDto.class);
        verify(mockedTemplate).getForEntity(
                ("https://api.themoviedb.org/3/trending/movie/day?api_key=" + tmdbConfig.getKey()),
                TmdbTrendingResponseDto.class);

        verify(mockedTemplate).getForEntity(
                ("https://www.omdbapi.com/?apikey=" + omdbConfig.getKey() + "&i=imdbid"),
                OmdbDetailsDto.class);
    }

    @Test
    public void findTrendingShouldReturnNullIfTimewindowIsEmpty(){
        //GIVEN
        //
        List<OmdbDetails> actual = trendingService.findTrending("", "series");
        //THEN
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void findTrendingShouldReturnNullIfTypeIsEmpty(){
        //GIVEN
        //
        List<OmdbDetails> actual = trendingService.findTrending("day", "");
        //THEN
        assertThat(actual, is(nullValue()));
    }

}
