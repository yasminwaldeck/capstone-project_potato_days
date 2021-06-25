import {NavLink} from "react-router-dom";
import useWatchlist from "../hooks/useWatchlist";
import {useState} from "react";
import styled from "styled-components";
import {ReactComponent as Add} from "../resources/plus-lg.svg"
import {ReactComponent as Remove} from "../resources/dash-lg.svg"
import useWatchHistory from "../hooks/useWatchHistory";

export default function MovieAndSeriesCard({item, onWatchlist, onWatchHistory}){

    const [watch, setWatch] = useState(onWatchlist)
    const [watchHistory, setWatchHistory] = useState(onWatchHistory)
    const { addToWatchlist, removeFromWatchlist } = useWatchlist();
    const { addToHistory, removeFromHistory } = useWatchHistory();

    const addList = () => {
        addToWatchlist(item.imdbID, item.type)
        setWatch("watched")
    }

    const removeList = () => {
        removeFromWatchlist(item)
        setWatch(null)
    }

    const addHistory = () => {
        addToHistory(item.imdbID, item.type)
        setWatchHistory("watched")
    }

    const removeHistory = () => {
        removeFromHistory(item)
        setWatchHistory(null)
    }

    return(
        <OverviewCard>
            <img src={item.poster}/>

                <h3>{item.title} ({item.year})</h3>
                <div id="buttons">
                    <NavLink to={("/details/" + item.imdbID)}><button>Details!</button></NavLink>
                    {!watch && <button className="btn btn-add" onClick={addList}> <Add class="icon"/> Add to watchlist</button>}
                    {watch && <button className="btn btn-remove" onClick={removeList}><Remove/> Remove from watchlist</button>}
                    {!watchHistory && <button className="btn btn-add" onClick={addHistory}> <Add class="icon"/> Add to watchhistory</button>}
                    {watchHistory && <button className="btn btn-remove" onClick={removeHistory}><Remove/> Remove from watchhistory</button>}
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

  h3 {
    padding: 1vh;
    margin: 0
  }

  #buttons {
    justify-content: flex-end;
    margin: 1vh auto 2vh auto;
  }

  .btn {
    border: none;
    font-size: inherit;
    color: white;
    cursor: pointer;
    padding: 10px 20px;
    margin: 15px 30px;
    text-transform: uppercase;
    font-weight: 700;
    outline: none;
    position: relative;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 60vw;

    .icon {
      margin: 0 10px 0 0;
    }
  }

  .btn:after {
    content: '';
    position: absolute;
    z-index: -1;
    -webkit-transition: all 0.3s;
    -moz-transition: all 0.3s;
    transition: all 0.3s;
  }

  .btn:before {
    line-height: 1;
    position: relative;
  }


  .btn-add {
    background: #00916E;
  }

  .btn-add:hover {
    background: #00664E;
  }

  .btn-add:active {
    background: #00664E;
    top: 2px;
  }


  .btn-remove {
    background: #66000E;
  }

  .btn-remove:hover {
    background: #8F0013;
  }

  .btn-remove:active {
    background: #8F0013;
    top: 2px;
  }



`