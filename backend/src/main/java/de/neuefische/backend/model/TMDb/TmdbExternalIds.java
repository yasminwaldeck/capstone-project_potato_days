package de.neuefische.backend.model.TMDb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TmdbExternalIds {
   private String imdb_id;
}
