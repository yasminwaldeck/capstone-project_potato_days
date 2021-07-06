import styled from "styled-components/macro";
import Ratings from "./Ratings";

export default function MovieInfo({ info }) {
  return (
    <>
      {info && (
        <Info>
          <div className={"basicInfo"}>
            <div>
              <p>Year:</p>
              <p>{info.year}</p>
            </div>
            <div>
              <p>Runtime:</p>
              <p>{info.runtime}</p>
            </div>
            <div>
              <p>Country:</p>
              <p>{info.country}</p>
            </div>
          </div>
          {info.release_date && <p>Released: {info.release_date}</p>}
          <h3>Plot:</h3>
          <p>{info.overview}</p>
          {info.in_production && <p>In Production!</p>}
          <Ratings info={info.ratings} />
        </Info>
      )}
    </>
  );
}

const Info = styled.div`
  .basicInfo {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 10px;
  }
`;
