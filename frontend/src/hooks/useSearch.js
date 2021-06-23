import {useEffect, useRef, useState} from "react";
import axios from "axios";

export default function useSearch(searchString, searchType){
    const timerId = useRef(0)
    const [searchResults, setSearchResults] = useState([])


    useEffect(() => {

            timerId.current = setTimeout(() => {
                axios
                    .get(`/api/omdb?searchString=${searchString}&type=${searchType}`)
                    .then(response => response.data)
                    .then((data) => (
                        setSearchResults(data)
                    ))
                    .catch(error => console.log(error))
            }, 1000)

    }, [searchString, searchType])

    return { searchResults }
}