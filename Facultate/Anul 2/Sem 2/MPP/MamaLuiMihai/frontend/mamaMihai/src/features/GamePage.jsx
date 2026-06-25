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
        n: 2
    });

    const [configs, setConfigs] = useState([])

    useEffect(() => {

        const poreclaSalvata = localStorage.getItem("porecla") || "Anonym";
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
                    n: data.n
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
        <div style={{ display: "flex", flexDirection: "column", minHeight: "100vh", fontFamily: "sans-serif" }}>
            <Header />
            <Navbar />

            <main style={{ flex: 1, display: "flex", flexDirection: "column", alignItems: "center", paddingTop: "30px", gap: "20px" }}>
                <h2>Sala de Așteptare Joc</h2>


                <div style={{ border: "1px solid #ccc", padding: "15px", borderRadius: "8px", width: "320px", textAlign: "center" }}>
                    <h3>Status: <span style={{ color: gameState.status === "ready" ? "green" : "orange" }}>{gameState.status}</span></h3>
                    <p>Jucători conectați: <strong>{gameState.nrOfPlayers}</strong></p>
                </div>

                {/* Lista de Jucători prinși din Map-ul din Java */}
                <div style={{ border: "1px solid #ccc", padding: "15px", borderRadius: "8px", width: "320px" }}>
                    <h3 style={{ marginTop: 0, textAlign: "center" }}>Jucători și Scoruri</h3>
                    {Object.keys(gameState.playerScores).length === 0 ? (
                        <p style={{ textAlign: "center", color: "gray" }}>Niciun jucător momentan...</p>
                    ) : (
                        <ul style={{ listStyle: "none", padding: 0, margin: 0 }}>
                            {Object.entries(gameState.playerScores).map(([porecla, scor]) => (
                                <li key={porecla} style={{ display: "flex", justifyContent: "between", padding: "8px 0", borderBottom: "1px solid #eee" }}>
                                    <span>👤 {porecla}</span>
                                    <span style={{ marginLeft: "auto", fontWeight: "bold" }}>{scor} Pct</span>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>

                {gameState.status === "ready" && (
                    <div style={{ border: "1px solid #ccc", padding: "15px", borderRadius: "8px", width: "320px", textAlign: "center" }}>
                        <h3 style={{ marginTop: 0 }}>Configurații Disponibile</h3>
                        <div style={{ display: "flex", flexDirection: "column", gap: "10px", textAlign: "left" }}>
                            {configs.map((config, index) => (
                                <div key={config.id || index} style={{ padding: "8px", border: "1px dashed #aaa", borderRadius: "4px" }}>
                                    <strong>Config #{index + 1}:</strong> {config.numbers}
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