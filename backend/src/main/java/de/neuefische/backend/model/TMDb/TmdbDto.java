package de.neuefische.backend.model.TMDb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TmdbDto {

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

}