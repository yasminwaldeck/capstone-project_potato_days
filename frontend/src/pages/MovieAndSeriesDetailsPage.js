import { useParams } from "react-router-dom";
import useDetails from "../hooks/useDetails";
import useWatchlist from "../hooks/useWatchlist";
import { useContext } from "react";
import Cast from "../components/Cast";
import Crew from "../components/Crew";
import ProviderElement from "../components/ProviderElement";
import styled from "styled-components/macro";
import MovieInfo from "../components/MovieInfo";
import TypeAndAuthContext from "../context/TypeAndAuthContext";
import SeriesInfo from "../components/SeriesInfo";
import SeasonCard from "../components/SeasonCard";
import AddRemoveWatchButtons from "../components/AddRemoveWatchButtons";
import useWatchHistory from "../hooks/useWatchHistory";
import useWatchHistoryProgress from "../hooks/useWatchHistoryProgress";
import LoadingSpinner from "../components/LoadingSpinner";

export default function MovieAndSeriesDetailsPage() {
  const { id } = useParams();
  const { item, isLoading } = useDetails(id);
  const { MOVIE, SERIES } = useContext(TypeAndAuthContext);
  const { watchlist } = useWatchlist();
  const { watchHistory } = useWatchHistory();
  const { seriesProgress } = useWatchHistoryProgress(id, item.id, item.type);

  return (
    <MovieAndSeriesDetails>
      {isLoading && <LoadingSpinner />}
      {item && (
        <>
          <img src={item.poster} alt={"Poster"} />
          <h3>{item.title}</h3>
          <p id="tagline">{item.tagline}</p>
          {item.type === MOVIE && <MovieInfo info={item} />}
          {item.type === SERIES && <SeriesInfo info={item} />}
          {item.genres && (
            <>
              <h3>Genres:</h3>
              <div id={"genre"}>
                {item.genres.map((genre) => (
                  <p key={genre.id}>{genre.name}</p>
                ))}
              </div>
            </>
          )}

          {item.seasons && (
            <div>
              {seriesProgress && seriesProgress !== 0 ? (
                <div>
                  <h3>Progress: {seriesProgress.toFixed(1)}%</h3>
                  <progress id={"progress"} value={seriesProgress} max="100" />
                </div>
              ) : (
                <div>
                  <h3>Progress: 0%</h3>
                  <progress id={"progress"} value={0} max="100" />
                </div>
              )}
              <div>
                <h3>Seasons</h3>
                {item.seasons.map((season) => (
                  <SeasonCard
                    season={season}
                    tmdbid={item.id}
                    key={season.id}
                  />
                ))}
              </div>
            </div>
          )}
          {item.cast && <Cast castlist={item.cast} />}
          {item.crew && <Crew crewlist={item.crew} />}
          {item.de && (
            <div>
              {item.de.flatrate && (
                <>
                  <h3>Streamable at:</h3>
                  <ProviderElement list={item.de.flatrate} />
                </>
              )}
              {item.de.buy && (
                <>
                  <h3>Buy at:</h3>
                  <ProviderElement list={item.de.buy} />
                </>
              )}
            </div>
          )}
          {item && (
            <AddRemoveWatchButtons
              onWatchlist={watchlist.find(
                (watchedItem) => watchedItem.imdbID === item.imdbID
              )}
              onWatchHistory={watchHistory.find(
                (watchedItem) => watchedItem.imdbID === item.imdbID
              )}
              item={item}
            />
          )}
        </>
      )}
    </MovieAndSeriesDetails>
  );
}

const MovieAndSeriesDetails = styled.div`
  width: 90vw;
  margin: 0 auto 5vh auto;

  #tagline {
    font-style: italic;
  }

  #genre {
    display: flex;
    flex-wrap: wrap;
    min-width: 20vh;
    justify-content: space-evenly;
  }

  #progress {
    -webkit-appearance: none;
    appearance: none;
    width: 70vw;
    height: 3vh;
    border-radius: 50px;
    background-color: #dededf;

    ::-moz-progress-bar {
      background-image: linear-gradient(180deg, #828282, #48484a);
      border-radius: 50px;
    }
    ::-webkit-progress-bar {
      background-image: linear-gradient(180deg, #828282, #48484a);
      border-radius: 50px;
    }
    ::-webkit-progress-value {
      background-image: linear-gradient(180deg, #828282, #48484a);
      border-radius: 50px;
    }
  }
`;
