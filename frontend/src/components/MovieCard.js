export default function MovieCard({movie}){



    return(
        <div>
            <img src={movie.poster}/>
            <h3>{movie.title} ({movie.year})</h3>
        </div>
    )
}