import { useContext, useEffect, useState } from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useSearch(searchString, searchType) {
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
      .get(`/api/omdb?searchString=${searchString}&type=${searchType}`, config)
      .then((response) => response.data)
      .then((data) => setSearchResults(data))
      .catch((error) => console.log(error))
      .finally(() => setIsLoading(false));
  }, [searchString, searchType, token]);

  return { searchResults, isLoading };
}
