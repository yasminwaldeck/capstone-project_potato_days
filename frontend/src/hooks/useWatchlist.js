import axios from "axios";
import {useContext, useEffect, useState} from "react";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useWatchlist(){

    const [watchlist, setWatchlist] = useState([]);
    const {token} = useContext(TypeAndAuthContext)
    const config = {
        headers: {
            Authorization: "Bearer " + token,
        },
    }

    useEffect( () => {
        axios.get("/api/watchlist/", config)
            .then(response => response.data)
            .then(setWatchlist)
            .catch((error) => console.error(error.message));
    }, [])

    const addToWatchlist = (imdbID, type) =>{
        axios.post("/api/watchlist", {imdbID, type}, config)
            .then((response) => setWatchlist([... watchlist, response.data]))
            .catch((error) => console.error(error.message))
    }

    const removeFromWatchlist = (item) =>{
        axios.delete("/api/watchlist", {
            headers: {
                Authorization: "Bearer " + token,
            },
            data: item.imdbID
        })
            .then(() => setWatchlist(watchlist.delete(item)))
            .catch((error) => console.error(error.message))
    }

    return { addToWatchlist, removeFromWatchlist, watchlist }
}