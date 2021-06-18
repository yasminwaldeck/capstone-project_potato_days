import {NavLink} from "react-router-dom";
import useWatchlist from "../hooks/useWatchlist";
import {useState} from "react";

export default function MovieAndSeriesCard({item, watched}){

    const [watch, setWatch] = useState(watched)
    const { addToWatchlist, removeFromWatchlist } = useWatchlist();

    const add = () => {
        addToWatchlist(item.imdbID, item.type)
        setWatch("watched")
    }

    const remove = () => {
        removeFromWatchlist(item)
        setWatch(null)
    }

    return(
        <div>
            <img src={item.poster}/>
            <h3>{item.title} ({item.year})</h3>
            <div>
                <NavLink to={("/details/" + item.imdbID)}>Details!</NavLink>
                {!watch && <button onClick={add}>Add to watchlist</button>}
                {watch && <button onClick={remove}>Remove from watchlist!</button>}
            </div>
        </div>
    )
}