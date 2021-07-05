import { useContext, useEffect, useState } from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useDetails(id) {
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
      .get(`/api/details/${id}`, config)
      .then((response) => response.data)
      .then(setItem)
      .catch((error) => console.error(error.message))
      .finally(() => setIsLoading(false));
  }, [id, token]);

  return { item, isLoading };
}
