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
import PrivateRoute from "./routing/PrivateRoute";

function App() {
  return (
    <div className="App">
        <Router>
            <TypeAndAuthProvider>
                <Header/>
                <Switch>
                    <PrivateRoute path={"/search"}>
                        <SearchPage/>
                    </PrivateRoute>
                    <PrivateRoute path={"/details/:imdbid/:id/:season"} exact>
                        <SeasonPage/>
                    </PrivateRoute>
                    <PrivateRoute path={"/details/:id"} exact>
                        <MovieAndSeriesDetailsPage/>
                    </PrivateRoute>
                    <PrivateRoute path={"/watchlist"}>
                        <WatchlistPage/>
                    </PrivateRoute>
                    <PrivateRoute path={"/trending"}>
                        <TrendingMovieAndSeriesPage/>
                    </PrivateRoute>
                    <PrivateRoute path={"/history"}>
                        <WatchHistoryPage/>
                    </PrivateRoute>
                    <PrivateRoute path={"/stats"}>
                        <StatsPage/>
                    </PrivateRoute>
                    <Route path={"/login"}>
                        <LoginPage/>
                    </Route>
                    <PrivateRoute path={"/home"}>
                        <LandingPage/>
                    </PrivateRoute>
                </Switch>
            </TypeAndAuthProvider>
        </Router>
    </div>
  );
}

export default App;
