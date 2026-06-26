import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import GenericLoginPage from "./features/GenericLoginPage.jsx";
import GenericGamePage from "./features/GenericGamePage.jsx";

function App() {

    return (
        <Router>
            <Routes>
                <Route path="/" element={<GenericLoginPage />} />
                <Route path="/game" element={<GenericGamePage/>} />
            </Routes>
        </Router>
    );
}

export default App;