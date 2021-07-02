import axios from "axios";
import {useContext, useEffect, useState} from "react";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useWatchHistory(){

    const [watchHistory, setWatchHistory] = useState([]);
    const {token} = useContext(TypeAndAuthContext)
    const config = {
        headers: {
            Authorization: "Bearer " + token,
        },
    }

    useEffect( () => {
        axios.get("/api/watchhistory/", config)
            .then(response => response.data)
            .then(setWatchHistory)
            .catch((error) => console.error(error.message));
    }, [])

    const addToHistory = (imdbID, type) =>{

        axios.post("/api/watchhistory", {imdbID, type})
            .then((response) => setWatchHistory([...watchHistory, response.data]))
            .catch((error) => console.error(error.message))
    }

    const removeFromHistory = (item) =>{
        axios.delete("/api/watchhistory", {
            headers: {
                Authorization: "Bearer " + token,
            },
            data: item.imdbID
        })
            .then(() => setWatchHistory(watchHistory.delete(item)))
            .catch((error) => console.error(error.message))
    }

    return { addToHistory, removeFromHistory, watchHistory }
}