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
    const [loginMessage, setLoginMessage] = useState(null);
    const [configs, setConfigs] = useState([]);
    const [poreclaAleasa, setPoreclaAleasa] = useState("");

    const handleConnected = (socket, nume) => {
        setPorecla(nume);
        globalSocket = socket;

        globalSocket.onclose = (e) => {

            console.warn("[Global WS] Conexiunea s-a închis:", e.code, e.reason);
            setIsConnected(false);

        };

        globalSocket.onmessage = (e) => {

            const msg = JSON.parse(e.data);
            console.log("[Global WS] Mesaj primit:", msg);

            if (msg.type === "ERROR") {
                console.error("[Global WS] Eroare:", msg.payload);
                setLoginMessage(msg.payload);
                return;
            }
            if(msg.type === "SUCCES"){
                setLoginMessage(null);
                setIsConnected(true);

            }
            if (msg.type === "STATUS") {
                setGameStatus(msg.payload);
            }

            if(msg.type === "CONFIG"){
                const msgPayload = JSON.parse(msg.payload);
                setConfigs(msgPayload.configurations);
                setPoreclaAleasa(msgPayload.porecla);
            }
        };

    };

    return (
        <div className="min-h-screen flex flex-col bg-gray-950 text-gray-100">
            <Header />
            <Navbar />
            <main className="flex-1 container mx-auto px-4 py-8">
                {!isConnected
                    ? <LoginPage onConnected={handleConnected} loginMessage = {loginMessage} />
                    : <GamePage ws={globalSocket} porecla={porecla} gameStatus={gameStatus} configs = {configs} poreclaAleasa = {poreclaAleasa} configuratieAleasa={configuratieAleasa} />
                }
            </main>
            <Footer />
        </div>
    );
}

export default App;