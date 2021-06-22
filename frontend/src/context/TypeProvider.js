import TypeContext from "./TypeContext";

export default function TypeProvider({children}){
    const MOVIE = "movie"
    const SERIES = "series"
    const BOTH = "both"


    return (
        <TypeContext.Provider value={{MOVIE, SERIES, BOTH}}>
            {children}
        </TypeContext.Provider>
    )
}