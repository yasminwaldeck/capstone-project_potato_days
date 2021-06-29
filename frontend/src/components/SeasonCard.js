import styled from "styled-components/macro";
import Default from "../resources/couchpotato.jpg"
import {NavLink, useParams} from "react-router-dom";

export default function SeasonCard({season, tmdbid}) {
    const {id} = useParams();

    return (
        <Season>
            <div id={"section"}>
                {season.poster_path ?
                    <img src={"https://image.tmdb.org/t/p/w500" + season.poster_path} alt={"Poster"}/> :
                    <img src={Default} alt={"altposter"}/>
                }
                <div id={"details"}>
                    {season.name ? <h3>{season.name}</h3> : <h3>Season {season.season_number}</h3>}
                    <p>Episodes: {season.episode_count}</p>
                    <p>{season.air_date}</p>
                    <NavLink to={ id + "/" + tmdbid + "/" + season.season_number}>
                        <button>Go to episodes</button>
                    </NavLink>
                </div>
            </div>
            {season.overview &&
            <details>
                <summary>Overview</summary>
                <p>{season.overview}</p>
            </details>}
        </Season>
    )

}

const Season = styled.div`
  width: 90vw;
  margin: 0 auto 5vh auto;
  
  img{
      max-width: 40vw;
     max-height:30vh;
  }
  
  #details{
      height: 30vh;
  }
  
  #section{
    display: flex;
    flex-direction: row;
    justify-content: space-around;
  }
  details{
    margin-top: 3vh;
  }
  `