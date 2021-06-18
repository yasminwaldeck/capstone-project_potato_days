import { useParams } from "react-router-dom";
import useOmdb from "../hooks/useOmdb";
import useWatchlist from "../hooks/useWatchlist";
import {useState} from "react";

export default function MovieAndSeriesDetailsCard(){

    const { id } = useParams();
    const { item } = useOmdb(id);
    const { addToWatchlist, removeFromWatchlist, watchlist } = useWatchlist();

    const add = () => {
        addToWatchlist(item.imdbID, item.type)
    }

    const remove = () => {
        removeFromWatchlist(item)
        setList(watchlist)
    }

    return (
        <div>
            <img src={item.poster}/>
            <h3>{item.title}</h3>
            <p>Year: {item.year}</p>
            <p>Runtime: {item.runtime}</p>
            <p>Genre: {item.genre}</p>
            <p>Director: {item.director}</p>
            <p>Writer: {item.writer}</p>
            <p>Actors: {item.actors}</p>
            <p>Country: {item.country}</p>
            {item.ratings && <div>Ratings: {item.ratings.map((rating) => (
                <p key={rating.source}>{rating.source}: {rating.value}</p>
            ))}</div>}
            {(item.totalSeasons > 0) && <p>Total Seasons: {item.totalSeasons}</p>}

            {!watchlist.find(watchedItem => watchedItem.imdbID === item.imdbID) && <button onClick={add}>Add to watchlist</button>}
            {watchlist.find(watchedItem => watchedItem.imdbID === item.imdbID) && <button onClick={remove}>Remove from watchlist!</button>}
        </div>
    )

}