import axios from "axios";
import {useEffect, useState} from "react";

export default function useWatchHistory(){

    const [watchHistory, setWatchHistory] = useState([]);


    useEffect( () => {
        axios.get("/api/watchhistory/")
            .then(response => response.data)
            .then(setWatchHistory)
            .catch((error) => console.error(error.message));
    }, [])

    const addToHistory = (imdbID, type) =>{
        axios.post("/api/watchhistory", {imdbID, type})
            .then((response) => setWatchHistory([... watchHistory, response.data]))
            .catch((error) => console.error(error.message))
    }

    const removeFromHistory = (item) =>{
        axios.delete("/api/watchhistory", {data: item.imdbID})
            .then(() => setWatchHistory(watchHistory.delete(item)))
            .catch((error) => console.error(error.message))
    }

    return { addToHistory, removeFromHistory, watchHistory }
}