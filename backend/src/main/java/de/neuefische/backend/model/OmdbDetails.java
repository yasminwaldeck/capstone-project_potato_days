package de.neuefische.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OmdbDetails {

    private String title;
    private String year;
    private String imdbID;
    private String poster;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String country;
    private List<OmdbRating> ratings;
    private String type;
    private int totalSeasons;
}
