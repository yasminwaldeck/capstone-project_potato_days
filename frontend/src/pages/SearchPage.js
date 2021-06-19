import {useContext, useRef, useState} from "react";
import axios from "axios";
import MovieAndSeriesCard from "../components/MovieAndSeriesCard";
import TypeContext from "../context/TypeContext";
import useWatchlist from "../hooks/useWatchlist";

export default function SearchPage(){

    const {MOVIE, SERIES} = useContext(TypeContext)
    const [searchString, setSearchString] = useState("")
    const [searchType, setSearchType] = useState(MOVIE)
    const [searchResults, setSearchResults] = useState([])
    const [search, setSearch] = useState()
    const { watchlist } = useWatchlist();
    const timerId = useRef(0)


    const clickHandler = (searchString, searchType) => {
                timerId.current = setTimeout(() => {
                    axios
                        .get(`/api/omdb?searchString=${searchString}&type=${searchType}`)
                        .then(response => response.data)
                        .then((data) => (
                            setSearchResults(data)
                        ))
                        .then(() => setSearch("searched"))
                        .catch(error => console.log(error))
                }, 600)
    }

    return(
        <div>
            <input type="text"
                   value={searchString}
                   onChange={(event) => setSearchString(event.target.value)}
            />
            <div>
                <input type="radio" name="search_type" onChange={() => setSearchType(MOVIE)} defaultChecked/> Movie
                <input type="radio" name="search_type" onChange={() => setSearchType(SERIES)}/> Series
            </div>
            <button disabled={searchString === ""} onClick={() => clickHandler(searchString, searchType)}>Search</button>
            {searchResults.map((item) => (
                <MovieAndSeriesCard key={item.imdbID} item={item} watched={watchlist.find(watchedItem => watchedItem.imdbID === item.imdbID)}/>
            ))}
            {search && <p>If you have not found what you have been looking for, try to be more specific.</p>}
        </div>
    )
}