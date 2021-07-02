import {useParams} from "react-router-dom";
import useSeasons from "../hooks/useSeasons";
import EpisodeCard from "../components/EpisodeCard";
import styled from "styled-components/macro";
import useWatchHistoryEpisodes from "../hooks/useWatchHistoryEpisodes";

export default function SeasonPage(){

    const {imdbid, id, season} = useParams();
    const {item} = useSeasons(id, season);
    const {episodeWatchHistory, seasonProgress, addEpisodeToHistory, removeEpisodeFromHistory} = useWatchHistoryEpisodes(imdbid, id, season)

    return(
        <SeasonOverview>
            {seasonProgress && (seasonProgress !== 0) ?
                <div id={"progress"}>
                    <h3>Progress: {seasonProgress.toFixed(1)}%</h3>
                    <progress value={seasonProgress} max="100"/>
                </div>:
                <div id={"progress"}>
                    <h3>Progress: 0%</h3>
                    <progress value={0} max="100"/>
                </div>
            }
            <div>
                {item.episodes && item.episodes.map((episode) => (
                    <EpisodeCard
                        key={episode.episode_number}
                        episode={episode}
                        addEpisodeToHistory={addEpisodeToHistory}
                        removeEpisodeFromHistory={removeEpisodeFromHistory}
                        onWatchHistory={episodeWatchHistory.find(watchedItem => watchedItem.episode_number === episode.episode_number)}
                    />
                ))}
            </div>
        </SeasonOverview>
    )

}

const SeasonOverview = styled.div`
  width: 90vw;
  margin: auto;
  #progress{
    margin: 0;
    padding-bottom: 3vh;
    position: sticky;
    top: 81px;
    h3{
      padding-top: 3vh;
      margin-top: 0;
    }
    background-color: #2c2c2d;
  }
  progress{
    -webkit-appearance: none;
    appearance: none;
    height: 5vh;
    width: 90vw;
  }
  
  img{
    width: 90vw;
  }
`