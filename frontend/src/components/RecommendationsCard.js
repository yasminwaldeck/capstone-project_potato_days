import MovieAndSeriesCard from "./MovieAndSeriesCard";
import useWatchlist from "../hooks/useWatchlist";
import useWatchHistory from "../hooks/useWatchHistory";
import useDetails from "../hooks/useDetails";

export default function RecommendationsCard({ imdbID }) {
  const { watchlist } = useWatchlist();
  const { watchHistory } = useWatchHistory();
  const { item } = useDetails(imdbID);

  return (
    <MovieAndSeriesCard
      key={imdbID}
      item={item}
      onWatchlist={watchlist.find(
        (watchedItem) => watchedItem.imdbID === item.imdbID
      )}
      onWatchHistory={watchHistory.find(
        (watchedItem) => watchedItem.imdbID === item.imdbID
      )}
    />
  );
}
