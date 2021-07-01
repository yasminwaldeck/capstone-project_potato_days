import {useContext, useEffect, useState} from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useWatchlistByType(type){
    const [watchlistitems, setWatchlistitems] = useState([])
    const {token} = useContext(TypeAndAuthContext)
    const config = {
        headers: {
            Authorization: "Bearer " + token,
        },
    }

    useEffect(() => {
        axios
            .get(`/api/watchlist?type=${type}`, config)
            .then((response) => response.data)
            .then(setWatchlistitems)
            .catch((error) => console.error(error.message));
    }, [type]);

    return { watchlistitems, setWatchlistitems }
}