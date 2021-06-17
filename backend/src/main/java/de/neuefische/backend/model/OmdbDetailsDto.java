package de.neuefische.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmdbDetailsDto {

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    private String imdbID;
    @JsonProperty("Poster")
    private String poster;
    @JsonProperty("Runtime")
    private String runtime;
    @JsonProperty("Genre")
    private String genre;
    @JsonProperty("Director")
    private String director;
    @JsonProperty("Writer")
    private String writer;
    @JsonProperty("Actors")
    private String actors;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Ratings")
    private List<OmdbRatingDto> ratings;
    private int totalSeasons;
}
