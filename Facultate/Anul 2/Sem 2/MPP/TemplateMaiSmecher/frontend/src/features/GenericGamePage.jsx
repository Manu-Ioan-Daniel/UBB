import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Client } from '@stomp/stompjs';
import api from "../api/axios.js";
import { layoutStyles, componentStyles } from "../styles/tailwindStyles.js";
import Footer from "../components/Footer.jsx";
import Navbar from "../components/Navbar.jsx";
import Header from "../components/Header.jsx";
import Card from "../components/Card.jsx";
import Button from "../components/Button.jsx";

export default function GenericGamePage() {
    const loggedUsername = sessionStorage.getItem("username");
    const navigate = useNavigate();
    const [gameState, setGameState] = useState(null);

    useEffect(() => {
        if (!loggedUsername) {
            navigate("/");
        }
    }, [navigate, loggedUsername]);

    useEffect(() => {
        const client = new Client({
            brokerURL: 'ws://localhost:8080/ws',
            onConnect: () => {
                console.log('Connected to the ws');

                client.publish({
                    destination: '/app/join',
                    body: sessionStorage.getItem("username")
                });

                client.subscribe('/api/state', (message) => {
                    const newState = JSON.parse(message.body);
                    console.log("State update received via WS:", newState);
                    setGameState(newState);
                });

                api.get('/game/state')
                    .then(response => {
                        setGameState(response.data);
                    })
                    .catch(err => console.error("Error fetching state via REST: ", err));
            },
        });

        client.activate();
        return () => client.deactivate();
    }, []);

    const genereazaLitera = () => {
        api.post('/game/generate')
            .then(response => {
                console.log("REST API: Litera a fost generata");
            })
            .catch(err => {
                console.error("REST API Error la generarea literei: ", err);
            });
    };

    const trimiteMutare = () => {
        api.post('/game/move')
            .then(response => {
                console.log("REST API: Mutarea a fost inregistrata");
            })
            .catch(err => {
                console.error("REST API Error la trimiterea mutarii: ", err);
            });
    };

    if (!gameState) {
        return (
            <div className={layoutStyles.mainWrapper}>
                <Navbar />
                <div className={layoutStyles.absoluteCenter}>
                    <p className="text-gray-500 animate-pulse">Se incarca starea jocului...</p>
                </div>
                <Footer footerNote="© 2026 Game Template Application" />
            </div>
        );
    }

    if (gameState.gameFinished) {
        return (
            <div className={layoutStyles.mainWrapper}>
                <Header title="Joc Terminat" subtitle="Toate rundele au fost completate!" />
                <Navbar />
                <main className={`${layoutStyles.centerContainer} flex-1 flex items-center justify-center py-8`}>
                    <Card title="Rezultate Meci" variant="centered">
                        <p className="text-xl font-bold text-gray-800 mb-2">Partida s-a incheiat cu succes!</p>
                        <p className="text-sm text-gray-500 mb-4">S-au jucat toate cele {gameState.n} runde.</p>
                        <Button text="Inapoi la Lobby" onClick={() => window.location.reload()} variant="primary" />
                    </Card>
                </main>
                <Footer footerNote="© 2026 Game Template Application" />
            </div>
        );
    }

    const minPlayersRequired = gameState.n;
    const eJucatoriInsuficienti = gameState.nrOfPlayers < minPlayersRequired;
    const esteChosenOne = gameState.chosenOne === loggedUsername;
    const esteRandulMeu = gameState.currentPlayer === loggedUsername;

    const jucatoriTitle = (
        <div className="flex justify-between items-center w-full">
            <span>Jucatori Conectati</span>
            <span className={componentStyles.badgeGreen}>
                {gameState.nrOfPlayers} activi
            </span>
        </div>
    );

    return (
        <div className={layoutStyles.mainWrapper}>
            <Header
                title={`Runda ${gameState.currentRound} / ${minPlayersRequired}`}
                subtitle={`Utilizator logat: ${loggedUsername}`}
            />
            <Navbar />

            <main className={`${layoutStyles.centerContainer} flex-1 py-8`}>
                <div className={layoutStyles.grid2Cols}>

                    <Card title={jucatoriTitle} variant="normal">
                        <div className="space-y-3 mt-1">
                            {gameState.loggedUsers && gameState.loggedUsers.map((user, index) => (
                                <div key={index} className={`${componentStyles.listItemRow} ${gameState.currentPlayer === user.name ? 'border-blue-500 bg-blue-50' : ''}`}>
                                    <div className="flex items-center gap-3">
                                        <span className="font-medium text-gray-700">
                                            {user.name} {user.name === loggedUsername && "(Tu)"}
                                        </span>
                                    </div>
                                    <div className="flex gap-2">
                                        {gameState.chosenOne === user.name && (
                                            <span className="text-xs bg-amber-100 text-amber-800 px-2 py-1 rounded font-semibold border border-amber-200">
                                                Decide
                                            </span>
                                        )}
                                        {gameState.currentPlayer === user.name && gameState.generatedLetter && (
                                            <span className="text-xs bg-blue-600 text-white px-2 py-1 rounded font-semibold animate-pulse">
                                                Randul Lui
                                            </span>
                                        )}
                                    </div>
                                </div>
                            ))}
                        </div>
                    </Card>

                    <Card title="Status Meci" variant="normal">
                        <div className="flex flex-col h-full justify-between min-h-[220px]">
                            <div>
                                {eJucatoriInsuficienti ? (
                                    <div className="p-4 bg-red-50 border border-red-200 text-red-700 rounded-lg text-sm font-medium">
                                        Jucatori insuficienti pentru a porni meciul (Minim {minPlayersRequired} necesari)
                                    </div>
                                ) : !gameState.generatedLetter && (
                                    <div className="p-4 bg-green-50 border border-green-200 text-green-700 rounded-lg text-sm font-medium">
                                        Se asteapta ca liderul ({gameState.chosenOne}) sa extraga litera
                                    </div>
                                )}

                                {gameState.generatedLetter && (
                                    <div className="mt-4 p-4 bg-blue-50 border border-blue-100 rounded-xl text-center">
                                        <span className="text-xs uppercase font-bold tracking-wider text-blue-500 block mb-1">
                                            Litera Extrasa
                                        </span>
                                        <strong className="text-4xl font-black text-blue-900">
                                            {gameState.generatedLetter}
                                        </strong>
                                    </div>
                                )}


                            </div>

                            <div className="pt-4 border-t border-gray-100">
                                {!gameState.generatedLetter ? (
                                    esteChosenOne ? (
                                        <div className="space-y-2">
                                            <Button
                                                text="Genereaza litera"
                                                onClick={eJucatoriInsuficienti ? null : genereazaLitera}
                                                variant={eJucatoriInsuficienti ? "secondary" : "primary"}
                                            />
                                        </div>
                                    ) : (
                                        <p className="text-xs text-gray-500 italic text-center">
                                            Asteapta ca {gameState.chosenOne} sa genereze litera.
                                        </p>
                                    )
                                ) : (
                                    <div>
                                        {esteRandulMeu ? (
                                            <div className="space-y-2">
                                                <Button
                                                    text="Finalizeaza Mutarea"
                                                    onClick={trimiteMutare}
                                                    variant="primary"
                                                />
                                            </div>
                                        ) : (
                                            <p className="text-xs text-gray-500 italic text-center">
                                                Asteapta ca {gameState.currentPlayer} sa isi termine randul.
                                            </p>
                                        )}
                                    </div>
                                )}
                            </div>
                        </div>
                    </Card>

                </div>
            </main>

            <Footer footerNote="© 2026 Game Template Application" />
        </div>
    );
}