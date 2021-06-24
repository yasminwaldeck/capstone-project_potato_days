package service;

import de.neuefische.backend.config.OMDbConfig;
import de.neuefische.backend.config.TMDbConfig;
import de.neuefische.backend.service.OmdbApiService;
import de.neuefische.backend.service.TmdbApiService;
import de.neuefische.backend.service.TrendingService;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;

public class TrendingServiceTest {
    private final RestTemplate mockedTemplate = mock(RestTemplate.class);
    private final OMDbConfig omdbConfig = new OMDbConfig();
    private final TMDbConfig tmdbConfig = new TMDbConfig();
    private final OmdbApiService omdbApiService = new OmdbApiService(omdbConfig, mockedTemplate);
    private final TmdbApiService tmdbApiService = new TmdbApiService(mockedTemplate, tmdbConfig);
    private final TrendingService trendingService = new TrendingService(tmdbApiService, omdbApiService);

    @Test
    public void findTrendingShouldReturnTrendingSeriesForTheDay(){
        //GIVEN


    }

}
