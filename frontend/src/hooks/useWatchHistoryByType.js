import { useContext, useEffect, useState } from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useWatchHistoryByType(type) {
  const [watchHistoryItems, setWatchHistoryItems] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const { token } = useContext(TypeAndAuthContext);

  useEffect(() => {
    setIsLoading(true);
    const config = {
      headers: {
        Authorization: "Bearer " + token,
      },
    };
    axios
      .get(`/api/watchhistory?type=${type}`, config)
      .then((response) => response.data)
      .then(setWatchHistoryItems)
      .catch((error) => console.error(error.message))
      .finally(() => setIsLoading(false));
  }, [type, token]);

  return { watchHistoryItems, setWatchHistoryItems, isLoading };
}
