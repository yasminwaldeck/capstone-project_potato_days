import styled from "styled-components/macro";
import Default from "../resources/couchpotato.jpg";
import { NavLink, useParams } from "react-router-dom";
import useWatchHistoryEpisodes from "../hooks/useWatchHistoryEpisodes";

export default function SeasonCard({ season, tmdbid }) {
  const { id } = useParams();
  const { seasonProgress } = useWatchHistoryEpisodes(
    id,
    tmdbid,
    season.season_number
  );

  return (
    <Season>
      <section>
        {season.poster_path ? (
          <img
            src={"https://image.tmdb.org/t/p/w500" + season.poster_path}
            alt={"Poster"}
          />
        ) : (
          <img src={Default} alt={"altposter"} />
        )}
        <div id={"details"}>
          {season.name ? (
            <h3>{season.name}</h3>
          ) : (
            <h3>Season {season.season_number}</h3>
          )}
          <p>Episodes: {season.episode_count}</p>
          <p>{season.air_date}</p>
          {seasonProgress && seasonProgress !== 0 ? (
            <div>
              <p>Progress: {seasonProgress.toFixed(1)}%</p>
              <progress value={seasonProgress} max="100" />
            </div>
          ) : (
            <div>
              <p>Progress: 0%</p>
              <progress value={0} max="100" />
            </div>
          )}
          <StyledNavLink to={id + "/" + tmdbid + "/" + season.season_number}>
            <button>Go to episodes</button>
          </StyledNavLink>
        </div>
      </section>
      {season.overview && (
        <details>
          <summary>
            <b>Overview</b>
          </summary>
          <p>{season.overview}</p>
        </details>
      )}
    </Season>
  );
}

const Season = styled.div`
  width: 90vw;
  margin: 0 auto 5vh auto;

  h3 {
    margin: 0;
  }
  p {
    margin: 0;
  }

  img {
    max-width: 40vw;
    max-height: 30vh;
  }

  #details {
    height: 30vh;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  details {
    margin: 3vh;
  }

  section {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
  }

  progress {
    -webkit-appearance: none;
    appearance: none;
    width: 30vw;
    height: 1.5vh;
    border-radius: 50px;
    background-color: #dededf;
    margin-top: 1.5vh;
    margin-bottom: 1.5vh;

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

  button {
    border: none;
    font-size: 14px;
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
    background-color: #828282;
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
`;

const StyledNavLink = styled(NavLink)` 
  text-decoration: none;
  color: #eae9f1;

  &:focus, &:hover, &:visited, &:link, &:active {
    text-decoration: none;
    color: #eae9f1;

`;
