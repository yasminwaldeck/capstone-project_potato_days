import styled from 'styled-components'
import {NavLink} from "react-router-dom";
import Menu from "./Menu";

export default function Header(){
    return(
        <Title>
            <Menu/>
            <h1>
                <StyledNavLink to={"/"} style={{ textDecoration: 'none' }}>POTATO DAYS</StyledNavLink>
            </h1>
        </Title>
    )


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
  justify-items: center;
`

const StyledNavLink = styled(NavLink)`
    text-decoration: none;

  color: white;  
    &:focus, &:hover, &:visited, &:link, &:active {
        text-decoration: none;
      color: white;
    
`
