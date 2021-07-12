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
          <div>
            <h3>{item.title}</h3>
            <h4>{item.year}</h4>
          </div>
          {item.progress > 0 && (
            <div>
              <p>Progress: {item.progress.toFixed(1)}%</p>
              <progress value={item.progress} max="100" />
            </div>
          )}
          <DetailsButtonsNarrow imdbID={item.imdbId} id={"button"} />
        </div>
      </section>
    </Random>
  );
}

const Random = styled.div`
  width: 80vw;
  margin: auto;
  height: 33vh;
  background: #414142;
  border-radius: 10px;
  padding-left: 3vw;
  padding-right: 3vw;

  h3 {
    margin-top: 0;
    margin-bottom: 2vh;
  }
  h4 {
    margin-top: 2vh;
    margin-bottom: 0vh;
  }
  p {
    margin-top: 0;
    margin-bottom: 1vh;
  }
  progress {
    -webkit-appearance: none;
    appearance: none;
    width: 35vw;
    height: 2vh;
    border-radius: 50px;
    background-color: #dededf;

    ::-moz-progress-bar {
      background-image: linear-gradient(180deg, #828282, #48484a);
      border-radius: 50px;
    }
    ::-webkit-progress-bar {
      background-image: linear-gradient(180deg, #828282, #48484a);
      border-radius: 50px;
    }
    ::-webkit-progress-value {
      background-image: linear-gradient(180deg, #828282, #48484a);
      border-radius: 50px;
    }
  }

  img {
    max-width: 40vw;
    max-height: 26vh;
    margin: auto auto auto 4vw;
  }

  #details {
    height: 30vh;
    margin-left: 3vh;
    margin-right: 3vh;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }

  section {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    padding-top: 1.5vh;
  }
`;
