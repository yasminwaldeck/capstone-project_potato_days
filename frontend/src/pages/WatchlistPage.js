import MovieAndSeriesCard from "../components/MovieAndSeriesCard";
import {useContext, useState} from "react";
import TypeContext from "../context/TypeContext";
import useOmdb from "../hooks/useOmdb";
import useWatchlistByType from "../hooks/useWatchlistByType";
import useWatchlist from "../hooks/useWatchlist";

export default function WatchlistPage(){
    const [type, setType] = useState();
    const {MOVIE, SERIES, BOTH} = useContext(TypeContext)
    const { watchlistitems } = useWatchlistByType(type);
    const { watchlist } = useWatchlist();



    return (
        <div>
            <button onClick={() => setType(MOVIE)}>See all movies!</button>
            <button onClick={() => setType(SERIES)}>See all series!</button>
            <button onClick={() => setType(BOTH)}>See everything!</button>
            {(type !== null) &&
                watchlistitems.map((item) => (
                    <MovieAndSeriesCard key={item.imdbID} item={item} watched={watchlist.find(watchedItem => watchedItem.imdbID === item.imdbID)}/>
                ))
            }
        </div>
    )
}