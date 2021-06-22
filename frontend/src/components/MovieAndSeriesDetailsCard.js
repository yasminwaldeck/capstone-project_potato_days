import { useParams } from "react-router-dom";
import useOmdb from "../hooks/useOmdb";
import useWatchlist from "../hooks/useWatchlist";
import {useState} from "react";

export default function MovieAndSeriesDetailsCard(){

    const { id } = useParams();
    const { item } = useOmdb(id);
    const { addToWatchlist, removeFromWatchlist, watchlist } = useWatchlist();
    const [watch, setWatch] = useState(watchlist.find(watchedItem => watchedItem.imdbID === item.imdbID))

    const add = () => {
        addToWatchlist(item.imdbID, item.type)
        setWatch("watched")
    }

    const remove = () => {
        removeFromWatchlist(item)
        setWatch(null)
    }

    return (
        <>
            {console.log(item)}

        <div>
            { item && (
                <>
                    <img src={item.poster}/>
                    <h3>{item.title}</h3>
                    <p>{item.tagline}</p>
                    <p>Year: {item.year}</p>
                    <p>Runtime: {item.runtime}</p>
                    <p>Country: {item.country}</p>
                    {item.ratings && <div>Ratings: {item.ratings.map((rating) => (
                        <p key={rating.source}>{rating.source}: {rating.value}</p>
                    ))}</div>}
                    <p>First aired: {item.first_air_date}</p>
                    <p>Last aired: {item.last_air_date}</p>
                    <p>Released: {item.release_date}</p>
                    {item.genres && <div>Grenres: {item.genres.map((genre) => (
                        <p key={genre.id}>{genre.name}</p>
                    ))}</div>}
                    <p>Number of episodes: {item.number_of_episodes}</p>
                    <p>Number of seaons: {item.number_of_seasons}</p>
                    <p>In Production: {item.in_production}</p>
                    <p>Status: {item.status}</p>
                    {item.seasons && <div>Seasons: {item.seasons.map((season) => (
                        <div key={season.id}>
                        <p>Season {season.season_number}: {season.name}</p>
                        <p>Number of episodes: {season.episode_count}</p>
                        <p>{season.air_date}</p>
                        <p>Overview: {season.overview}</p>
                            <img src={"https://image.tmdb.org/t/p/w500" + season.poster_path}/>
                        </div>
                        ))}</div>}
                    {item.cast &&  <div>Cast: {item.cast.map((cast) => (
                        <div key={cast.id}>
                            <p>{cast.name} as {cast.character}</p>
                            <img src={"https://image.tmdb.org/t/p/w500" + cast.profile_path}/>
                        </div>
                    ))}</div>}
                    {item.crew &&  <div>Crew: {item.crew.map((crew) => (
                        <div key={crew.id}>
                            <p>{crew.name}, {crew.department}</p>
                            <img src={"https://image.tmdb.org/t/p/w500" + crew.profile_path}/>
                        </div>
                    ))}</div>}
                    {item.de &&  <div>Streamable at: {item.de.flatrate.map((provider) => (
                        <div key={provider.provider_name}>
                            <p>{provider.provider_name}</p>
                            <img src={"https://image.tmdb.org/t/p/original" + provider.provider_path}/>
                        </div>
                    ))}</div>}
                    {item.de &&  <div>Buy at: {item.de.buy.map((provider) => (
                        <div key={provider.provider_name}>
                            <p>{provider.provider_name}</p>
                            <img src={"https://image.tmdb.org/t/p/original" + provider.provider_path}/>
                        </div>
                    ))}</div>}

            </>)
            }

            {!watch && <button onClick={add}>Add to watchlist</button>}
            {watch && <button onClick={remove}>Remove from watchlist!</button>}

        </div>
    </>
    )

}