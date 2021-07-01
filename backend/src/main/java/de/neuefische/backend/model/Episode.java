package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Episode {
    private String air_date;
    private int episode_number;
    private String name;
    private String overview;
    private String still_path;
    private int season_number;
    private String imdbId;
}
