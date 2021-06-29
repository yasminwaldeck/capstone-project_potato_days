import './App.css';
import SearchPage from "./pages/SearchPage";
import Header from "./components/Header";
import TypeProvider from "./context/TypeProvider";
import {Switch, Route, BrowserRouter as Router} from "react-router-dom";
import MovieAndSeriesDetailsPage from "./pages/MovieAndSeriesDetailsPage";
import LandingPage from "./pages/LandingPage";
import WatchlistPage from "./pages/WatchlistPage";
import TrendingMovieAndSeriesPage from "./pages/TrendingMovieAndSeriesPage";
import WatchHistoryPage from "./pages/WatchHistoryPage";
import StatsPage from "./pages/StatsPage";
import SeasonPage from "./pages/SeasonPage";

function App() {
  return (
    <div className="App">
        <TypeProvider>
            <Router>
                <Header/>
                <Switch>
                    <Route path={"/search"}>
                        <SearchPage/>
                    </Route>
                    <Route path={"/details/:imdbid/:id/:season"} exact>
                        <SeasonPage/>
                    </Route>
                    <Route path={"/details/:id"} exact>
                        <MovieAndSeriesDetailsPage/>
                    </Route>
                    <Route path={"/watchlist"}>
                        <WatchlistPage/>
                    </Route>
                    <Route path={"/trending"}>
                        <TrendingMovieAndSeriesPage/>
                    </Route>
                    <Route path={"/history"}>
                        <WatchHistoryPage/>
                    </Route>
                    <Route path={"/stats"}>
                        <StatsPage/>
                    </Route>
                    <Route path={"/"}>
                        <LandingPage/>
                    </Route>
                </Switch>
            </Router>
        </TypeProvider>
    </div>
  );
}

export default App;
