import {useEffect, useState} from "react";
import axios from "axios";

export default function useDetails(id){
    const [item, setItem] = useState({});

    useEffect(() => {
        axios
            .get(`/api/details/${id}`)
            .then((response) => response.data)
            .then(setItem)
            .catch((error) => console.error(error.message));
    }, [id]);

    return { item }
}