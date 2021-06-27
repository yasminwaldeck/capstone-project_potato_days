import useWatchlist from "../hooks/useWatchlist";
import {useEffect, useState} from "react";
import {ReactComponent as Add} from "../resources/plus-lg.svg";
import {ReactComponent as Remove} from "../resources/dash-lg.svg";
import styled from "styled-components";
import useWatchHistory from "../hooks/useWatchHistory";
import RecommendedInputElement from "./RecommendedInputElement";
import useRecommendedBy from "../hooks/useRecommendedBy";

export default function AddRemoveWatchButtons({item, onWatchlist, onWatchHistory}){

    const [watch, setWatch] = useState(onWatchlist)
    const [watchHistory, setWatchHistory] = useState(onWatchHistory)
    const { addToWatchlist, removeFromWatchlist } = useWatchlist();
    const { addToHistory, removeFromHistory } = useWatchHistory();
    const { name } = useRecommendedBy(item.imdbID)

    useEffect(() => {setWatch(onWatchlist)
        setWatchHistory(onWatchHistory)},[onWatchlist, onWatchHistory])

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

return (
    <Buttons>
        {watch && <RecommendedInputElement imdbID={item.imdbID} preSetName={name}/>}
        {!watch && <button className={"btn btn-add"} onClick={addList}> <Add className="icon"/> Add to watchlist</button>}
        {watch && <button className="btn btn-remove" onClick={removeList}><Remove/> Remove from watchlist</button>}
        {!watchHistory && <button className="btn btn-add" onClick={addHistory}> <Add className="icon"/> Add to watchhistory</button>}
        {watchHistory && <button className="btn btn-remove" onClick={removeHistory}><Remove/> Remove from watchhistory</button>}
    </Buttons>
)
}

const Buttons = styled.div`
  width: 100%;
  justify-content: flex-end;
  margin: 1vh auto 2vh auto;


  .btn {
    border: none;
    font-size: inherit;
    color: white;
    cursor: pointer;
    padding: 10px 20px;
    margin: 15px auto 15px auto;
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