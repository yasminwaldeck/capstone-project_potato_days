package de.neuefische.backend.model.OMDb;

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
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Ratings")
    private List<OmdbRatingDto> ratings;
    @JsonProperty("Type")
    private String type;
}
