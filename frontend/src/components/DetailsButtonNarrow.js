import { ReactComponent as Info } from "../resources/info.svg";
import styled from "styled-components/macro";
import { NavLink } from "react-router-dom";

export default function DetailsButtonsNarrow({ imdbID }) {
  return (
    <Button>
      <StyledNavLink to={"/details/" + imdbID}>
        <button id={"btn"}>
          <Info id={"icon"} />
          Details!
        </button>
      </StyledNavLink>
    </Button>
  );
}

const Button = styled.div`
  #btn {
    border: none;
    margin-top: 1.5vh;
    font-size: inherit;
    color: white;
    cursor: pointer;
    padding: 10px 25px 10px 20px;
    text-transform: uppercase;
    font-weight: 700;
    outline: none;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
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
