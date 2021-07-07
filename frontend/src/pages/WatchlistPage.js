import MovieAndSeriesCard from "../components/MovieAndSeriesCard";
import { useContext, useState } from "react";
import TypeAndAuthContext from "../context/TypeAndAuthContext";
import useWatchlistByType from "../hooks/useWatchlistByType";
import useWatchlist from "../hooks/useWatchlist";
import useWatchHistory from "../hooks/useWatchHistory";
import LoadingSpinner from "../components/LoadingSpinner";

export default function WatchlistPage() {
  const [type, setType] = useState();
  const [filtered, setFiltered] = useState(false);
  const { MOVIE, SERIES } = useContext(TypeAndAuthContext);
  const { watchlistitems, setWatchlistitems, isLoading } =
    useWatchlistByType(type, filtered);
  const { watchlist } = useWatchlist(filtered);
  const { watchHistory } = useWatchHistory();

  return (
    <div>
      <button onClick={() => setType(MOVIE)}>See all movies!</button>
      <button onClick={() => setType(SERIES)}>See all series!</button>
      <button
        onClick={() => {
          setWatchlistitems(watchlist);
        }}
      >
        See everything!
      </button>{filtered ? <button onClick={() => setFiltered(false)}>Include watched items</button> : <button onClick={() => setFiltered(true)}>Exclude watched items</button>}
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
