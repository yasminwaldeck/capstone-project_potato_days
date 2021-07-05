import { NavLink } from "react-router-dom";
import styled from "styled-components/macro";
import AddRemoveWatchButtons from "./AddRemoveWatchButtons";
import { ReactComponent as Info } from "../resources/info.svg";

export default function MovieAndSeriesCard({
  item,
  onWatchlist,
  onWatchHistory,
}) {
  return (
    <OverviewCard>
      <img src={item.poster} alt={"Poster"} />

      <h3>
        {item.title} ({item.year})
      </h3>
      <div id="buttons">
        <StyledNavLink to={"/details/" + item.imdbID}>
          <button id={"btn"}>
            <Info id={"icon"} />
            Details!
          </button>
        </StyledNavLink>
        <AddRemoveWatchButtons
          onWatchlist={onWatchlist}
          onWatchHistory={onWatchHistory}
          item={item}
        />
      </div>
    </OverviewCard>
  );
}

const OverviewCard = styled.div`
  width: 80vw;
  min-height: 50vh;
  background: #49494a;
  color: white;
  display: flex;
  flex-direction: column;

  justify-items: center;
  border-radius: 10px;
  margin: 5vh auto 5vh auto;

  img {
    max-width: 27vh;
    height: auto;
    max-height: 40vh;
    padding: 2vh 2vh 1vh 2vh;
    margin: 10px 10px 0 10px;
    align-self: center;
  }

  h3 {
    padding: 1vh;
    margin: 0;
  }

  #btn {
    border: none;
    font-size: inherit;
    color: white;
    cursor: pointer;
    padding: 10px 18vw 10px 20px;
    margin: 15px auto 15px auto;
    text-transform: uppercase;
    font-weight: 700;
    outline: none;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 60vw;
    background-color: #828282;
  }
  #icon {
    margin: 0 10px 0 0;
    width: 15px;
    height: auto;
  }

  #btn:after {
    content: "";
    position: absolute;
    z-index: -1;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
  }

  #btn:before {
    line-height: 1;
    position: relative;
  }
`;

const StyledNavLink = styled(NavLink)`
    text-decoration: none;
    color: #eae9f1;  
  
    &:focus, &:hover, &:visited, &:link, &:active {
        text-decoration: none;
        color: #eae9f1;
    
`;
