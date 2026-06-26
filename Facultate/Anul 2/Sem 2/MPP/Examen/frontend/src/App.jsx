import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import IntrareJocPage from "./features/IntrareJocPage.jsx";
import JocPage from "./features/JocPage.jsx";

function App() {

    return (
        <Router>
            <Routes>
                <Route path="/" element={<IntrareJocPage />} />
                <Route path="/game" element={<JocPage />} />
            </Routes>
        </Router>
    );
}

export default App;