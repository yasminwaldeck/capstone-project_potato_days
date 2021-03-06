package de.neuefische.backend.model.OMDb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmdbResponseDto {
    @JsonProperty("Search")
    private List<OmdbDetailsDto> list;
    private int totalResults;
}
