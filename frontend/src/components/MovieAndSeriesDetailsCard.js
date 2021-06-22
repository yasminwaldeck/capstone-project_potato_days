import { useParams } from "react-router-dom";
import useOmdb from "../hooks/useOmdb";
import useWatchlist from "../hooks/useWatchlist";
import {useState} from "react";
import Cast from "./Cast";
import Crew from "./Crew";
import ProviderElement from "./ProviderElement";
import styled from "styled-components";
import MovieInfo from "./MovieInfo";
import Ratings from "./Ratings";

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
        <MovieAndSeriesDetails>
            { item && (
                <>
                    <img src={item.poster}/>
                    <h3>{item.title}</h3>
                    <p id="tagline">{item.tagline}</p>
                    <MovieInfo info={item}/>
                    <p>First aired: {item.first_air_date}</p>
                    <p>Last aired: {item.last_air_date}</p>
                    {item.genres &&
                    <><h3>Genres:</h3>
                    <div id={"genre"}>{item.genres.map((genre) => (
                        <p key={genre.id}>{genre.name}</p>
                    ))}</div></>}
                    <p>Number of episodes: {item.number_of_episodes}</p>
                    <p>Number of seaons: {item.number_of_seasons}</p>
                    {item.in_production && <p>In Production!</p>}

                    {item.seasons && <div>Seasons: {item.seasons.map((season) => (
                        <div key={season.id}>
                        <p>Season {season.season_number}: {season.name}</p>
                        <p>Number of episodes: {season.episode_count}</p>
                        <p>{season.air_date}</p>
                        <p>Overview: {season.overview}</p>
                            <img src={"https://image.tmdb.org/t/p/w500" + season.poster_path}/>
                        </div>
                        ))}</div>}
                    {item.cast &&  <Cast castlist={item.cast}/>}
                    {item.crew &&  <Crew crewlist={item.crew}/>}
                    {item.de &&  (<div>
                        <h3>Streamable at:</h3>
                        <ProviderElement list={item.de.flatrate}/>
                        <h3>Buy at:</h3>
                        <ProviderElement list={item.de.buy}/>
                    </div>)}

            </>)}

            {!watch && <button onClick={add}>Add to watchlist</button>}
            {watch && <button onClick={remove}>Remove from watchlist!</button>}
    </MovieAndSeriesDetails>
    )

}

const MovieAndSeriesDetails = styled.div`

  width: 90vw;
  margin: 0 auto 5vh auto;
  
    #tagline{
      font-style: italic;
    }
  
  #genre{
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 10px;
  }
`