import {useEffect, useState} from "react";
import axios from "axios";

export default function useWatchHistoryProgress(imdbId, id) {


    const [seriesProgress, setSeriesProgress] = useState();


    useEffect(() => {
        if(id !== undefined) {
            console.log(imdbId + ", " + id)
            axios.get(`/api/watchhistory/episode/${imdbId}/${id}/progress`)
                .then(response => response.data)
                .then(setSeriesProgress)
                .catch((error) => console.error(error.message));
        }
    }, [id])


    return { seriesProgress }
}