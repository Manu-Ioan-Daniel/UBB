import { useEffect, useState } from "react";
import Header from "../components/Header.jsx";
import Navbar from "../components/Navbar.jsx";
import Footer from "../components/Footer.jsx";
import api from "../api/axios.js";

export default function GamePage() {

    const [gameState, setGameState] = useState({
        status: "Desconectat de la socket...",
        playerScores: {},
        nrOfPlayers: 0,
        n: 2,
        chosenOne: ""
    });

    const [configs, setConfigs] = useState([])

    const handleSendConfig = (config) => {
        api.post("/choose-config", {
            porecla: sessionStorage.getItem("porecla"),
            configId: config.id,
            numbers: config.numbers
        })
            .then((res) => {
                console.log("Configurația a fost trimisă cu succes:", res.data);
            })
            .catch((err) => {
                console.error("Eroare la trimiterea configurației:", err);
            });
    };
    useEffect(() => {

        const poreclaSalvata = sessionStorage.getItem("porecla");
        const socket = new WebSocket(`ws://localhost:8080/ws?porecla=${encodeURIComponent(poreclaSalvata)}`);

        socket.onopen = () => {
            console.log("Conectat la WebSocket!");
        };

        socket.onmessage = (event) => {
            try {
                const data = JSON.parse(JSON.parse(event.data).payload);
                console.log("GameState primit:", data);
                setGameState({
                    status: data.status,
                    playerScores: data.playerScores || {},
                    nrOfPlayers: data.nrOfPlayers,
                    n: data.n,
                    chosenOne: data.chosenOne,
                });
            } catch (err) {
                console.error("Eroare la parsarea datelor de la socket:", err);
            }
        };

        socket.onerror = (error) => {
            console.error("Eroare Socket:", error);
        };

        socket.onclose = () => {
            console.log("Conexiune WebSocket închisă.");
        };



        return () => {
            socket.close();
        };
    }, []);

    useEffect(() => {
        if (gameState.status === "ready") {

            api.get(`/config?n=${gameState.n}`)
                .then((res) => {
                    setConfigs(res.data.configs);
                })
                .catch((err) => {
                    console.error("Eroare la aducerea configurațiilor:", err);
                })

        }
    }, [gameState.status, gameState.n]);

    return (
        <div className="flex flex-col min-h-screen font-sans">
            <Header />
            <Navbar />

            <main className="flex-1 flex flex-col items-center pt-8 gap-5">
                <h2 className="text-2xl font-bold">Sala de Așteptare Joc</h2>

                <div className="border border-gray-300 padding p-4 rounded-lg w-80 text-center">
                    <h3 className="text-lg font-semibold">
                        Status:{" "}
                        <span className={gameState.status === "ready" ? "text-green-600" : "text-orange-500"}>
                            {gameState.status}
                        </span>
                    </h3>
                    <p className="mt-1">
                        Jucători conectați: <strong className="font-bold">{gameState.nrOfPlayers}</strong>
                    </p>
                </div>

                <div className="border border-gray-300 p-4 rounded-lg w-80">
                    <h3 className="text-lg font-semibold mt-0 text-center">Jucători și Scoruri</h3>
                    {Object.keys(gameState.playerScores).length === 0 ? (
                        <p className="text-center text-gray-500 mt-2">Niciun jucător momentan...</p>
                    ) : (
                        <ul className="list-none p-0 m-0 mt-2">
                            {Object.entries(gameState.playerScores).map(([porecla, scor]) => (
                                <li key={porecla} className="flex justify-between py-2 border-b border-gray-100 last:border-0">
                                    <span>👤 {porecla}</span>
                                    <span className="font-bold">{scor} Pct</span>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>

                {gameState.status === "ready" && (
                    <div className="border border-gray-300 p-4 rounded-lg w-80 text-center">
                        <h3 className="text-lg font-semibold mt-0">Configurații Disponibile</h3>
                        <div className="flex flex-col gap-2.5 text-left mt-2">
                            {configs.map((config, index) => (
                                <div key={config.id || index} className="p-2 border border-dashed border-gray-400 rounded">
                                    <strong className="font-bold">Config #{index + 1}:</strong> {config.numbers}
                                    {gameState.chosenOne === sessionStorage.getItem("porecla") && (
                                        <button className = "p-2 border-black border-2" onClick = handleSendConfig>Alege</button>
                                    )}
                                </div>
                            ))}
                        </div>
                    </div>
                )}

            </main>

            <Footer />
        </div>
    );
}