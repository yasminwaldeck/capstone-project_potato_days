package de.neuefische.backend.model.OMDb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OmdbDetailsDto {

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    private String imdbID;
    @JsonProperty("Poster")
    private String poster;
    @JsonProperty("Runtime")
    private String runtime;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Ratings")
    private List<OmdbRatingDto> ratings;
    @JsonProperty("Type")
    private String type;
}



//        Title	"Spider-Man: The Animated Series"
//        Year	"1994–1998"
//        Rated	"TV-Y"
//        Released	"19 Nov 1994"
//        Runtime	"23 min"
//        Genre	"Animation, Action, Adventure, Family, Sci-Fi"
//        Director	"N/A"
//        Writer	"Stan Lee, Steve Ditko"
//        Actors	"Christopher Daniel Barnes, Sara Ballantine, Edward Asner, Roscoe Lee Browne"
//        Plot	"After being bitten by a radioactive spider, young Peter Parker finds that he now has spider-like super powers. Hoping to use his new-found abiilties for wealth and fame, he lets his ego blind him to the needs of others, and indirectly causes the death of his uncle Ben when he refuses to help a police officer catch a fleeing criminal. Humbled by his failure, he resolves to use his talents for fighting crime, and becomes the superhero Spider-Man. While he fights assorted super-villains, Peter also must balance his personal life, including his girlfriend Mary Jane, his job as a photographer at the Daily Bugle, and a an editor who has convinced himself that Spider-man is a criminal that has to be brought down."
//        Language	"English"
//        Country	"USA"
//        Awards	"4 nominations."
//        Poster	"https://m.media-amazon.com/images/M/MV5BMmQ1NzBlYmItNmZkZi00OTZkLTg5YTEtNTI5YjczZjk3Yjc1XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg"
//        Ratings
//        0
//        Source	"Internet Movie Database"
//        Value	"8.4/10"
//        Metascore	"N/A"
//        imdbRating	"8.4"
//        imdbVotes	"27,401"
//        imdbID	"tt0112175"
//        Type	"series"
//        totalSeasons	"5"
//        Response	"True"