package service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.model.*;
import de.neuefische.backend.model.OMDb.OmdbOverview;
import de.neuefische.backend.model.OMDb.OmdbOverviewDto;
import de.neuefische.backend.repo.MovieAndSeriesRepo;
import de.neuefische.backend.service.OmdbApiService;
import de.neuefische.backend.service.WatchlistService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.*;

public class WatchlistServiceTest {

    private final MovieAndSeriesRepo movieAndSeriesRepo = mock(MovieAndSeriesRepo.class);
    private final OMDbConfig omDbConfig = new OMDbConfig();
    private final RestTemplate mockedTemplate = mock(RestTemplate.class);
    private final OmdbApiService omdbApiService = new OmdbApiService(omDbConfig, mockedTemplate);
    private final WatchlistService watchlistService = new WatchlistService(movieAndSeriesRepo, omdbApiService);

    @Test
    public void addMovieToDatabaseTest(){
        //GIVEN
        MovieAndSeries itemToAdd = MovieAndSeries.builder().imdbID("imdbID").type("type").build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=imdbID", OmdbOverviewDto.class))
                .thenReturn(ResponseEntity.ok(OmdbOverviewDto.builder().imdbID("imdbID").build()));

        when(movieAndSeriesRepo.findById("imdbID_name")).thenReturn(Optional.of(MovieAndSeries.builder().imdbID("imdbID").type("type").watchHistory(true).watchlist(false).build()));

        //WHEN
        watchlistService.addToWatchlist("name", itemToAdd);

        //THEN
        verify(movieAndSeriesRepo).save(MovieAndSeries.builder().imdbID("imdbID").type("type").watchHistory(true).watchlist(true).build());
    }

    @Test
    public void deleteMovieToDatabaseTest(){
        //GIVEN

        when(movieAndSeriesRepo.findById("imdbID_name")).thenReturn(Optional.of(MovieAndSeries.builder().imdbID("imdbID").watchHistory(false).watchlist(true).build()));


        //WHEN
        watchlistService.removeFromWatchlist("name", "imdbID");

        //THEN
        verify(movieAndSeriesRepo).deleteById("imdbID_name");
    }

    @Test
    public void updateInsteadOfDeletingMovieToDatabaseTest(){
        //GIVEN
        when(movieAndSeriesRepo.findById("imdbID_name")).thenReturn(Optional.of(MovieAndSeries.builder().imdbID("imdbID").watchHistory(true).watchlist(true).build()));

        //WHEN
        watchlistService.removeFromWatchlist("name", "imdbID");

        //THEN
        verify(movieAndSeriesRepo).save(MovieAndSeries.builder().imdbID("imdbID").watchHistory(true).watchlist(false).build());
    }

    @Test
    public void getWatchlistByTypeShouldReturnWatchlistByType() {
        // GIVEN
        when(movieAndSeriesRepo.findMovieAndSeriesByWatchlistIsTrueAndTypeAndUsername("name", "type")).thenReturn(List.of(
                MovieAndSeries.builder().imdbID("id1").username("name").type("type").build(),
                MovieAndSeries.builder().imdbID("id2").username("name").type("type").build()
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
        List<OmdbOverview> items = watchlistService.getWatchlistByType("name", Optional.of("type"));

        // THEN
        assertThat(items, is(List.of(
                new OmdbOverview(
                        "title", "year", "id1", "poster", "type"
                ),
                new OmdbOverview(
                        "title", "year", "id2", "poster", "type"
                )
        )));
        verify(movieAndSeriesRepo).findMovieAndSeriesByWatchlistIsTrueAndTypeAndUsername("name", "type");
    }


}
