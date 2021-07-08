import TypeAndAuthContext from "../context/TypeAndAuthContext";
import useWatchlist from "../hooks/useWatchlist";
import { useContext, useState } from "react";
import useTrending from "../hooks/useTrending";
import MovieAndSeriesCard from "../components/MovieAndSeriesCard";
import useWatchHistory from "../hooks/useWatchHistory";
import LoadingSpinner from "../components/LoadingSpinner";
import styled from "styled-components/macro";

export default function TrendingMovieAndSeriesPage() {
  const { MOVIE, SERIES, DAY, WEEK } = useContext(TypeAndAuthContext);
  const [searchType, setSearchType] = useState(MOVIE);
  const [timewindow, setTimewindow] = useState(DAY);
  const { watchlist } = useWatchlist();
  const { searchResults, isLoading } = useTrending(timewindow, searchType);
  const { watchHistory } = useWatchHistory();

  return (
    <div>
      <Selector>
        <div>
          <input
            type="radio"
            name="search_type"
            onChange={() => setSearchType(MOVIE)}
            label={"Movie"}
            defaultChecked
          />
          <input
            type="radio"
            name="search_type"
            onChange={() => setSearchType(SERIES)}
            label={"Series"}
          />
        </div>
        <div>
          <input
            type="radio"
            name="timewindow"
            onChange={() => setTimewindow(DAY)}
            label={"Day"}
            defaultChecked
          />
          <input
            type="radio"
            name="timewindow"
            onChange={() => setTimewindow(WEEK)}
            label={"Week"}
          />
        </div>
      </Selector>
      {isLoading && <LoadingSpinner />}
      {searchResults &&
        searchResults.map((item) => (
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

const Selector = styled.div`
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
    margin-left: 0;
    margin-right: 0;
    margin-bottom: 1vh;
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
`
