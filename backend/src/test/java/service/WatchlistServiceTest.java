package service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.model.*;
import de.neuefische.backend.model.OMDb.OmdbDetails;
import de.neuefische.backend.model.OMDb.OmdbDetailsDto;
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
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;

public class WatchlistServiceTest {

    private final MovieAndSeriesRepo movieAndSeriesRepo = mock(MovieAndSeriesRepo.class);
    private final OMDbConfig omDbConfig = new OMDbConfig();
    private final RestTemplate mockedTemplate = mock(RestTemplate.class);
    private final OmdbApiService omdbApiService = new OmdbApiService(omDbConfig, mockedTemplate);
    private final WatchlistService watchlistService = new WatchlistService(movieAndSeriesRepo, omdbApiService);

    @Test
    public void addMovieToDatabaseWhenIDExists(){
        //GIVEN
        MovieAndSeries itemToAdd = MovieAndSeries.builder().imdbID("imdbID").type("type").build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=imdbID", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(OmdbDetailsDto.builder().imdbID("imdbID").build()));

        when(movieAndSeriesRepo.findById("imdbID_name")).thenReturn(Optional.of(MovieAndSeries.builder().imdbID("imdbID").type("type").watchHistory(true).watchlist(false).build()));
        when(movieAndSeriesRepo.existsById("imdbID_name")).thenReturn(true);

        //WHEN
        watchlistService.addToWatchlist("name", itemToAdd);

        //THEN
        verify(movieAndSeriesRepo).save(MovieAndSeries.builder().imdbID("imdbID").type("type").watchHistory(true).watchlist(true).build());
    }

    @Test
    public void addMovieToDatabaseWhenIDDoesNotExist(){
        //GIVEN
        MovieAndSeries itemToAdd = MovieAndSeries.builder().imdbID("imdbID").type("type").build();

        when(mockedTemplate.getForEntity(
                "https://www.omdbapi.com/?apikey=" + omDbConfig.getKey() + "&i=imdbID", OmdbDetailsDto.class))
                .thenReturn(ResponseEntity.ok(OmdbDetailsDto.builder().imdbID("imdbID").build()));
        when(movieAndSeriesRepo.existsById("imdbID_name")).thenReturn(false);
        when(movieAndSeriesRepo.findById("imdbID_name")).thenReturn(Optional.of(MovieAndSeries.builder().imdbID("imdbID").type("type").watchHistory(true).watchlist(false).build()));


        //WHEN
        watchlistService.addToWatchlist("name", itemToAdd);

        //THEN
        verify(movieAndSeriesRepo).save(MovieAndSeries.builder().id("imdbID_name").username("name").imdbID("imdbID").type("type").watchHistory(false).watchlist(true).build());
    }

    @Test
    public void removeFromWatchlistWhenWatchHistoryIsFalse(){
        //GIVEN

        when(movieAndSeriesRepo.findById("imdbID_name")).thenReturn(Optional.of(MovieAndSeries.builder().imdbID("imdbID").watchHistory(false).watchlist(true).build()));
        when(movieAndSeriesRepo.existsById("imdbID_name")).thenReturn(true);

        //WHEN
        watchlistService.removeFromWatchlist("name", "imdbID");

        //THEN
        verify(movieAndSeriesRepo).deleteById("imdbID_name");
    }

    @Test
    public void removeFromWatchlistWhenWatchHistoryIsTrue(){
        //GIVEN
        when(movieAndSeriesRepo.findById("imdbID_name")).thenReturn(Optional.of(MovieAndSeries.builder().imdbID("imdbID").watchHistory(true).watchlist(true).build()));
        when(movieAndSeriesRepo.existsById("imdbID_name")).thenReturn(true);

        //WHEN
        watchlistService.removeFromWatchlist("name", "imdbID");

        //THEN
        verify(movieAndSeriesRepo).save(MovieAndSeries.builder().imdbID("imdbID").watchHistory(true).watchlist(false).build());
    }

