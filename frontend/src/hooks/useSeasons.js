import {useEffect, useState} from "react";
import axios from "axios";

export default function useSeasons(id, season){
    const [item, setItem] = useState({});

    useEffect(() => {
        axios
            .get(`/api/details/${id}/season/${season}`)
            .then((response) => response.data)
            .then(setItem)
            .catch((error) => console.error(error.message));
    }, [id, season]);

    return { item }
}