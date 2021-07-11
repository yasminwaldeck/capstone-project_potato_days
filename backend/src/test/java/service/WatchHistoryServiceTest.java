package service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.config.TMDbConfig;
import de.neuefische.backend.model.Episode;
import de.neuefische.backend.model.MovieAndSeries;
import de.neuefische.backend.model.OMDb.OmdbDetails;
import de.neuefische.backend.model.OMDb.OmdbDetailsDto;
import de.neuefische.backend.repo.MovieAndSeriesRepo;
import de.neuefische.backend.service.OmdbApiService;
import de.neuefische.backend.service.TmdbApiService;
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

    private final MovieAndSeriesRepo movieAndSeriesRepo = mock(MovieAndSeriesRepo.class);
    private final OMDbConfig omDbConfig = new OMDbConfig();
    private final TMDbConfig tmDbConfig = new TMDbConfig();
    private final RestTemplate mockedTemplate = mock(RestTemplate.class);
    private final OmdbApiService omdbApiService = new OmdbApiService(omDbConfig, mockedTemplate);
    private final TmdbApiService tmdbApiService = new TmdbApiService(mockedTemplate, tmDbConfig);
    private final WatchHistoryService watchHistoryService = new WatchHistoryService(movieAndSeriesRepo, omdbApiService, tmdbApiService);

    @Test
    public void addMovieToHistoryDatabaseWhenMovieIsNotSavedYet(){
        //GIVEN
        MovieAndSeries itemToAdd = MovieAndSeries.builder().imdbID("imdbID").type("type").build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=imdbID", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(OmdbDetailsDto.builder().imdbID("imdbID").build()));

        when(movieAndSeriesRepo.findById("imdbID_name"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(MovieAndSeries.builder().id("imdbID_name").imdbID("imdbID").type("type").build()));

        //WHEN
        watchHistoryService.addToWatchHistory("name", itemToAdd);

        //THEN
        verify(movieAndSeriesRepo, times(2)).findById("imdbID_name");
        verify(movieAndSeriesRepo).save(MovieAndSeries.builder().id("imdbID_name").username("name").imdbID("imdbID").type("type").watchHistory(true).build());
    }

    @Test
    public void addMovieToHistoryDatabaseWhenMovieIsInDatabase(){
        //GIVEN
        MovieAndSeries itemToAdd = MovieAndSeries.builder().imdbID("imdbID").type("type").build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=imdbID", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(OmdbDetailsDto.builder().imdbID("imdbID").build()));

        when(movieAndSeriesRepo.findById("imdbID_name"))
                .thenReturn(Optional.of(MovieAndSeries.builder().id("imdbID_name").username("name").imdbID("imdbID").type("type").watchHistory(true).watchlist(false).build()));

        //WHEN
        watchHistoryService.addToWatchHistory("name", itemToAdd);

        //THEN
        verify(movieAndSeriesRepo).save(MovieAndSeries.builder().id("imdbID_name").username("name").imdbID("imdbID").type("type").watchHistory(true).watchHistory(true).build());
    }

    @Test
    public void removeFromWatchHistorydeleteMovieFromHistoryDatabaseTest(){
        //GIVEN
        when(movieAndSeriesRepo.findById("imdbID_name")).thenReturn(Optional.of(MovieAndSeries.builder().id("imdbID_name").username("name").imdbID("imdbID").watchHistory(true).watchlist(false).build()));

        //WHEN
        watchHistoryService.removeFromWatchHistory("name", "imdbID");

        //THEN
        verify(movieAndSeriesRepo).deleteById("imdbID_name");
    }

    @Test
    public void removeFromWatchHistorysetWatchHistoryFalse(){
        //GIVEN
        when(movieAndSeriesRepo.findById("imdbID_name")).thenReturn(Optional.of(MovieAndSeries.builder().id("imdbID_name").username("name").imdbID("imdbID").watchHistory(true).watchlist(true).build()));

        //WHEN
        watchHistoryService.removeFromWatchHistory("name", "imdbID");

        //THEN
        verify(movieAndSeriesRepo).save(MovieAndSeries.builder().id("imdbID_name").username("name").imdbID("imdbID").watchHistory(false).watchlist(true).build());
    }

    @Test
    public void getWatchHistoryByTypeShouldReturnWatchlistByType() {
        // GIVEN
        when(movieAndSeriesRepo.findMovieAndSeriesByWatchHistoryIsTrueAndTypeAndUsername( "type", "name")).thenReturn(List.of(
                MovieAndSeries.builder().imdbID("id1").username("name").type("type").build(),
                MovieAndSeries.builder().imdbID("id2").username("name").type("type").build()
        ));

        OmdbDetailsDto omdbDetailsDto1 = OmdbDetailsDto.builder().title("title1").build();
        OmdbDetailsDto omdbDetailsDto2 = OmdbDetailsDto.builder().title("title2").build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=id1", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(omdbDetailsDto1));

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=id2", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(omdbDetailsDto2));

        // WHEN
        List<OmdbDetails> items = watchHistoryService.getWatchHistoryByType("name", Optional.of("type"));

        // THEN
        assertThat(items, is(List.of(
                OmdbDetails.builder().title("title1").build(),
                OmdbDetails.builder().title("title2").build()
        )));
        verify(movieAndSeriesRepo).findMovieAndSeriesByWatchHistoryIsTrueAndTypeAndUsername("type", "name");
    }

    @Test
    public void getWatchHistoryByTypeShouldReturnTotalWatchlistWhenTypeIsEmptry() {
        // GIVEN
        when(movieAndSeriesRepo.findMovieAndSeriesByWatchHistoryIsTrueAndUsername("name")).thenReturn(List.of(
                MovieAndSeries.builder().imdbID("id1").username("name").type("type").build(),
                MovieAndSeries.builder().imdbID("id2").username("name").type("type2").build()
        ));

        OmdbDetailsDto omdbDetailsDto1 = OmdbDetailsDto.builder().title("title1").build();
        OmdbDetailsDto omdbDetailsDto2 = OmdbDetailsDto.builder().title("title2").build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=id1", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(omdbDetailsDto1));

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=id2", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(omdbDetailsDto2));

        // WHEN
        List<OmdbDetails> items = watchHistoryService.getWatchHistoryByType("name", Optional.empty());

        // THEN
        assertThat(items, is(List.of(
                OmdbDetails.builder().title("title1").build(),
                OmdbDetails.builder().title("title2").build()
        )));
        verify(movieAndSeriesRepo).findMovieAndSeriesByWatchHistoryIsTrueAndUsername("name");
    }

    @Test
    public void getWatchHistoryEpisodesReturnEmptyListIfIdIsNotInDB(){
        //GIVEN
        when(movieAndSeriesRepo.existsById("id_name")).thenReturn(false);
        //WHEN
        List<Episode> actual = watchHistoryService.getWatchHistoryEpisodes("name", "id", "1");
        //THEN
        assertThat(actual, is(List.of()));
    }

    @Test
    public void getWatchHistoryEpisodesReturnListIfIdIsInDB(){
        //GIVEN
        when(movieAndSeriesRepo.existsById("id_name")).thenReturn(true);
        when(movieAndSeriesRepo.findById("id_name")).thenReturn(
                Optional.of(
                        MovieAndSeries.builder().
                                watchedEpisodes(
                                        List.of(
                                                Episode.builder()
                                                        .season_number(1)
                                                        .episode_number(1)
                                                        .build(),
                                        Episode.builder()
                                                .season_number(2)
                                                .episode_number(1)
                                                .build()))
                                .build()
                )
        );
        //WHEN
        List<Episode> actual = watchHistoryService.getWatchHistoryEpisodes("name", "id", "1");
        //THEN
        assertThat(actual, is(List.of(Episode.builder()
                .season_number(1)
                .episode_number(1)
                .build())));
    }

    @Test
    public void getWatchHistoryAllEpisodesReturnEmptyListIfIdIsNotInDB(){
        //GIVEN
        when(movieAndSeriesRepo.existsById("id_name")).thenReturn(false);
        //WHEN
        List<Episode> actual = watchHistoryService.getWatchHistoryAllEpisodes("id_name");
        //THEN
        assertThat(actual, is(List.of()));
    }

    @Test
    public void getWatchHistoryAllEpisodesReturnListIfIdIsInDB(){
        //GIVEN
        when(movieAndSeriesRepo.existsById("id_name")).thenReturn(true);
        when(movieAndSeriesRepo.findById("id_name")).thenReturn(
                Optional.of(
                        MovieAndSeries.builder().
                                watchedEpisodes(
                                        List.of(
                                                Episode.builder()
                                                        .season_number(1)
                                                        .episode_number(1)
                                                        .build(),
                                                Episode.builder()
                                                        .season_number(2)
                                                        .episode_number(1)
                                                        .build()))
                                .build()
                )
        );
        //WHEN
        List<Episode> actual = watchHistoryService.getWatchHistoryAllEpisodes("id_name");
        //THEN
        assertThat(actual, is(List.of(Episode.builder()
                        .season_number(1)
                        .episode_number(1)
                        .build(),
                Episode.builder()
                        .season_number(2)
                        .episode_number(1)
                        .build())));
    }
}