    @Test
    public void getWatchlistByTypeShouldReturnWatchlistByTypeUnfiltered() {
        // GIVEN
        when(movieAndSeriesRepo.findMovieAndSeriesByWatchlistIsTrueAndTypeAndUsername("type", "name")).thenReturn(List.of(
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
        List<OmdbDetails> items = watchlistService.getWatchlistByType("name", Optional.of("type"), "false");

        // THEN
        assertThat(items, is(List.of(
                OmdbDetails.builder().title("title1").build(),
                OmdbDetails.builder().title("title2").build()
        )));
        verify(movieAndSeriesRepo).findMovieAndSeriesByWatchlistIsTrueAndTypeAndUsername("type", "name");
    }

    @Test
    public void getWatchlistByTypeShouldReturnTotalWatchlistWhenTypeIsEmptyUnfiltered() {
        // GIVEN
        when(movieAndSeriesRepo.findMovieAndSeriesByWatchlistIsTrueAndUsername("name")).thenReturn(List.of(
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
        List<OmdbDetails> items = watchlistService.getWatchlistByType("name", Optional.empty(), "false");

        // THEN
        assertThat(items, is(List.of(
                OmdbDetails.builder().title("title1").build(),
                OmdbDetails.builder().title("title2").build()
        )));
        verify(movieAndSeriesRepo).findMovieAndSeriesByWatchlistIsTrueAndUsername("name");
    }

    @Test
    public void getWatchlistByTypeShouldReturnWatchlistByTypeFiltered() {
        // GIVEN
        when(movieAndSeriesRepo.findMovieAndSeriesByWatchlistIsTrueAndWatchHistoryIsFalseAndTypeAndUsername("type", "name")).thenReturn(List.of(
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
        List<OmdbDetails> items = watchlistService.getWatchlistByType("name", Optional.of("type"), "true");

        // THEN
        assertThat(items, is(List.of(
                OmdbDetails.builder().title("title1").build(),
                OmdbDetails.builder().title("title2").build()
        )));
        verify(movieAndSeriesRepo).findMovieAndSeriesByWatchlistIsTrueAndWatchHistoryIsFalseAndTypeAndUsername("type", "name");
    }

    @Test
    public void getWatchlistByTypeShouldReturnTotalWatchlistWhenTypeIsEmptyFiltered() {
        // GIVEN
        when(movieAndSeriesRepo.findMovieAndSeriesByWatchlistIsTrueAndWatchHistoryIsFalseAndUsername("name")).thenReturn(List.of(
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
        List<OmdbDetails> items = watchlistService.getWatchlistByType("name", Optional.empty(), "true");

        // THEN
        assertThat(items, is(List.of(
                OmdbDetails.builder().title("title1").build(),
                OmdbDetails.builder().title("title2").build()
        )));
        verify(movieAndSeriesRepo).findMovieAndSeriesByWatchlistIsTrueAndWatchHistoryIsFalseAndUsername("name");
    }

    @Test
    public void getNameFromWatchlistReturnRecommendedBy(){
        //GIVEN
        when(movieAndSeriesRepo.findById("id_name")).thenReturn(Optional.of(
                MovieAndSeries.builder().recommendedBy("recommended").build())
        );
        when(movieAndSeriesRepo.existsById("id_name")).thenReturn(true);

        //WHEN

        String actual = watchlistService.getNameFromWatchlist("name", "id");

        //THEN
        assertThat(actual, is("recommended"));
        verify(movieAndSeriesRepo).findById("id_name");
        verify(movieAndSeriesRepo).existsById("id_name");
    }

    @Test
    public void getNameFromWatchlistReturnNull(){
        //GIVEN
        when(movieAndSeriesRepo.existsById("id_name")).thenReturn(false);

        //WHEN

        String actual = watchlistService.getNameFromWatchlist("name", "id");

        //THEN
        assertThat(actual, is(nullValue()));
        verify(movieAndSeriesRepo).existsById("id_name");
    }

    @Test
    public void addNameToWatchListWhenIDExists(){
        //GIVEN
        when(movieAndSeriesRepo.existsById("id_name")).thenReturn(true);
        when(movieAndSeriesRepo.findById("id_name")).thenReturn(Optional.of(
                MovieAndSeries.builder().build())
        );

        MovieAndSeries itemToAdd = MovieAndSeries.builder().imdbID("id").recommendedBy("bla").build();

        //WHEN
        String actual = watchlistService.addNameToWatchlist("name", itemToAdd);

        //THEN
        assertThat(actual, is("bla"));
        verify(movieAndSeriesRepo).save(MovieAndSeries.builder().recommendedBy("bla").build());

    }

    @Test
    public void addNameToWatchListWhenIDDoesNotExist(){
        //GIVEN
        when(movieAndSeriesRepo.existsById("id_name")).thenReturn(false);

        MovieAndSeries itemToAdd = MovieAndSeries.builder().imdbID("id").recommendedBy("bla").build();

        //WHEN
        String actual = watchlistService.addNameToWatchlist("name", itemToAdd);

        //THEN
        assertThat(actual, is(nullValue()));
    }


    @Test
    public void removeNameFromWatchList(){
        //GIVEN
        when(movieAndSeriesRepo.existsById("id_name")).thenReturn(true);

        when(movieAndSeriesRepo.findById("id_name")).thenReturn(Optional.of(
                MovieAndSeries.builder().recommendedBy("bla").build()
        ));

        //WHEN
        watchlistService.removeNameFromWatchlist("name", "id");

        //THEN
        verify(movieAndSeriesRepo).save(MovieAndSeries.builder().build());
    }

    @Test
    public void getRecommendedByStats(){
        //GIVEN
        when(movieAndSeriesRepo.findByUsername("name")).thenReturn(List.of(
                MovieAndSeries.builder().imdbID("id").recommendedBy("Hans").build(),
                MovieAndSeries.builder().imdbID("id2").recommendedBy("Hans").build(),
                MovieAndSeries.builder().imdbID("id3").recommendedBy("Ferdinand").build()
        ));

        List<Stats> expected = List.of(
                Stats.builder()
                        .name("Hans")
                        .recommendations(List.of("id", "id2"))
                        .number(2)
                        .build(),
                Stats.builder()
                        .name("Ferdinand")
                        .recommendations(List.of("id3"))
                        .number(1)
                        .build()
        );

        //WHEN

        List<Stats> actual = watchlistService.getRecommendedByStats("name");

        //THEN
        assertThat(actual, is(expected));
    }

    @Test
    public void getRandomWatchlistEntryButListIsEmpty(){
        //GIVEN
        when(movieAndSeriesRepo.findByUsernameAndWatchHistoryIsFalseAndWatchingIsFalse("name"))
                .thenReturn(List.of());
        //WHEN
        Random actual = watchlistService.getRandomWatchlistEntry("name");
        //THEN
        assertThat(actual, is(nullValue()));
    }
}
