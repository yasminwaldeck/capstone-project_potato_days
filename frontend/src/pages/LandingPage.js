import {NavLink} from "react-router-dom";
import styled from "styled-components/macro";

export default function LandingPage(){

    return(
        <div>
            <h2>Hello potato :)</h2>
            <div><StyledNavLink to={"/search"}>GO TO SEARCH</StyledNavLink></div>
            <div><StyledNavLink to={"/watchlist"}>GO TO WATCHLIST</StyledNavLink></div>
            <div><StyledNavLink to={"/history"}>GO TO WATCHHISTORY</StyledNavLink></div>
            <div><StyledNavLink to={"/trending"}>GO TO TRENDING MOVIES AND SERIES</StyledNavLink></div>
            <div><StyledNavLink to={"/stats"}>GO TO STATS</StyledNavLink></div>
        </div>
    )
}

const StyledNavLink = styled(NavLink)`
    text-decoration: none;

    color: #eae9f1;  
    &:focus, &:hover, &:visited, &:link, &:active {
        text-decoration: none;
        color: #eae9f1;
    
`