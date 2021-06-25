package service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.MovieAndSeriesWatched;
import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.model.OMDb.OmdbOverviewDto;
import de.neuefische.backend.repo.WatchHistoryRepo;
import de.neuefische.backend.service.OmdbApiService;
import de.neuefische.backend.service.WatchHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class WatchHistoryServiceTest {

    private final WatchHistoryRepo watchHistoryRepo = mock(WatchHistoryRepo.class);
    private final OMDbConfig omDbConfig = new OMDbConfig();
    private final RestTemplate mockedTemplate = mock(RestTemplate.class);
    private final OmdbApiService omdbApiService = new OmdbApiService(omDbConfig, mockedTemplate);
    private final WatchHistoryService watchHistoryService = new WatchHistoryService(watchHistoryRepo, omdbApiService);

    @Test
    public void addMovieToHistoryDatabaseTest(){
        //GIVEN
        MovieAndSeriesWatched itemToAdd = MovieAndSeriesWatched.builder().imdbID("imdbID").type("type").build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=imdbID", OmdbOverviewDto.class))
                .thenReturn(ResponseEntity.ok(OmdbOverviewDto.builder().imdbID("imdbID").build()));

        //WHEN
        watchHistoryService.addToWatchHistory(itemToAdd);

        //THEN
        verify(watchHistoryRepo).save(MovieAndSeriesWatched.builder().imdbID("imdbID").type("type").build());
    }

    @Test
    public void deleteMovieToHistoryDatabaseTest(){
        //GIVEN


        //WHEN
        watchHistoryService.removeFromWatchHistory("imdbID");

        //THEN
        verify(watchHistoryRepo).deleteByImdbID("imdbID");
    }

    @Test
    public void getWatchHistoryByTypeShouldReturnWatchlistByType() {
        // GIVEN
        when(watchHistoryRepo.findByType("type")).thenReturn(List.of(
                MovieAndSeriesWatched.builder().imdbID("id1").type("type").build(),
                MovieAndSeriesWatched.builder().imdbID("id2").type("type").build()
        ));

        OmdbOverviewDto omdbOverviewDto1 = new OmdbOverviewDto(
                "title", "year", "id1", "poster", "type"
        );
        OmdbOverviewDto omdbOverviewDto2 = new OmdbOverviewDto(
                "title", "year", "id2", "poster", "type"
        );

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=id1", OmdbOverviewDto.class))
                .thenReturn(ResponseEntity.ok(omdbOverviewDto1));

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=id2", OmdbOverviewDto.class))
                .thenReturn(ResponseEntity.ok(omdbOverviewDto2));

        // WHEN
        List<OmdbOverview> items = watchHistoryService.getWatchHistoryByType(Optional.of("type"));

        // THEN
        assertThat(items, is(List.of(
                new OmdbOverview(
                        "title", "year", "id1", "poster", "type"
                ),
                new OmdbOverview(
                        "title", "year", "id2", "poster", "type"
                )
        )));
        verify(watchHistoryRepo).findByType("type");
    }
}
