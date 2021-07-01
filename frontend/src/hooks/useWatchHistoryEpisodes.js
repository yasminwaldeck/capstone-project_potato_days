import {useEffect, useState} from "react";
import axios from "axios";

export default function useWatchHistoryEpisodes(imdbId, id, season) {

    const [episodeWatchHistory, setEpisodeWatchHistory] = useState([]);
    const [seasonProgress, setSeasonProgress] = useState();

    useEffect(() => {
        axios.get(`/api/watchhistory/episode/${imdbId}/season/${season}`)
            .then(response => response.data)
            .then(setEpisodeWatchHistory)
            .catch((error) => console.error(error.message));

        axios.get(`/api/watchhistory/episode/${imdbId}/${id}/season/${season}/progress`)
            .then(response => response.data)
            .then(setSeasonProgress)
            .catch((error) => console.error(error.message));
    }, [imdbId, season, id])

    const getEpisodeHistory = () => {
        axios.get(`/api/watchhistory/episode/${imdbId}/season/${season}`)
            .then(response => response.data)
            .then(setEpisodeWatchHistory)
            .catch((error) => console.error(error.message));

        axios.get(`/api/watchhistory/episode/${imdbId}/${id}/season/${season}/progress`)
            .then(response => response.data)
            .then(setSeasonProgress)
            .catch((error) => console.error(error.message));
    }

    const addEpisodeToHistory = (imdbId, season_number, episode_number) => {
        axios.post("/api/watchhistory/episode", {imdbId, season_number, episode_number})
            .then(() => getEpisodeHistory())
            .catch((error) => console.error(error.message))
    }

    const removeEpisodeFromHistory = (imdbId, season_number, episode_number) => {
        axios.delete("/api/watchhistory/episode", {data: {imdbId, season_number, episode_number}})
            .then(() => getEpisodeHistory())
            .catch((error) => console.error(error.message))
    }

    return { episodeWatchHistory, seasonProgress, addEpisodeToHistory, removeEpisodeFromHistory }
}