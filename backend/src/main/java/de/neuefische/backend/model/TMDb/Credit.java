package de.neuefische.backend.model.TMDb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credit {
    private String name;
    private String profile_path;
    private String character;
    private String department;

}