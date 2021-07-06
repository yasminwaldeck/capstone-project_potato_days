import { useContext, useEffect, useState } from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useWatchlistByType(type, filtered) {
  const [watchlistitems, setWatchlistitems] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const { token } = useContext(TypeAndAuthContext);

  useEffect(() => {
    const config = {
      headers: {
        Authorization: "Bearer " + token,
      },
    };
    setIsLoading(true);
    axios
      .get(`/api/watchlist?type=${type}&filtered=${filtered}`, config)
      .then((response) => response.data)
      .then(setWatchlistitems)
      .catch((error) => console.error(error.message))
      .finally(() => setIsLoading(false));
  }, [type, token, filtered]);

  return { watchlistitems, setWatchlistitems, isLoading };
}
