import {useEffect} from "react";
import axios from "axios";

export default function useOmdb(id){
    const [movie, setMovie] = useState({});

    useEffect(() => {
        axios
            .get(`/api/movie/${id}`)
            .then((response) => response.data)
            .then(setMovie)
            .catch((error) => console.error(error.message));
    }, [id]);

    return { movie }
}