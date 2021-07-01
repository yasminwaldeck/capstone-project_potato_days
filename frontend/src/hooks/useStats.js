import axios from "axios";
import {useContext, useEffect, useState} from "react";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useStats(){

    const [stats, setStats] = useState([]);
    const {token} = useContext(TypeAndAuthContext)
    const config = {
        headers: {
            Authorization: "Bearer " + token,
        },
    }


    useEffect( () => {
        axios.get("/api/watchlist/name/stats", config)
            .then(response => response.data)
            .then((response) => {
                setStats(response);
            console.log(response);}
            )
            .catch((error) => console.error(error.message));
    }, [])

    return {stats}
}