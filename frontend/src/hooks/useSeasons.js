import { useContext, useEffect, useState } from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useSeasons(id, season) {
  const [item, setItem] = useState({});
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
      .get(`/api/details/${id}/season/${season}`, config)
      .then((response) => response.data)
      .then(setItem)
      .catch((error) => console.error(error.message))
      .finally(() => setIsLoading(false));
  }, [id, season]);

  return { item, isLoading };
}
