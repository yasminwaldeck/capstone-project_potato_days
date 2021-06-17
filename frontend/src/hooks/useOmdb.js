import {useEffect, useState} from "react";
import axios from "axios";

export default function useOmdb(id){
    const [item, setItem] = useState({});

    useEffect(() => {
        axios
            .get(`/api/omdb/details/${id}`)
            .then((response) => response.data)
            .then(setItem)
            .catch((error) => console.error(error.message));
    }, [id]);

    return { item }
}