import {useEffect, useState} from "react";
import axios from "axios";

export default function useRecommendedBy(imdbID) {
    const [name, setName] = useState("");

    useEffect(() => {
        axios.get(`/api/watchlist/name?imdbID=${imdbID}`)
            .then((response) => response.data)
            .then(setName)
            .catch((error) => console.error(error.message))

    }, [imdbID, name]);

    const setRecommendedBy = (imdbID, recommendedBy) => {
        axios.post("/api/watchlist/name", {imdbID, recommendedBy})
            .then((response) => response.data)
            .then(setName)
            .catch((error) => console.error(error.message))
    }

    const deleteRecommendedBy = (imdbID) => {
        axios.delete(`/api/watchlist/name?imdbID=${imdbID}`)
            .then(setName(""))
            .catch((error) => console.error(error.message))
    }

    return { name, setRecommendedBy,  deleteRecommendedBy }
}