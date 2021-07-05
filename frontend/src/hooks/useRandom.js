import TypeAndAuthContext from "../context/TypeAndAuthContext";
import axios from "axios";
import { useContext, useEffect, useState } from "react";

export default function useRandom() {
  const [watchlistItem, setWatchlistItem] = useState({});
  const [watchhistoryItem, setWatchhistorytItem] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const { token } = useContext(TypeAndAuthContext);
  const config = {
    headers: {
      Authorization: "Bearer " + token,
    },
  };

  useEffect(() => {
    setIsLoading(true);
    axios
      .get(`/api/watchlist/random`, config)
      .then((response) => response.data)
      .then(setWatchlistItem)
      .catch((error) => console.error(error.message));

    axios
      .get(`/api/watchhistory/random`, config)
      .then((response) => response.data)
      .then(setWatchhistorytItem)
      .catch((error) => console.error(error.message))
      .finally(() => setIsLoading(false));
  }, []);

  return { watchlistItem, watchhistoryItem, isLoading };
}
