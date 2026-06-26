import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Client } from '@stomp/stompjs';
import api from "../api/axios.js";
import Header from "../components/Header.jsx";
import Footer from "../components/Footer.jsx";
import Navbar from "../components/Navbar.jsx";
import { layoutStyles, modalStyles, componentStyles } from "../styles/tailwindStyles.js";
import Button from "../components/Button.jsx";

export default function GenericGamePage() {
    const loggedUsername = sessionStorage.getItem("username");
    const navigate = useNavigate();
    const [gameState, setGameState] = useState(null);
    const [lastRoll, setLastRoll] = useState(null);

    useEffect(() => {
        if (!loggedUsername) { navigate("/"); }
    }, [navigate, loggedUsername]);

    useEffect(() => {
        const stompClient = new Client({
            brokerURL: 'http://localhost:8080/ws',
            onConnect: () => {
                stompClient.publish({ destination: '/app/join', body: loggedUsername });
                stompClient.subscribe('/api/state', (message) => {
                    setGameState(JSON.parse(message.body));
                });
                api.get('/game/state')
                    .then(res => setGameState(res.data))
                    .catch(err => console.error(err));
            }
        });
        stompClient.activate();
        return () => stompClient.deactivate();
    }, [loggedUsername]);

    const handleAlege = (config) => {
        api.post('/config', { numbers: config.numbers, n: gameState.n })
            .catch(err => console.error(err));
    };

    const handleGenerate = () => {
        api.post('/generate', { username: loggedUsername })
            .then(res => setLastRoll(res.data.generated))
            .catch(err => alert(err.response?.data || "Eroare"));
    };

    if (!gameState) return null;

    const isMyTurn = gameState.players[gameState.playerTurn] === loggedUsername;

    return (
        <div className={layoutStyles.mainWrapper}>
            <Header title="Regele" subtitle="Manuga" />
            <Navbar />

            {/* Containerul central cu stilul tău de listă verticală */}
            <div className="grid grid-cols-1 gap-4 max-w-md mx-auto p-6 flex-grow">

                {/* 1. FAZA DE CONFIGURARE */}
                {gameState.state !== "started" && gameState.state !== "finished" && (
                    <>
                        <p className={modalStyles.content}>
                            {gameState.nrOfPlayers < gameState.n ? "Jucatori Insuficienti" : "Esti regele, se poate incepe"}
                        </p>
                        {!gameState.currentConfig && gameState.nrOfPlayers === gameState.n && gameState.configs.map((config, index) => (
                            <div key={index} className={componentStyles.cardInteractive}>
                                <div className="flex flex-col items-center gap-4">
                                    <span className="font-medium text-lg">{config.numbers}</span>
                                    {gameState.chosenOne === loggedUsername && (
                                        <Button text="Alege" onClick={() => handleAlege(config)} />
                                    )}
                                </div>
                            </div>
                        ))}
                    </>
                )}

                {/* 2. FAZA DE JOC ACTIVATĂ */}
                {gameState.state === "started" && (
                    <div className="space-y-6 w-full">
                        <div className={componentStyles.cardNormal + " text-center space-y-2"}>
                            <h2 className="text-xl font-bold">Joc în desfășurare</h2>
                            <p className="text-sm bg-blue-50 text-blue-700 py-1 px-3 rounded-full inline-block font-semibold">
                                Runda {gameState.currentRound} din {gameState.n}
                            </p>
                            <p className="text-xs text-gray-500">Configurația aleasă: {gameState.currentConfig?.numbers}</p>
                        </div>

                        {/* Notificare globală cu istoricul mutărilor */}
                        {gameState.lastActionMessage && (
                            <div className="bg-amber-50 border border-amber-200 text-amber-800 p-3 rounded-lg text-sm text-center font-medium">
                                {gameState.lastActionMessage}
                            </div>
                        )}

                        {/* Rândul curent */}
                        <div className="text-center">
                            {isMyTurn ? (
                                <Button text="Generează Număr" onClick={handleGenerate} />
                            ) : (
                                <p className="text-gray-600 font-medium">
                                    Așteaptă... este rândul lui: <span className="font-bold text-blue-600">{gameState.players[gameState.playerTurn]}</span>
                                </p>
                            )}
                        </div>

                        {/* Tabela de scoruri dinamică */}
                        <div className="space-y-2">
                            <h3 className="font-semibold text-gray-700">Tabelă Jucători:</h3>
                            {gameState.players.map((p) => (
                                <div key={p} className={componentStyles.listItemRow + " justify-between"}>
                                    <span className={p === loggedUsername ? "font-bold text-blue-600" : ""}>
                                        {p} {gameState.players[gameState.playerTurn] === p ? "🎲" : ""}
                                    </span>
                                    <div className="text-sm space-x-4">
                                        <span>Poziție: {gameState.positions[p] ?? 0}</span>
                                        <span className="font-semibold text-green-600">Puncte: {gameState.scores[p] ?? 0}</span>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                )}

                {/* 3. FAZA FINISHED */}
                {/* 3. FAZA FINISHED (CERINȚA 4 COMPLETE) */}
                {gameState.state === "finished" && (
                    <div className="space-y-6 w-full">
                        {/* Cardul cu Câștigătorul / Câștigătorii Jocului */}
                        <div className={componentStyles.cardNormal + " text-center bg-green-50 border-green-200 space-y-2"}>
                            <h2 className="text-2xl font-bold text-green-800">Jocul s-a terminat!</h2>
                            <div className="text-sm text-gray-700">
                <span className="font-semibold block text-base text-green-700">
                    🏆 Câștigător: {gameState.winners?.join(", ")}
                </span>
                                Scor maxim realizat: <span className="font-bold">{gameState.highestTotalScore} puncte</span>
                            </div>
                        </div>

                        {/* Listă detaliată pentru fiecare jucător și rundele lui cele mai bune */}
                        <div className="space-y-3">
                            <h3 className="font-semibold text-gray-700 text-sm">Statistici finale per jucător:</h3>
                            {gameState.players.map((p) => {
                                // Extragere runde maxime pentru jucătorul curent p
                                const pRounds = gameState.roundScores?.[p] || {};
                                let maxRoundPoints = -1;
                                let bestRounds = [];

                                Object.entries(pRounds).forEach(([roundNum, points]) => {
                                    if (points > maxRoundPoints) {
                                        maxRoundPoints = points;
                                        bestRounds = [roundNum];
                                    } else if (points === maxRoundPoints) {
                                        bestRounds.push(roundNum);
                                    }
                                });

                                return (
                                    <div key={p} className={componentStyles.cardNormal + " space-y-2 bg-white"}>
                                        <div className="flex justify-between items-center border-b pb-1">
                            <span className={`font-bold ${p === loggedUsername ? "text-blue-600" : "text-gray-800"}`}>
                                {p} {gameState.winners?.includes(p) ? "👑" : ""}
                            </span>
                                            <span className="font-semibold text-green-600 text-sm">Total: {gameState.scores[p] ?? 0} pct</span>
                                        </div>
                                        <p className="text-xs text-gray-600">
                                            <span className="font-medium text-gray-700">Rundele cele mai profitabile:</span>{" "}
                                            {bestRounds.length > 0
                                                ? `Runda ${bestRounds.join(", ")} (${maxRoundPoints} puncte obținute)`
                                                : "Nicio rundă cu puncte"}
                                        </p>
                                    </div>
                                );
                            })}
                        </div>
                    </div>
                )}
            </div>

            <Footer footerNote={"Logged in as " + loggedUsername} />
        </div>
    );
}