import styled from "styled-components/macro";
import { NavLink } from "react-router-dom";
import Menu from "./Menu";
import Potato from "../resources/tenor.gif";

export default function Header() {
  return (
    <Title>
      <Menu />
      <h1>
        <StyledNavLink to={"/home"}>POTATO DAYS</StyledNavLink>
      </h1>
      <img src={Potato} alt={"logo"} />
    </Title>
  );
}

const Title = styled.div`
  margin: 0 0 5vh;
  width: 100%;
  background: #454546;
  color: white;
  display: grid;
  align-items: center;
  display: grid;
  grid-template-columns: 20vw auto 20vw;
  align-items: center;
  align-content: center;
  justify-items: center;
  position: sticky;
  top: 0px;
  z-index: 5;
  img {
    width: 60px;
    height: auto;
    transform: scaleX(-1);
    z-index: -1;
  }
`;

const StyledNavLink = styled(NavLink)`
    text-decoration: none;

  color: white;  
    &:focus, &:hover, &:visited, &:link, &:active {
        text-decoration: none;
      color: white;
    
`;
