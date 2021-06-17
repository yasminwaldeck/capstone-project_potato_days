import {useEffect, useState} from "react";
import axios from "axios";

export default function useWatchlist(type){
    const [watchlistitems, setWatchlistitems] = useState([])

    useEffect(() => {
        axios
            .get(`/api/watchlist/${type}`)
            .then((response) => response.data)
            .then(setWatchlistitems)
            .catch((error) => console.error(error.message));
    }, [type]);

    return { watchlistitems }
}