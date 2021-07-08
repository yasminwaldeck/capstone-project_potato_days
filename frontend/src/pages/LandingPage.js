import useRandom from "../hooks/useRandom";
import TypeAndAuthContext from "../context/TypeAndAuthContext";
import { useContext } from "react";
import RandomCard from "../components/RandomCard";
import LoadingSpinner from "../components/LoadingSpinner";
import styled from "styled-components/macro";

export default function LandingPage() {
  const { watchlistItem, watchhistoryItem, isLoading } = useRandom();
  const { name } = useContext(TypeAndAuthContext);

  return (
    <Home>
      <h2>Hello {name} :)</h2>
      <div id={"random"}>
        {isLoading && <LoadingSpinner />}
        {watchlistItem && (
          <div>
            <h3>How about watching something from your watchlist?</h3>
            <RandomCard item={watchlistItem} />
          </div>
        )}
        {watchhistoryItem && (
          <div>
            <h3>Or continue watching this?</h3>
            <RandomCard item={watchhistoryItem} />
          </div>
        )}
      </div>
    </Home>
  );
}

const Home = styled.div`
  width: 90vw;
  margin: auto;
  padding-bottom: 3vh;
`
