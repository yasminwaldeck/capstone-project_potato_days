import {NavLink} from "react-router-dom";

export default function LandingPage(){

    return(
        <div>
            <h2>Hello potato :)</h2>
            <div><NavLink to={"/search"}>GO TO SEARCH</NavLink></div>
            <div><NavLink to={"/watchlist"}>GO TO WATCHLIST</NavLink></div>
            <div><NavLink to={"/trending"}>GO TO TRENDING MOVIES AND SERIES</NavLink></div>
        </div>
    )
}