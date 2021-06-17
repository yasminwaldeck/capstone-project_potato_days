export default function MovieAndSeriesCard({result}){



    return(
        <div>
            <img src={result.poster}/>
            <h3>{result.title} ({result.year})</h3>
        </div>
    )
}