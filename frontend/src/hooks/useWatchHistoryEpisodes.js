import { useContext, useEffect, useState } from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useWatchHistoryEpisodes(imdbId, id, season) {
  const [episodeWatchHistory, setEpisodeWatchHistory] = useState([]);
  const [seasonProgress, setSeasonProgress] = useState();

  const { token } = useContext(TypeAndAuthContext);
  const config = {
    headers: {
      Authorization: "Bearer " + token,
    },
  };

  useEffect(() => {
    axios
      .get(`/api/watchhistory/episode/${imdbId}/season/${season}`, config)
      .then((response) => response.data)
      .then(setEpisodeWatchHistory)
      .catch((error) => console.error(error.message));

    axios
      .get(
        `/api/watchhistory/episode/${imdbId}/${id}/season/${season}/progress`,
        config
      )
      .then((response) => response.data)
      .then(setSeasonProgress)
      .catch((error) => console.error(error.message));
  }, [imdbId, season, id]);

  const getEpisodeHistory = () => {
    axios
      .get(`/api/watchhistory/episode/${imdbId}/season/${season}`, config)
      .then((response) => response.data)
      .then(setEpisodeWatchHistory)
      .catch((error) => console.error(error.message));

    axios
      .get(
        `/api/watchhistory/episode/${imdbId}/${id}/season/${season}/progress`,
        config
      )
      .then((response) => response.data)
      .then(setSeasonProgress)
      .catch((error) => console.error(error.message));
  };

  const addEpisodeToHistory = (imdbId, season_number, episode_number) => {
    axios
      .post(
        "/api/watchhistory/episode",
        { imdbId, season_number, episode_number },
        config
      )
      .then(() => getEpisodeHistory())
      .catch((error) => console.error(error.message));
  };

  const removeEpisodeFromHistory = (imdbId, season_number, episode_number) => {
    axios
      .delete("/api/watchhistory/episode", {
        headers: {
          Authorization: "Bearer " + token,
        },
        data: { imdbId, season_number, episode_number },
      })
      .then(() => getEpisodeHistory())
      .catch((error) => console.error(error.message));
  };

  return {
    episodeWatchHistory,
    seasonProgress,
    addEpisodeToHistory,
    removeEpisodeFromHistory,
  };
}
