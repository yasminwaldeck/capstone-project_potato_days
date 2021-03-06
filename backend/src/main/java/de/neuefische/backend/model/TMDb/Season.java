package de.neuefische.backend.model.TMDb;

import de.neuefische.backend.model.Episode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Episode> episodes;
}
