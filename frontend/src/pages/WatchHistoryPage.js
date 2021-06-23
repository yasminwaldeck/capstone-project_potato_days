import MovieAndSeriesCard from "../components/MovieAndSeriesCard";
import {useContext, useState} from "react";
import TypeContext from "../context/TypeContext";
import useOmdb from "../hooks/useOmdb";
import useWatchlistByType from "../hooks/useWatchlistByType";
import useWatchlist from "../hooks/useWatchlist";
import useWatchHistory from "../hooks/useWatchHistory";
import useWatchHistoryByType from "../hooks/useWatchHistoryByType";

export default function WatchHistoryPage(){
    const [type, setType] = useState();
    const {MOVIE, SERIES, BOTH} = useContext(TypeContext)
    const { watchHistoryItems, setWatchHistoryItems } = useWatchHistoryByType(type);
    const { watchHistory } = useWatchHistory();
    const { watchlist } = useWatchlist();



    return (

        <div>
            <button onClick={() => setType(MOVIE)}>See all movies!</button>
            <button onClick={() => setType(SERIES)}>See all series!</button>
            <button onClick={() => {setWatchHistoryItems(watchHistory)}}>See everything!</button>

            {(type !== null) && (
                watchHistoryItems.map((item) => (
                    <MovieAndSeriesCard key={item.imdbID} item={item} onWatchlist={watchlist.find(watchedItem => watchedItem.imdbID === item.imdbID)} onWatchHistory={watchHistory.find(watchedItem => watchedItem.imdbID === item.imdbID)}/>
                )))}

        </div>
    )
}