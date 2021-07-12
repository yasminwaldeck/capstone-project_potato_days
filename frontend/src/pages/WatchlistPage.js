import MovieAndSeriesCard from "../components/MovieAndSeriesCard";
import { useContext, useState } from "react";
import TypeAndAuthContext from "../context/TypeAndAuthContext";
import useWatchlistByType from "../hooks/useWatchlistByType";
import useWatchlist from "../hooks/useWatchlist";
import useWatchHistory from "../hooks/useWatchHistory";
import LoadingSpinner from "../components/LoadingSpinner";
import styled from "styled-components/macro";

export default function WatchlistPage() {
  const { MOVIE, SERIES } = useContext(TypeAndAuthContext);
  const [type, setType] = useState(MOVIE);
  const [filtered, setFiltered] = useState(false);
  const { watchlistitems, setWatchlistitems, isLoading } = useWatchlistByType(
    type,
    filtered
  );
  const { watchlist } = useWatchlist(filtered);
  const { watchHistory } = useWatchHistory();

  return (
    <div>
      <Select>
        <div className={"typeselectors"}>
          <input
            type="radio"
            name="type"
            onChange={() => setType(MOVIE)}
            defaultChecked
            label={"Movie"}
          />
          <input
            type="radio"
            name="type"
            onChange={() => setType(SERIES)}
            label={"Series"}
          />
          <input
            type="radio"
            name="type"
            onChange={() => setWatchlistitems(watchlist)}
            label={"Both"}
          />
        </div>

        {filtered ? (
          <input
            className={"filter"}
            type="checkbox"
            onClick={() => setFiltered(false)}
            label="Show watched items"
          />
        ) : (
          <input
            type="checkbox"
            className={"filter"}
            onClick={() => setFiltered(true)}
            label="Hide watched items"
          />
        )}
      </Select>
      {isLoading && <LoadingSpinner />}
      {type !== null &&
        watchlistitems.map((item) => (
          <MovieAndSeriesCard
            key={item.imdbID}
            item={item}
            onWatchlist={watchlist.find(
              (watchedItem) => watchedItem.imdbID === item.imdbID
            )}
            onWatchHistory={watchHistory.find(
              (watchedItem) => watchedItem.imdbID === item.imdbID
            )}
          />
        ))}
    </div>
  );
}

const Select = styled.div`
  padding: 4px;
  border-radius: 3px;
  position: relative;
  width: 80vw;
  margin: auto;

  .typeselectors {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
  }

  input {
    height: 100%;
    width: 25vw;
    appearance: none;
    outline: none;
    cursor: pointer;
    border-radius: 2px;
    padding: 4px 8px;
    background: #48484a;
    font-size: 16px;
    transition: all 100ms linear;
  }

  input:checked {
    background-image: linear-gradient(180deg, #828282, #48484a);
  }

  input:before {
    content: attr(label);
    display: inline-block;
    text-align: center;
    width: 100%;
  }

  .filter {
    width: auto;
    margin-top: 1.5vh;
    background-image: linear-gradient(180deg, #48484a, #828282);
  }

  .filter:checked {
    background-image: linear-gradient(180deg, #828282, #48484a);
  }
`;
