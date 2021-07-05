import { NavLink } from "react-router-dom";
import styled from "styled-components/macro";
import useRandom from "../hooks/useRandom";
import TypeAndAuthContext from "../context/TypeAndAuthContext";
import { useContext } from "react";
import RandomCard from "../components/RandomCard";

export default function LandingPage() {
  const { watchlistItem, watchhistoryItem } = useRandom();
  const { name } = useContext(TypeAndAuthContext);

  return (
    <div>
      <h2>Hello {name} :)</h2>
      {/*<div><StyledNavLink to={"/search"}>GO TO SEARCH</StyledNavLink></div>*/}
      {/*<div><StyledNavLink to={"/watchlist"}>GO TO WATCHLIST</StyledNavLink></div>*/}
      {/*<div><StyledNavLink to={"/history"}>GO TO WATCHHISTORY</StyledNavLink></div>*/}
      {/*<div><StyledNavLink to={"/trending"}>GO TO TRENDING MOVIES AND SERIES</StyledNavLink></div>*/}
      {/*<div><StyledNavLink to={"/stats"}>GO TO STATS</StyledNavLink></div>*/}
      <div id={"random"}>
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
    </div>
  );
}

// const StyledNavLink = styled(NavLink)`
//     text-decoration: none;
//
//     color: #eae9f1;
//     &:focus, &:hover, &:visited, &:link, &:active {
//         text-decoration: none;
//         color: #eae9f1;
//
// `
