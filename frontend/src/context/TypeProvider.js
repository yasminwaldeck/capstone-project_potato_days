import TypeContext from "./TypeContext";

export default function TypeProvider({children}){
    const MOVIE = "movie"
    const SERIES = "series"

    return (
        <TypeContext.Provider value={{MOVIE, SERIES}}>
            {children}
        </TypeContext.Provider>
    )
}