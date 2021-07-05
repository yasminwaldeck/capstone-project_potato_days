import { useContext, useEffect, useState } from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useSeasons(id, season) {
  const [item, setItem] = useState({});
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
      .get(`/api/details/${id}/season/${season}`, config)
      .then((response) => response.data)
      .then(setItem)
      .catch((error) => console.error(error.message))
      .finally(() => setIsLoading(false));
  }, [id, season, token]);

  return { item, isLoading };
}
