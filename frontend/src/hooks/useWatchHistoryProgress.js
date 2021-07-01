import {useContext, useEffect, useState} from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useWatchHistoryProgress(imdbId, id) {


    const [seriesProgress, setSeriesProgress] = useState();
    const {token} = useContext(TypeAndAuthContext)
    const config = {
        headers: {
            Authorization: "Bearer " + token,
        },
    }


    useEffect(() => {
        if(id !== undefined) {
            console.log(imdbId + ", " + id)
            axios.get(`/api/watchhistory/episode/${imdbId}/${id}/progress`, config)
                .then(response => response.data)
                .then(setSeriesProgress)
                .catch((error) => console.error(error.message));
        }
    }, [id])


    return { seriesProgress }
}