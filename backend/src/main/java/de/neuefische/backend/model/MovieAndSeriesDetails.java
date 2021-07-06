package de.neuefische.backend.model;

import de.neuefische.backend.model.OMDb.OmdbRating;
import de.neuefische.backend.model.TMDb.Credit;
import de.neuefische.backend.model.TMDb.Genre;
import de.neuefische.backend.model.TMDb.Season;
import de.neuefische.backend.model.TMDb.TmdbOTTDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieAndSeriesDetails {

    //From omdb
    private String title;
    private String year;
    private String imdbID;
    private String poster;
    private String runtime;
    private String country;
    private List<OmdbRating> ratings;
    private String type;

    //From tmdb (details)
    private String first_air_date;
    private String last_air_date;
    private String release_date;
    private List<Genre> genres;
    private String id;
    private boolean in_production;
    private int number_of_episodes;
    private int number_of_seasons;
    private List<Season> seasons;
    private String status;
    private String tagline;
    private String overview;

    //From tmdb (credits)

    private List<Credit> cast;
    private List<Credit> crew;

    //From tmdb (ott)
    private TmdbOTTDto de;
    private TmdbOTTDto fr;
    private TmdbOTTDto gb;
    private TmdbOTTDto us;

}
