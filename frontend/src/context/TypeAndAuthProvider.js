import TypeAndAuthContext from "./TypeAndAuthContext";
import {useState} from "react";
import axios from "axios";
import {useHistory} from "react-router-dom";

export default function TypeAndAuthProvider({children}){
    const MOVIE = "movie"
    const SERIES = "series"
    const BOTH = "both"
    const DAY = "day"
    const WEEK = "week"

    const [token, setToken] = useState()
    const history = useHistory()

    const login = credentials => {
        axios
            .post('/auth/login', credentials)
            .then(response => response.data)
            .then(setToken)
            .then(() => history.push("/home"))
            .catch(error => console.error(error.message))
    }

     return (
        <TypeAndAuthContext.Provider value={{MOVIE, SERIES, BOTH, DAY, WEEK, login, token}}>
            {children}
        </TypeAndAuthContext.Provider>
    )
}