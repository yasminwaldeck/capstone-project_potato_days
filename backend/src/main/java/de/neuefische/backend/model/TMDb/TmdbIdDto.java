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
public class TmdbIdDto {
    private List<TmdbDto> movie_results;
    private List<TmdbDto> tv_results;
}