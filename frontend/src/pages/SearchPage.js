import {useRef, useState} from "react";
import axios from "axios";
import MovieCard from "../components/MovieCard";

export default function SearchPage(){

    const [searchString, setSearchString] = useState("")
    const [searchType, setSearchType] = useState("movie")
    const [searchResults, setSearchResults] = useState([])
    const timerId = useRef(0)

    const clickHandler = (searchString, searchType) => {
        if (searchString !== ""){
            if(searchType === "movie") {
                timerId.current = setTimeout(() => {
                    axios
                        .get(`/api/movie?searchString=${searchString}`)
                        .then(response => response.data)
                        .then((data) => (
                            setSearchResults(data),
                            console.log(data)
                        ))
                        .catch(error => console.log(error))
                }, 600)
            }
        }
    }

    return(
        <>
        <div>
            <input type="text"
                   value={searchString}
                   onChange={(event) => setSearchString(event.target.value)}
            />
            <div>
                <input type="radio" name="movie" onChange={() => setSearchType("movie")} defaultChecked/> Movie
                <input type="radio" name="series" onChange={() => setSearchType("series")}/> Series
            </div>
            <button disabled={searchString === ""} onClick={() => clickHandler(searchString, searchType)}>Search</button>
            {searchResults.map((movie) => (
                <MovieCard key={movie.imdbID} movie={movie}/>
            ))}
        </div>
        </>
    )
}