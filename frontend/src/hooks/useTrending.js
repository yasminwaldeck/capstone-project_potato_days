import axios from "axios";
import { useContext, useEffect, useState } from "react";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useTrending(timewindow, searchType) {
  const [searchResults, setSearchResults] = useState([]);
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
      .get(`/api/trending?timewindow=${timewindow}&type=${searchType}`, config)
      .then((response) => response.data)
      .then((data) => setSearchResults(data))
      .catch((error) => console.log(error))
      .finally(() => setIsLoading(false));
  }, [timewindow, searchType, token]);

  return { searchResults, isLoading };
}
