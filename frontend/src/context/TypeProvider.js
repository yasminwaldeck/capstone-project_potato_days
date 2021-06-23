import TypeContext from "./TypeContext";

export default function TypeProvider({children}){
    const MOVIE = "movie"
    const SERIES = "series"
    const BOTH = "both"
    const DAY = "day"
    const WEEK = "week"


    return (
        <TypeContext.Provider value={{MOVIE, SERIES, BOTH, DAY, WEEK}}>
            {children}
        </TypeContext.Provider>
    )
}