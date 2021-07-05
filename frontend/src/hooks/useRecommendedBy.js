import { useContext, useEffect, useState } from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useRecommendedBy(imdbID) {
  const [name, setName] = useState("");
  const { token } = useContext(TypeAndAuthContext);
  const config = {
    headers: {
      Authorization: "Bearer " + token,
    },
  };

  useEffect(() => {
    const config = {
      headers: {
        Authorization: "Bearer " + token,
      },
    };
    axios
      .get(`/api/watchlist/name?imdbID=${imdbID}`, config)
      .then((response) => response.data)
      .then(setName)
      .catch((error) => console.error(error.message));
  }, [imdbID, name, token]);

  const setRecommendedBy = (imdbID, recommendedBy) => {
    axios
      .post("/api/watchlist/name", { imdbID, recommendedBy }, config)
      .then((response) => response.data)
      .then(setName)
      .catch((error) => console.error(error.message));
  };

  const deleteRecommendedBy = (imdbID) => {
    axios
      .delete(`/api/watchlist/name?imdbID=${imdbID}`, config)
      .then(setName(""))
      .catch((error) => console.error(error.message));
  };

  return { name, setRecommendedBy, deleteRecommendedBy };
}
