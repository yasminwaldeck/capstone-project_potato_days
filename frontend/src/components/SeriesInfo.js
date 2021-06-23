import Ratings from "./Ratings";
import styled from "styled-components";

export default function SeriesInfo({info}){

    return (
        <>
            {info && (
                <Info>
                    <div className={"basicInfo"}>
                        <div><p>Year:</p><p>{info.year}</p></div>
                        <div><p>Runtime:</p><p>{info.runtime}</p></div>
                        <div><p>Country:</p><p>{info.country}</p></div>
                    </div>
                    <div className={"episodeInfo"}>
                        <div><p>Seasons:</p><p>{info.number_of_episodes}</p></div>
                        <div><p>Episodes:</p><p>{info.number_of_seasons}</p></div>
                    </div>
                    {info.first_air_date && <p>First aired: {info.first_air_date}</p>}
                    {info.last_air_date && <p>Last aired: {info.last_air_date}</p>}
                    <h3>Plot:</h3>
                    <p>{info.overview}</p>
                    <Ratings info={info.ratings}/>
                </Info>)}

        </>
    )
}

const Info = styled.div`
 
  .basicInfo {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 10px;
  }

  .episodeInfo {
    display: flex;
    justify-content: space-evenly;

  }
`