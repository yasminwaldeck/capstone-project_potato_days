import axios from "axios";
import {useEffect, useState} from "react";

export default function useTrending(timewindow, searchType){
    const [searchResults, setSearchResults] = useState([])

    useEffect(() => {

        axios
            .get(`/api/trending?timewindow=${timewindow}&type=${searchType}`)
            .then(response => response.data)
            .then((data) => (
                setSearchResults(data)
            ))
            .catch(error => console.log(error))

    }, [timewindow, searchType])

    return { searchResults }
}