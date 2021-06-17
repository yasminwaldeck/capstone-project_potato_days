import './App.css';
import SearchPage from "./pages/SearchPage";
import Header from "./components/Header";
import TypeProvider from "./context/TypeProvider";
import {Switch, Route} from "react-router-dom";
import MovieAndSeriesDetailsPage from "./pages/MovieAndSeriesDetailsPage";

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
                    <MovieAndSeriesDetailsPage/>
                </Route>
        </Switch>
        </TypeProvider>
    </div>
  );
}

export default App;
