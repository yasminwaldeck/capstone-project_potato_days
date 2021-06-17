import {NavLink} from "react-router-dom";

export default function MovieAndSeriesCard({item}){



    return(
        <div>
            <img src={item.poster}/>
            <h3>{item.title} ({item.year})</h3>
            <div>
                <NavLink to={("/details/" + item.imdbID)}>Details!</NavLink>
            </div>
        </div>
    )
}