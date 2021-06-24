import {NavLink} from "react-router-dom";
import styled from "styled-components";
import AddRemoveWatchButtons from "./AddRemoveWatchButtons";

export default function MovieAndSeriesCard({item, onWatchlist, onWatchHistory}){


    return(
        <OverviewCard>
            <img src={item.poster} alt={"Poster"}/>

                <h3>{item.title} ({item.year})</h3>
                <div id="buttons">
                    <NavLink to={("/details/" + item.imdbID)}>
                        <button>
                            Details!
                        </button>
                    </NavLink>
                    <AddRemoveWatchButtons
                        onWatchlist={onWatchlist}
                        onWatchHistory={onWatchHistory}
                        item={item}/>
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

`