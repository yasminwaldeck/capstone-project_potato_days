import { useParams } from "react-router-dom";
import useOmdb from "../hooks/useOmdb";

export default function MovieDetailsPage(){

    const { id } = useParams();
    const { movie } = useOmdb(id);

    return (
        <div>
            <h3>{movie.title}</h3>
            <p>{movie.year}</p>
            <p>{movie.year}</p>
            <p>{movie.year}</p>
        </div>
    )

}