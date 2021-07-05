import { useState } from "react";
import RecommendationsCard from "./RecommendationsCard";

export default function StatsCard({ recommendationDetails }) {
  const [showing, setShowing] = useState();

  return (
    <div>
      <p>Name: {recommendationDetails.name}</p>
      <p>Score: {recommendationDetails.number}</p>
      <button onClick={() => setShowing(!showing)}>Show recommendations</button>
      <div style={{ display: showing ? "block" : "none" }}>
        {recommendationDetails.recommendations &&
          recommendationDetails.recommendations.map((recommendation) => (
            <RecommendationsCard imdbID={recommendation} />
          ))}
      </div>
    </div>
  );
}
