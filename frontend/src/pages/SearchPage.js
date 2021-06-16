import {useState} from "react";

export default function SearchPage(){

    const [searchString, setSearchString] = useState("")
    const [searchType, setSearchType] = useState("movie")

    const clickHandler = (searchString, searchType) => {
        console.log("String: " + searchString + " Type: " + searchType)
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
        </div>
        </>
    )
}