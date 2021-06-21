package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Season {
    private String air_date;
    private int episode_count;
    private String id;
    private String name;
    private String overview;
    private String poster_path;
    private int season_number;
}
