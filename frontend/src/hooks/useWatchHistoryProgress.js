import { useContext, useEffect, useState } from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useWatchHistoryProgress(imdbId, id, type) {
  const { SERIES } = useContext(TypeAndAuthContext);
  const [seriesProgress, setSeriesProgress] = useState();
  const { token } = useContext(TypeAndAuthContext);


  useEffect(() => {
    const config = {
      headers: {
        Authorization: "Bearer " + token,
      },
    };
    if (id !== undefined && type === SERIES) {
      axios
        .get(`/api/watchhistory/episode/${imdbId}/${id}/progress`, config)
        .then((response) => response.data)
        .then(setSeriesProgress)
        .catch((error) => console.error(error.message));
    }
  }, [id, imdbId, SERIES, type, token]);

  return { seriesProgress };
}
