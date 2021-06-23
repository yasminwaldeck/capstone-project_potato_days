import axios from "axios";
import {useEffect, useState} from "react";

export default function useWatchlist(){

    const [watchlist, setWatchlist] = useState([]);


    useEffect( () => {
        axios.get("/api/watchlist/")
            .then(response => response.data)
            .then(setWatchlist)
            .catch((error) => console.error(error.message));
    }, [])

    const addToWatchlist = (imdbID, type) =>{
        axios.post("/api/watchlist", {imdbID, type})
            .then((response) => setWatchlist([... watchlist, response.data]))
            .catch((error) => console.error(error.message))
    }

    const removeFromWatchlist = (item) =>{
        axios.delete("/api/watchlist", {data: item.imdbID})
            .then(() => setWatchlist(watchlist.delete(item)))
            .catch((error) => console.error(error.message))
    }

    return { addToWatchlist, removeFromWatchlist, watchlist }
}