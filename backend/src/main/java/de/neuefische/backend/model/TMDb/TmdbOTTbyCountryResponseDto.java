package de.neuefische.backend.model.TMDb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TmdbOTTbyCountryResponseDto {
    @JsonProperty("DE")
    private TmdbOTTDto de;
    @JsonProperty("FR")
    private TmdbOTTDto fr;
    @JsonProperty("GB")
    private TmdbOTTDto gb;
    @JsonProperty("US")
    private TmdbOTTDto us;
}
