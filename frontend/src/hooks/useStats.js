import axios from "axios";
import { useContext, useEffect, useState } from "react";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useStats() {
  const [stats, setStats] = useState([]);
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
      .get("/api/watchlist/name/stats", config)
      .then((response) => response.data)
      .then((response) => {
        setStats(response);
      })
      .catch((error) => console.error(error.message))
      .finally(() => setIsLoading(false));
  }, []);

  return { stats, isLoading };
}
