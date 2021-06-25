import {useState} from "react";
import useRecommendedBy from "../hooks/useRecommendedBy";

export default function RecommendedInputElement({imdbID, preSetName}){

    const { setRecommendedBy, deleteRecommendedBy } = useRecommendedBy(imdbID);
    const [input, setInput] = useState()
    const [sent, setSent] = useState()
    const [name, setName] = useState("")
    const [deleted, setDeleted] = useState(false)


    return(
        <div>

            {
                ((!sent && preSetName === "") || (deleted)) &&
                <div>
                <input type="text"
                value={input}
                onChange={(event) => setInput(event.target.value)}/>
                <button onClick={() => {
                setRecommendedBy(imdbID, input);
                setSent(true);
                setDeleted(false);
                setName(input)
            }}>Set
                </button>
                </div>
            }
            {
                ((preSetName !== "" || name !== "")  || sent ) && !deleted &&
                <div>
            {sent ? <p>Recommended by: {input}</p> : <p>Recommended by: {preSetName}</p>}
                <button onClick={() => {setSent(false); setDeleted(true);  deleteRecommendedBy(imdbID);}}>clear</button>
                </div>
            }

        </div>
    )
}