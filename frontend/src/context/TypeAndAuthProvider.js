import TypeAndAuthContext from "./TypeAndAuthContext";
import {useState} from "react";
import axios from "axios";
import {useHistory} from "react-router-dom";
import jwt_decode from "jwt-decode";

export default function TypeAndAuthProvider({children}){
    const MOVIE = "movie"
    const SERIES = "series"
    const BOTH = "both"
    const DAY = "day"
    const WEEK = "week"

    const [token, setToken] = useState()
    const [name, setName] = useState()
    const history = useHistory()

    const login = credentials => {
        axios
            .post('/auth/login', credentials)
            .then(response => response.data)
            .then(data => {
                setToken(data)
                setName(jwt_decode(data.toString()).sub)
            })
            .then(() => history.push("/home"))
            .catch(error => console.error(error.message))
    }

     return (
        <TypeAndAuthContext.Provider value={{MOVIE, SERIES, BOTH, DAY, WEEK, login, token, name}}>
            {children}
        </TypeAndAuthContext.Provider>
    )
}