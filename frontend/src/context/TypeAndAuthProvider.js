import TypeAndAuthContext from "./TypeAndAuthContext";
import {useState} from "react";
import axios from "axios";
import jwt_decode from "jwt-decode";

export default function TypeAndAuthProvider({children}){
    const MOVIE = "movie"
    const SERIES = "series"
    const BOTH = "both"
    const DAY = "day"
    const WEEK = "week"

    const [token, setToken] = useState()
    const [jwtDecoded, setJwtDecoded] = useState()

    const login = credentials => {
        axios
            .post('/auth/login', credentials)
            .then(response => response.data)
            .then(data => {
                setToken(data)
                setJwtDecoded(jwt_decode(data.toString()))
                console.log(jwt_decode(data.toString()))
                console.log(data)
            })
            .catch(error => console.error(error.message))
    }

     return (
        <TypeAndAuthContext.Provider value={{MOVIE, SERIES, BOTH, DAY, WEEK, login}}>
            {children}
        </TypeAndAuthContext.Provider>
    )
}