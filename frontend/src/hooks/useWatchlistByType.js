import {useEffect, useState} from "react";
import axios from "axios";

export default function useWatchlistByType(type){
    const [watchlistitems, setWatchlistitems] = useState([])

    useEffect(() => {
        axios
            .get(`/api/watchlist?type=${type}`)
            .then((response) => response.data)
            .then(setWatchlistitems)
            .catch((error) => console.error(error.message));
    }, [type]);

    return { watchlistitems, setWatchlistitems }
}