import { useContext, useEffect, useRef, useState } from "react";
import axios from "axios";
import TypeAndAuthContext from "../context/TypeAndAuthContext";

export default function useSearch(searchString, searchType) {
  const timerId = useRef(0);
  const [searchResults, setSearchResults] = useState([]);
  const { token } = useContext(TypeAndAuthContext);
  const config = {
    headers: {
      Authorization: "Bearer " + token,
    },
  };

  useEffect(() => {
    timerId.current = setTimeout(() => {
      axios
        .get(
          `/api/omdb?searchString=${searchString}&type=${searchType}`,
          config
        )
        .then((response) => response.data)
        .then((data) => setSearchResults(data))
        .catch((error) => console.log(error));
    }, 1000);
  }, [searchString, searchType]);

  return { searchResults };
}
