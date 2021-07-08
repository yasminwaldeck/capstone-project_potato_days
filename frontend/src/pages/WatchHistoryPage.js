import MovieAndSeriesCard from "../components/MovieAndSeriesCard";
import { useContext, useState } from "react";
import TypeAndAuthContext from "../context/TypeAndAuthContext";
import useWatchlist from "../hooks/useWatchlist";
import useWatchHistory from "../hooks/useWatchHistory";
import useWatchHistoryByType from "../hooks/useWatchHistoryByType";
import LoadingSpinner from "../components/LoadingSpinner";
import styled from "styled-components/macro";

export default function WatchHistoryPage() {

  const { MOVIE, SERIES } = useContext(TypeAndAuthContext);
  const [type, setType] = useState(MOVIE);
  const { watchHistoryItems, setWatchHistoryItems, isLoading } =
    useWatchHistoryByType(type);
  const { watchHistory } = useWatchHistory();
  const { watchlist } = useWatchlist();

  return (
    <div>
        <Select>
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
                onChange={() => setWatchHistoryItems(watchHistory)}
                label={"Both"}
            />

        </Select>
      {isLoading && <LoadingSpinner />}

      {type !== null &&
        watchHistoryItems.map((item) => (
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
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  padding: 4px;
  border-radius: 3px;
  position: relative;
  width: 80vw;
  margin: auto;

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
`;