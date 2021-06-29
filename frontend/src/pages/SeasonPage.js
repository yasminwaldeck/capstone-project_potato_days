import {useParams} from "react-router-dom";
import useSeasons from "../hooks/useSeasons";
import EpisodeCard from "../components/EpisodeCard";
import styled from "styled-components/macro";

export default function SeasonPage(){

    const {id, season} = useParams();
    const {item} = useSeasons(id, season);

    return(
        <SeasonOverview>
            {item.episodes && item.episodes.map((episode) => (
                <EpisodeCard
                    key={episode.episode_number}
                    episode={episode}
                />
            ))}
        </SeasonOverview>
    )

}

const SeasonOverview = styled.div`
  width: 90vw;
  margin: auto;
  
  img{
    width: 90vw;
  }
`