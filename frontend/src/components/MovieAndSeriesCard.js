import {NavLink} from "react-router-dom";
import useWatchlist from "../hooks/useWatchlist";
import {useState} from "react";
import styled from "styled-components";

export default function MovieAndSeriesCard({item, watched}){

    const [watch, setWatch] = useState(watched)
    const { addToWatchlist, removeFromWatchlist } = useWatchlist();

    const add = () => {
        addToWatchlist(item.imdbID, item.type)
        setWatch("watched")
    }

    const remove = () => {
        removeFromWatchlist(item)
        setWatch(null)
    }

    return(
        <OverviewCard>
            <img src={item.poster}/>

                <h3>{item.title} ({item.year})</h3>
                <div id="buttons">
                    <NavLink to={("/details/" + item.imdbID)}><button>Details!</button></NavLink>
                    {!watch && <button onClick={add}>Add to watchlist</button>}
                    {watch && <button onClick={remove}>Remove from watchlist!</button>}
                </div>

        </OverviewCard>
    )
}

const OverviewCard = styled.div`

  width: 80vw;
  min-height: 50vh;
  background: #AD343E;
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
  h3{
    padding: 1vh;
    margin: 0
  }
  
   #buttons  {
     justify-content: flex-end;
    margin: 1vh auto 2vh auto;
  }
  
 
`