import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Client } from '@stomp/stompjs';
import api from "../api/axios.js";
import Header from "../components/Header.jsx";
import Footer from "../components/Footer.jsx";
import Navbar from "../components/Navbar.jsx";

export default function JocPage() {
    const porecla = sessionStorage.getItem("porecla");
    const varsta = sessionStorage.getItem("varsta");
    const navigate = useNavigate();
    const [gameState, setGameState] = useState(null);
    const [raspunsuri, setRaspunsuri] = useState({});

    useEffect(() => {
        if (!porecla) {
            navigate("/");
        }
    }, [navigate, porecla]);

    useEffect(() => {
        if (!porecla) return;

        const stompClient = new Client({
            brokerURL: 'ws://localhost:8080/ws',
            onConnect: () => {
                stompClient.publish({
                    destination: '/app/join',
                    body: porecla
                });

                stompClient.subscribe('/api/state', (message) => {
                    const newState = JSON.parse(message.body);
                    setGameState(newState);
                });

                api.get('/game/state')
                    .then(response => {
                        setGameState(response.data);
                    })
                    .catch(err => console.error(err));
            }
        });

        stompClient.activate();
        return () => {
            stompClient.deactivate();
        };
    }, [porecla]);

    useEffect(() => {
        setRaspunsuri({});
    }, [gameState?.literaCurenta, gameState?.rundaCurenta]);

    const handleExit = () => {
        sessionStorage.clear();
        navigate("/");
    };

    const handleAlegeLitera = () => {
        api.post(`/game/choose-letter?porecla=${porecla}`)
            .catch(err => alert(err.response?.data || "Eroare"));
    };

    const handleInputChange = (numeCat, valoare) => {
        setRaspunsuri(prev => ({
            ...prev,
            [numeCat]: valoare
        }));
    };

    const handleTrimiteRaspunsuri = () => {
        api.post(`/game/submit-answers?porecla=${porecla}`, raspunsuri)
            .catch(err => alert(err.response?.data || "Eroare"));
    };

    if (!gameState) {
        return (
            <div className="min-h-screen flex flex-col bg-gray-50 text-gray-800">
                <Header title="Tari, Orase, Ape, Munti" subtitle="Incarcare..." />
                <Navbar />
                <div className="flex-grow flex items-center justify-center p-4">
                    Se incarca starea jocului...
                </div>
                <Footer footerNote="Joc realizat pentru Examenul MPP" />
            </div>
        );
    }

    const esteRandulMeu = gameState.jucatorCareAlege === porecla;
    const amTrimis = gameState.raspunsuriTrimise && gameState.raspunsuriTrimise[porecla] != null;

    return (
        <div className="min-h-screen flex flex-col bg-gray-50 text-gray-800">
            <Header title="Tari, Orase, Ape, Munti" subtitle={`Jucator conectat: ${porecla} (${varsta} ani)`} />
            <Navbar />

            <main className="max-w-md w-full mx-auto p-4 space-y-4 flex-grow">
                <div className="flex justify-end">
                    <button onClick={handleExit} className="bg-red-500 text-white px-3 py-1 rounded text-sm cursor-pointer">
                        Iesire
                    </button>
                </div>

                <div className="p-3 bg-gray-100 rounded border text-center">
                    <span className="font-semibold">{gameState.statusMesaj}</span>
                </div>

                {gameState.inceput && (
                    <div className="space-y-4">
                        {/* Categorii */}
                        <div className="border p-3 rounded bg-white">
                            <h4 className="font-bold mb-2">Categorii:</h4>
                            <ul className="list-disc pl-5 space-y-1">
                                {gameState.categoriiSelectate.map((cat, idx) => (
                                    <li key={cat.id || idx}>{cat.nume}</li>
                                ))}
                            </ul>
                        </div>

                        {/* Control Runda */}
                        <div className="border p-3 rounded bg-white text-center space-y-3">
                            {!gameState.literaCurenta ? (
                                <div>
                                    {esteRandulMeu ? (
                                        <div className="space-y-2">
                                            <p className="text-sm font-medium">Este randul tau sa alegi litera!</p>
                                            <button onClick={handleAlegeLitera} className="bg-blue-600 text-white px-4 py-2 rounded cursor-pointer">
                                                Alege Litera
                                            </button>
                                        </div>
                                    ) : (
                                        <p className="text-sm text-gray-600">
                                            Asteptam ca <strong>{gameState.jucatorCareAlege}</strong> sa aleaga litera...
                                        </p>
                                    )}

                                    {gameState.puncteRundaCurenta && Object.keys(gameState.puncteRundaCurenta).length > 0 && (
                                        <div className="border-t pt-2 mt-2 text-left">
                                            <span className="text-xs font-bold text-gray-500 block">Puncte runda anterioara:</span>
                                            <ul className="text-xs space-y-1 mt-1">
                                                {Object.entries(gameState.puncteRundaCurenta).map(([player, pct]) => (
                                                    <li key={player}>{player}: {pct} puncte</li>
                                                ))}
                                            </ul>
                                        </div>
                                    )}
                                </div>
                            ) : (
                                <div className="space-y-3">
                                    <div>
                                        <span className="text-sm text-gray-500 block">Litera generata (Runda {gameState.rundaCurenta}):</span>
                                        <span className="text-2xl font-bold text-blue-600">{gameState.literaCurenta}</span>
                                    </div>

                                    {amTrimis ? (
                                        <p className="text-sm text-gray-600 bg-gray-50 p-2 rounded">
                                            Raspunsuri trimise. Asteptam ceilalti jucatori...
                                        </p>
                                    ) : (
                                        <div className="space-y-3 text-left">
                                            <h5 className="font-bold text-sm">Completati raspunsurile:</h5>
                                            {gameState.categoriiSelectate.map((cat, idx) => (
                                                <div key={cat.id || idx} className="flex flex-col gap-1">
                                                    <label className="text-xs font-medium text-gray-700">{cat.nume}</label>
                                                    <input 
                                                        type="text"
                                                        placeholder={`Incepe cu ${gameState.literaCurenta}...`}
                                                        value={raspunsuri[cat.nume] || ""}
                                                        onChange={(e) => handleInputChange(cat.nume, e.target.value)}
                                                        className="border p-2 rounded text-sm w-full"
                                                    />
                                                </div>
                                            ))}
                                            <button 
                                                onClick={handleTrimiteRaspunsuri} 
                                                className="bg-green-600 text-white w-full py-2 rounded mt-2 font-medium cursor-pointer"
                                            >
                                                Trimite Raspunsuri
                                            </button>
                                        </div>
                                    )}
                                </div>
                            )}
                        </div>

                        {/* Punctaj total */}
                        <div className="border p-3 rounded bg-white">
                            <h4 className="font-bold mb-2">Punctaj total joc:</h4>
                            <ul className="text-sm space-y-1">
                                {Object.entries(gameState.punctaje).map(([player, pct]) => (
                                    <li key={player} className="flex justify-between border-b pb-1">
                                        <span>{player}</span>
                                        <span className="font-semibold">{pct} puncte</span>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    </div>
                )}
            </main>

            <Footer footerNote="Joc realizat pentru Examenul MPP" />
        </div>
    );
}
