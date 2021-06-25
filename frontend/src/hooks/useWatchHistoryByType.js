import {useEffect, useState} from "react";
import axios from "axios";

export default function useWatchHistoryByType(type){
    const [watchHistoryItems, setWatchHistoryItems] = useState([])

    useEffect(() => {
        axios
            .get(`/api/watchhistory?type=${type}`)
            .then((response) => response.data)
            .then(setWatchHistoryItems)
            .catch((error) => console.error(error.message));
    }, [type]);

    return { watchHistoryItems, setWatchHistoryItems }
}