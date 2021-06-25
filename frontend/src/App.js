import './App.css';
import SearchPage from "./pages/SearchPage";
import Header from "./components/Header";
import TypeProvider from "./context/TypeProvider";
import {Switch, Route} from "react-router-dom";
import MovieAndSeriesDetailsCard from "./components/MovieAndSeriesDetailsCard";
import LandingPage from "./pages/LandingPage";
import WatchlistPage from "./pages/WatchlistPage";
import TrendingMovieAndSeriesPage from "./pages/TrendingMovieAndSeriesPage";
import WatchHistoryPage from "./pages/WatchHistoryPage";
import StatsPage from "./pages/StatsPage";

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
        </TypeProvider>
    </div>
  );
}

export default App;
