import Default from "../resources/couchpotato.jpg";
import styled from "styled-components/macro";
import {useParams} from "react-router-dom";

export default function EpisodeCard({episode, addEpisodeToHistory, removeEpisodeFromHistory, onWatchHistory}){
    const {imdbid} = useParams();

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
                    {onWatchHistory ?
                    <button onClick={() => removeEpisodeFromHistory(imdbid, episode.season_number, episode.episode_number)}>Remove from watch history</button> :
                        <button onClick={() => addEpisodeToHistory(imdbid, episode.season_number, episode.episode_number)}>Add to watch history</button>}
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