import {useState} from "react";
import useRecommendedBy from "../hooks/useRecommendedBy";
import styled from "styled-components";
import {ReactComponent as Remove} from "../resources/dash-lg.svg";
import {ReactComponent as Add} from "../resources/plus-lg.svg";

export default function RecommendedInputElement({imdbID, preSetName}){

    const { setRecommendedBy, deleteRecommendedBy } = useRecommendedBy(imdbID);
    const [input, setInput] = useState()
    const [sent, setSent] = useState()
    const [name, setName] = useState("")
    const [deleted, setDeleted] = useState(false)


    return(
        <RecommendedField>

            {
                ((!sent && preSetName === "") || (deleted)) &&
                <div>
                    <p>Recommended by:</p>
                <input type="text"
                value={input}
                onChange={(event) => setInput(event.target.value)}/>
                <button onClick={() => {
                    setRecommendedBy(imdbID, input);
                    setSent(true);
                    setDeleted(false);
                    setName(input)
                    }}
                        className={"btn add"}
                >
                    <Add id="addicon"/>
                </button>
                </div>
            }
            {
                ((preSetName !== "" || name !== "")  || sent ) && !deleted &&
                <div>
            {sent ? <p>Recommended by: {input}</p> : <p>Recommended by: {preSetName}</p>}
                <button onClick={() => {
                    setSent(false);
                    setDeleted(true);
                    deleteRecommendedBy(imdbID);}}
                    className={"btn remove"}>
                    <Remove id={"icon"}/>
                </button>
                </div>
            }

        </RecommendedField>
    )
}


const RecommendedField = styled.div`
  .btn {
    border: none;
    font-size: inherit;
    color: white;
    cursor: pointer;
    height: 20px;
    width: 20px;
    padding: 5px;
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
  }
  .remove {
    background: #66000E;
    border-radius: 50%;
  }
  .remove:hover {
    background: #8F0013;
  }

  .remove:active {
    background: #8F0013;
    top: 2px;
  }

  .add {
    background: #00916E;
    border-radius: 50%;
  }

  .add:hover {
    background: #00664E;
  }

  .add:active {
    background: #00664E;
    top: 2px;
  }
  
  .icon{
    width: 15px;
    height: auto;
  }

  
`