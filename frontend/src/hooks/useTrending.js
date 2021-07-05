import axios from "axios";
import { useContext, useEffect, useState } from "react";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useTrending(timewindow, searchType) {
  const [searchResults, setSearchResults] = useState([]);
  const { token } = useContext(TypeAndAuthContext);
  const config = {
    headers: {
      Authorization: "Bearer " + token,
    },
  };

  useEffect(() => {
    axios
      .get(`/api/trending?timewindow=${timewindow}&type=${searchType}`, config)
      .then((response) => response.data)
      .then((data) => setSearchResults(data))
      .catch((error) => console.log(error));
  }, [timewindow, searchType]);

  return { searchResults };
}
