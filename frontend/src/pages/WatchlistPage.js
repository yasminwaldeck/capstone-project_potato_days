import MovieAndSeriesCard from "../components/MovieAndSeriesCard";
import {useContext, useState} from "react";
import TypeContext from "../context/TypeContext";
import useOmdb from "../hooks/useOmdb";
import useWatchlist from "../hooks/useWatchlist";

export default function WatchlistPage(){
    const [type, setType] = useState();
    const {MOVIE, SERIES, BOTH} = useContext(TypeContext)
    const { watchlistitems } = useWatchlist(type);


    return (
        <div>
            <button onClick={() => setType(MOVIE)}>See all movies!</button>
            <button onClick={() => setType(SERIES)}>See all series!</button>
            <button onClick={() => setType(BOTH)}>See everything!</button>
            {(type !== null) &&
                watchlistitems.map((item) => (
                    <MovieAndSeriesCard key={item.imdbID} item={item}/>
                ))
            }
        </div>
    )
}