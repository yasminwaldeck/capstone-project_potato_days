import './App.css';
import SearchPage from "./pages/SearchPage";
import Header from "./components/Header";
import TypeProvider from "./context/TypeProvider";

function App() {
  return (
    <div className="App">
        <TypeProvider>
            <Header/>
            <SearchPage/>
        </TypeProvider>
    </div>
  );
}

export default App;
