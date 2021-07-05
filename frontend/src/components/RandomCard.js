import styled from "styled-components/macro";
import Default from "../resources/couchpotato.jpg";
import DetailsButtonsNarrow from "./DetailsButtonNarrow";

export default function RandomCard({ item }) {
  return (
    <Random>
      <section>
        {item.poster_path ? (
          <img src={item.poster_path} alt={"Poster"} />
        ) : (
          <img src={Default} alt={"altposter"} />
        )}
        <div id={"details"}>
          <h3>{item.title}</h3>
          <h4>{item.year}</h4>
          {item.progress > 0 && (
            <div>
              <p>Progress: {item.progress.toFixed(1)}%</p>
              <progress value={item.progress} max="100" />
            </div>
          )}
          <DetailsButtonsNarrow imdbID={item.imdbId} id={"button"}/>
        </div>
      </section>
    </Random>
  );
}

const Random = styled.div`
  width: 90vw;
  margin: 0 auto 5vh auto;
  
  h3{
    margin-top:1vh;
    margin-bottom: 2vh;
  }
  h4{
    margin-top: 2vh;
  }

  img {
    max-width: 40vw;
    max-height: 28vh;
  }

  #details {
    height: 30vh;
  }

  section {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
  }
`;

