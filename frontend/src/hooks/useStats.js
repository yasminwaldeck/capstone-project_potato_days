import axios from "axios";
import {useEffect, useState} from "react";

export default function useStats(){

    const [stats, setStats] = useState([]);


    useEffect( () => {
        axios.get("/api/watchlist/name/stats")
            .then(response => response.data)
            .then((response) => {
                setStats(response);
            console.log(response);}
            )
            .catch((error) => console.error(error.message));
    }, [])

    return {stats}
}