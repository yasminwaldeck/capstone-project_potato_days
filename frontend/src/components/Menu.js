import { NavLink } from "react-router-dom";
import styled from "styled-components/macro";

export default function Menu() {
  return (
    <Nav role="navigation">
      <div id="menuToggle">
        <input type="checkbox" id={"toggle"} />
        <span></span>
        <span></span>
        <span></span>
        <ul id="menu">
          <li>
            <StyledNavLink
              to={"/home"}
              onClick={() =>
                (document.getElementById("toggle").checked = false)
              }
            >
              HOME
            </StyledNavLink>
          </li>
          <li>
            <StyledNavLink
              to={"/search"}
              onClick={() =>
                (document.getElementById("toggle").checked = false)
              }
            >
              SEARCH
            </StyledNavLink>
          </li>
          <li>
            <StyledNavLink
              to={"/watchlist"}
              onClick={() =>
                (document.getElementById("toggle").checked = false)
              }
            >
              WATCHLIST
            </StyledNavLink>
          </li>
          <li>
            <StyledNavLink
              to={"/trending"}
              onClick={() =>
                (document.getElementById("toggle").checked = false)
              }
            >
              TRENDING
            </StyledNavLink>
          </li>
          <li>
            <StyledNavLink
              to={"/history"}
              onClick={() =>
                (document.getElementById("toggle").checked = false)
              }
            >
              HISTORY
            </StyledNavLink>
          </li>
          <li>
            <StyledNavLink
              to={"/stats"}
              onClick={() =>
                (document.getElementById("toggle").checked = false)
              }
            >
              STATS
            </StyledNavLink>
          </li>
        </ul>
      </div>
    </Nav>
  );
}

const Nav = styled.nav`
  text-align: left;

  ul {
    padding: 0;
    list-style-type: none;
  }

  #menuToggle {
    display: flex;
    flex-direction: column;
    position: relative;
    -webkit-user-select: none;
    user-select: none;
  }

  #menuToggle input {
    display: flex;
    width: 40px;
    height: 32px;
    position: absolute;
    cursor: pointer;
    opacity: 0;
    z-index: 2;
  }

  #menuToggle span {
    display: flex;
    width: 29px;
    height: 2px;
    margin-bottom: 5px;
    position: relative;
    background: #ffffff;
    border-radius: 3px;
    z-index: 1;
    transform-origin: 5px 0px;
    transition: transform 0.5s cubic-bezier(0.77, 0.2, 0.05, 1),
      background 0.5s cubic-bezier(0.77, 0.2, 0.05, 1), opacity 0.55s ease;
  }

  #menuToggle span:first-child {
    transform-origin: 0% 0%;
    background: #ffffff;
  }

  #menuToggle span:nth-last-child(2) {
    transform-origin: 0% 100%;
    background: #ffffff;
  }

  #menuToggle input:checked ~ span {
    opacity: 1;
    transform: rotate(45deg) translate(-3px, -1px);
    background: #ffffff;
  }

  #menuToggle input:checked ~ span:nth-last-child(3) {
    opacity: 0;
    transform: rotate(0deg) scale(0.2, 0.2);
    background: #ffffff;
  }

  #menuToggle input:checked ~ span:nth-last-child(2) {
    transform: rotate(-45deg) translate(0, -1px);
    background: #ffffff;
  }

  #menu {
    position: absolute;
    width: 70vw;
    height: 75vh;
    box-shadow: 0 0 10px #85888c;
    margin: -50px 0 0 -50px;
    padding: 125px 50px 50px;
    background-color: #454546;
    -webkit-font-smoothing: antialiased;
    transform-origin: 0% 0%;
    transform: translate(-100%, 0);
    transition: transform 0.5s cubic-bezier(0.77, 0.2, 0.05, 1);
  }

  #menu li {
    padding: 10px 0;
    transition-delay: 2s;
  }

  #menuToggle input:checked ~ ul {
    transform: none;
  }
`;

const StyledNavLink = styled(NavLink)`
    text-decoration: none;
    font-size: 3vh;
    color: #eae9f1;  
    margin-left: 10vw;
    
    &:focus, &:hover, &:visited, &:link, &:active {
        text-decoration: none;
      color: #eae9f1;
    
    `;
