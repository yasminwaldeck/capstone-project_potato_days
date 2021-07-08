import Default from "../resources/couchpotato.jpg";
import styled from "styled-components/macro";
import { useParams } from "react-router-dom";

export default function EpisodeCard({
  episode,
  addEpisodeToHistory,
  removeEpisodeFromHistory,
  onWatchHistory,
}) {
  const { imdbid } = useParams();

  return (
    <Episode>
      <div id={"section"}>
        {episode.still_path ? (
          <img
            src={"https://image.tmdb.org/t/p/w500" + episode.still_path}
            alt={"Poster"}
          />
        ) : (
          <img src={Default} alt={"altposter"} />
        )}
        <div id={"details"}>
          {episode.name ? (
            <h3>{episode.name}</h3>
          ) : (
            <h3>Episode {episode.episode_number}</h3>
          )}
          <p>{episode.air_date}</p>
          {onWatchHistory ? (
            <button
                className={"remove"}
              onClick={() =>
                removeEpisodeFromHistory(
                  imdbid,
                  episode.season_number,
                  episode.episode_number
                )
              }
            >
              Remove from watch history
            </button>
          ) : (
            <button
                className={"add"}
              onClick={() =>
                addEpisodeToHistory(
                  imdbid,
                  episode.season_number,
                  episode.episode_number
                )
              }
            >
              Add to watch history
            </button>
          )}
        </div>
      </div>
      {episode.overview && (
        <details>
          <summary>Overview</summary>
          <p>{episode.overview}</p>
        </details>
      )}
    </Episode>
  );
}

const Episode = styled.div`
  margin-bottom: 5vh;

  button {
    border: none;
    margin-top: 1.5vh;
    font-size: inherit;
    color: white;
    cursor: pointer;
    padding: 10px 25px 10px 20px;
    text-transform: uppercase;
    font-weight: 700;
    outline: none;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
  }

  button:after {
    content: "";
    position: absolute;
    z-index: -1;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
  }

  button:before {
    line-height: 1;
    position: relative;
  }

  .add {
    background: #00916e;
  }

  .add:hover {
    background: #00664e;
  }

  .add:active {
    background: #00664e;
    top: 2px;
  }

  .remove {
    background: #66000e;
  }

  .remove:hover {
    background: #8f0013;
  }

  .remove:active {
    background: #8f0013;
    top: 2px;
  }

  details{
    margin: 3vh;
  }
`;
