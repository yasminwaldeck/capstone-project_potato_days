import {useContext, useEffect, useState} from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useDetails(id){
    const [item, setItem] = useState({});
    const {token} = useContext(TypeAndAuthContext)
    const config = {
        headers: {
            Authorization: "Bearer " + token,
        },
    }

    useEffect(() => {
        axios
            .get(`/api/details/${id}`, config)
            .then((response) => response.data)
            .then(setItem)
            .catch((error) => console.error(error.message));
    }, [id]);

    return { item }
}