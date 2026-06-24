import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./features/LoginPage.jsx";
import GamePage from "./features/GamePage.jsx";

export default function App() {

    return (
        <Router>
            <Routes>

                <Route path="/" element={<LoginPage/>} />
                <Route
                    path="/game"
                    element={<GamePage/>}
                />

            </Routes>
        </Router>
    );
}