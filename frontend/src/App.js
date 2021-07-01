import './App.css';
import SearchPage from "./pages/SearchPage";
import Header from "./components/Header";
import TypeAndAuthProvider from "./context/TypeAndAuthProvider";
import {Switch, Route, BrowserRouter as Router} from "react-router-dom";
import MovieAndSeriesDetailsPage from "./pages/MovieAndSeriesDetailsPage";
import LandingPage from "./pages/LandingPage";
import WatchlistPage from "./pages/WatchlistPage";
import TrendingMovieAndSeriesPage from "./pages/TrendingMovieAndSeriesPage";
import WatchHistoryPage from "./pages/WatchHistoryPage";
import StatsPage from "./pages/StatsPage";
import SeasonPage from "./pages/SeasonPage";
import LoginPage from "./pages/LoginPage";

function App() {
  return (
    <div className="App">
        <TypeAndAuthProvider>
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
                    <Route path={"/login"}>
                        <LoginPage/>
                    </Route>
                    <Route path={"/home"}>
                        <LandingPage/>
                    </Route>
                </Switch>
            </Router>
        </TypeAndAuthProvider>
    </div>
  );
}

export default App;
