package de.neuefische.backend.controller;

import de.neuefische.backend.model.MovieOmdbOverview;
import de.neuefische.backend.model.OmdbDetails;
import de.neuefische.backend.service.MovieApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private MovieApiService movieApiService;

    @Autowired
    public MovieController(MovieApiService movieApiService){
        this.movieApiService = movieApiService;
    }

    @GetMapping
    public List<MovieOmdbOverview> searchMovies(@RequestParam String searchString){
        return movieApiService.searchMovies(searchString);
    }

}
