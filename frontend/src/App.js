import './App.css';
import SearchPage from "./pages/SearchPage";
import Header from "./components/Header";
import TypeProvider from "./context/TypeProvider";
import {Switch, Route} from "react-router-dom";
import MovieAndSeriesDetailsCard from "./components/MovieAndSeriesDetailsCard";
import LandingPage from "./pages/LandingPage";
import WatchlistPage from "./pages/WatchlistPage";
import TrendingMovieAndSeriesPage from "./pages/TrendingMovieAndSeriesPage";

function App() {
  return (
    <div className="App">
        <TypeProvider>
                <Header/>
                <Switch>
                    <Route path={"/search"}>
                        <SearchPage/>
                    </Route>
                    <Route path={"/details/:id"}>
                        <MovieAndSeriesDetailsCard/>
                    </Route>
                    <Route path={"/watchlist"}>
                        <WatchlistPage/>
                    </Route>
                    <Route path={"/trending"}>
                        <TrendingMovieAndSeriesPage/>
                    </Route>
                    <Route path={"/"}>
                        <LandingPage/>
                    </Route>
                </Switch>
        </TypeProvider>
    </div>
  );
}

export default App;
