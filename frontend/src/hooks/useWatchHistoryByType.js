import {useContext, useEffect, useState} from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useWatchHistoryByType(type){
    const [watchHistoryItems, setWatchHistoryItems] = useState([])
    const {token} = useContext(TypeAndAuthContext)
    const config = {
        headers: {
            Authorization: "Bearer " + token,
        },
    }

    useEffect(() => {
        axios
            .get(`/api/watchhistory?type=${type}`, config)
            .then((response) => response.data)
            .then(setWatchHistoryItems)
            .catch((error) => console.error(error.message));
    }, [type]);

    return { watchHistoryItems, setWatchHistoryItems }
}