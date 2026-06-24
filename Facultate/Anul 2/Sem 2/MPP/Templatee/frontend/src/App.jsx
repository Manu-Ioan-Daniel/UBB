import { useState } from "react";
import Navbar from "./components/Navbar.jsx";
import Header from "./components/Header.jsx";
import Footer from "./components/Footer.jsx";
import LoginPage from "./features/LoginPage.jsx";
import GamePage from "./features/GamePage.jsx";


let globalSocket = null;

function App() {
    const [isConnected, setIsConnected] = useState(false);
    const [porecla, setPorecla] = useState("");
    const [gameStatus, setGameStatus] = useState("not_ready");
    const [loginError, setLoginError] = useState(null);

    const handleConnected = (socket, nume) => {
        setPorecla(nume);
        globalSocket = socket; // Salvăm instanța în variabila simplă din exterior

        globalSocket.onmessage = (e) => {

            const msg = JSON.parse(e.data);
            console.log("[Global WS] Mesaj primit:", msg);

            if (msg.type === "ERROR") {
                console.error("[Global WS] Eroare:", msg.payload);
                setLoginError(msg.payload);
                return;
            }
            if(msg.type === "SUCCESS"){
                setLoginError(null);
                setIsConnected(true);
            }
            if (msg.type === "GAME_STATUS") {
                setGameStatus(msg.payload);
            }
        };

    };

    return (
        <div className="min-h-screen flex flex-col bg-gray-950 text-gray-100">
            <Header />
            <Navbar />
            <main className="flex-1 container mx-auto px-4 py-8">
                {!isConnected
                    ? <LoginPage onConnected={handleConnected} loginError = {loginError} />
                    : <GamePage ws={globalSocket} porecla={porecla} gameStatus={gameStatus} />
                }
            </main>
            <Footer />
        </div>
    );
}

export default App;