import Default from "../resources/couchpotato.jpg";
import styled from "styled-components/macro";

export default function EpisodeCard({episode}){

    return (
        <Episode>
            <div id={"section"}>
                {episode.still_path ?
                    <img src={"https://image.tmdb.org/t/p/w500" + episode.still_path} alt={"Poster"}/> :
                    <img src={Default} alt={"altposter"}/>
                }
                <div id={"details"}>
                    {episode.name ? <h3>{episode.name}</h3> : <h3>Episode {episode.episode_number}</h3>}
                    <p>{episode.air_date}</p>
                    <button>Add to watch history</button>
                </div>
            </div>
            {episode.overview &&
            <details>
                <summary>Overview</summary>
                <p>{episode.overview}</p>
            </details>}
        </Episode>
    )
}

const Episode = styled.div`
margin-bottom: 5vh;
`