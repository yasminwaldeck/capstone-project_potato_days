import './App.css';
import SearchPage from "./pages/SearchPage";
import Header from "./components/Header";
import {Switch, Route} from "react-router-dom";

function App() {
  return (
    <div className="App">
        <Header/>
        <Switch>
            <Route path={'/search'} exact>
                <SearchPage/>
            </Route>
            <Route path={'/movie/:id'} exact>
                <SearchPage/>
            </Route>

        </Switch>
    </div>
  );
}

export default App;
