import { useContext, useState } from "react";
import MovieAndSeriesCard from "../components/MovieAndSeriesCard";
import TypeAndAuthContext from "../context/TypeAndAuthContext";
import useWatchlist from "../hooks/useWatchlist";
import useSearch from "../hooks/useSearch";
import styled from "styled-components/macro";
import useWatchHistory from "../hooks/useWatchHistory";
import LoadingSpinner from "../components/LoadingSpinner";

export default function SearchPage() {
  const { MOVIE, SERIES } = useContext(TypeAndAuthContext);
  const [searchString, setSearchString] = useState("");
  const [searchType, setSearchType] = useState(MOVIE);
  const { watchlist } = useWatchlist();
  const { searchResults, isLoading } = useSearch(searchString, searchType);
  const { watchHistory } = useWatchHistory();

  return (
    <Search>
      <input
        type="text"
        value={searchString}
        onChange={(event) => setSearchString(event.target.value)}
      />
      <TypeSelector>
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
      </TypeSelector>
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
      {
        <p>
          If you have not found what you have been looking for, try to be more
          specific.
        </p>
      }
    </Search>
  );
}

const Search = styled.div`
  input {
    margin-bottom: 2vh;
  }

  p {
    width: 80vw;
    margin: auto auto 3vh;
  }
`;

const TypeSelector = styled.div`
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
