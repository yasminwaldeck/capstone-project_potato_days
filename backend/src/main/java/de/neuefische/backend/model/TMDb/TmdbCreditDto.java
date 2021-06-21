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
public class TmdbCreditDto {

    private List<Credit> cast;
    private List<Credit> crew;
}
