package de.neuefische.backend.controller;

import de.neuefische.backend.model.MovieAndSeriesDetails;
import de.neuefische.backend.model.TMDb.Season;
import de.neuefische.backend.service.MovieAndSeriesDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/details")
public class MovieAndSeriesDetailsController {

    private MovieAndSeriesDetailsService movieAndSeriesDetailsService;

    public MovieAndSeriesDetailsController(MovieAndSeriesDetailsService movieAndSeriesDetailsService) {
        this.movieAndSeriesDetailsService = movieAndSeriesDetailsService;
    }

    @GetMapping("{id}")
    public MovieAndSeriesDetails getDetails(@PathVariable String id){
        return movieAndSeriesDetailsService.getDetails(id);
    }

    @GetMapping("{id}/season/{season}")
    public Season getEpisodes(@PathVariable String id, @PathVariable String season){
        return movieAndSeriesDetailsService.getEpisodes(id, season);
    }
}
