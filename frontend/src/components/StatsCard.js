import { useState } from "react";
import RecommendationsCard from "./RecommendationsCard";
import styled from "styled-components/macro";

export default function StatsCard({ recommendationDetails }) {
  const [showing, setShowing] = useState();

  return (
    <Recommended>
      <p>
        <b>Name:</b> {recommendationDetails.name}
      </p>
      <p>
        <b>Recommendations:</b> {recommendationDetails.number}
      </p>
      {showing ? (
        <input
          type="checkbox"
          onClick={() => setShowing(!showing)}
          label={"Hide recommendations"}
        />
      ) : (
        <input
          type="checkbox"
          onClick={() => setShowing(!showing)}
          label={"Show recommendations"}
        />
      )}
      <div style={{ display: showing ? "block" : "none" }}>
        {recommendationDetails.recommendations &&
          recommendationDetails.recommendations.map((recommendation) => (
            <RecommendationsCard imdbID={recommendation} />
          ))}
      </div>
    </Recommended>
  );
}

const Recommended = styled.div`
  position: relative;
  width: 80vw;
  margin: auto auto 2vh auto;
  background: #49494a;
  border-radius: 10px;
  padding-bottom: 1vh;
  padding-top: 1vh;

  input {
    height: 100%;
    width: auto;
    margin-top: 1.5vh;
    margin-bottom: 1.5vh;
    background-image: linear-gradient(180deg, #828282, #48484a);
    appearance: none;
    outline: none;
    cursor: pointer;
    border-radius: 2px;
    padding: 4px 8px;
    font-size: 16px;
    transition: all 100ms linear;
  }

  input:checked {
    background-image: linear-gradient(180deg, #48484a, #828282);
  }

  input:before {
    content: attr(label);
    display: inline-block;
    text-align: center;
    width: 100%;
  }
`;
