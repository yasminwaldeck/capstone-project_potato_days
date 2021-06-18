import axios from "axios";

export default function useWatchlist(){

    const addToWatchlist = (imdbID, type) =>{
        axios.post("/api/watchlist", {imdbID, type})
            .catch((error) => console.error(error.message))
    }

    const removeFromWatchlist = (imdbID) =>{
        axios.delete("/api/watchlist", {data: imdbID})
            .catch((error) => console.error(error.message))
    }

    return { addToWatchlist, removeFromWatchlist }
}