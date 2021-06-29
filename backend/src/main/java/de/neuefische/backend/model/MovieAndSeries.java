package de.neuefische.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection="moviesAndSeries")
public class MovieAndSeries {

    @Id
    private String imdbID;
    private String type;
    private String recommendedBy;
    private boolean watchHistory;
    private boolean watchlist;
    private boolean watching;
    private String username;
    private List<Episode> watchedEpisodes;
}
