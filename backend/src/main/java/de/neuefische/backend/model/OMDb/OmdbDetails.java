package de.neuefische.backend.model.OMDb;

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
    private String country;
    private List<OmdbRating> ratings;
    private String type;
}
