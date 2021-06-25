import { useParams } from "react-router-dom";
import useOmdb from "../hooks/useOmdb";
import useWatchlist from "../hooks/useWatchlist";
import {useContext} from "react";
import Cast from "./Cast";
import Crew from "./Crew";
import ProviderElement from "./ProviderElement";
import styled from "styled-components";
import MovieInfo from "./MovieInfo";
import TypeContext from "../context/TypeContext";
import SeriesInfo from "./SeriesInfo";
import AddRemoveWatchButtons from "./AddRemoveWatchButtons";
import useWatchHistory from "../hooks/useWatchHistory";

export default function MovieAndSeriesDetailsCard(){

    const { id } = useParams();
    const { item } = useOmdb(id);
    const { MOVIE, SERIES } = useContext(TypeContext)
    const { watchlist} = useWatchlist();
    const { watchHistory } = useWatchHistory();

    return (
        <MovieAndSeriesDetails>
            { item && (
                <>
                    <img src={item.poster} alt={"Poster"}/>
                    <h3>{item.title}</h3>
                    <p id="tagline">{item.tagline}</p>
                    {(item.type === MOVIE) && <MovieInfo info={item}/>}
                    {(item.type === SERIES) && <SeriesInfo info={item}/>}
                    {item.genres &&
                    <><h3>Genres:</h3>
                    <div id={"genre"}>{item.genres.map((genre) => (
                        <p key={genre.id}>{genre.name}</p>
                    ))}</div></>}
                    {item.in_production && <p>In Production!</p>}

                    {item.seasons && <div>Seasons: {item.seasons.map((season) => (
                        <div key={season.id}>
                        <p>Season {season.season_number}: {season.name}</p>
                        <p>Number of episodes: {season.episode_count}</p>
                        <p>{season.air_date}</p>
                        <p>Overview: {season.overview}</p>
                            <img src={"https://image.tmdb.org/t/p/w500" + season.poster_path} alt={"Poster"}/>
                        </div>
                        ))}</div>}
                    {item.cast &&  <Cast castlist={item.cast}/>}
                    {item.crew &&  <Crew crewlist={item.crew}/>}
                    {item.de &&  (<div>
                            {item.de.flatrate && <>
                            <h3>Streamable at:</h3>
                            <ProviderElement list={item.de.flatrate}/>
                            </>}
                            {item.de.buy &&
                            <>
                            <h3>Buy at:</h3>
                            <ProviderElement list={item.de.buy}/>
                            </>}
                    </div>)}
                    {item && <AddRemoveWatchButtons
                        onWatchlist={watchlist.find(watchedItem => watchedItem.imdbID === item.imdbID)}
                        onWatchHistory={watchHistory.find(watchedItem => watchedItem.imdbID === item.imdbID)}
                        item={item}/>}
            </>)}

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
    display: flex;
    flex-wrap: wrap;
    min-width: 20vh;
    justify-content: space-evenly;
  }
`