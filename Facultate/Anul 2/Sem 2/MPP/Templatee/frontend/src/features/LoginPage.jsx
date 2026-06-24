import { useState } from "react";

function LoginPage({ onConnected, loginError }) {
    
    
    
    const [numeInput, setNumeInput] = useState("");

    const handleConnect = (e) => {
        e.preventDefault();

        const usernameCleaned = numeInput.trim().toLowerCase();



        // 1. Deschidem conexiunea nativă WebSocket
        const socket = new WebSocket("ws://localhost:8080/ws");

        // 2. Când conexiunea este pregătită, trimitem pachetul de LOGIN
        socket.onopen = () => {
            const loginMessage = {
                type: "LOGIN",
                sender: usernameCleaned,
                payload: null
            };

            socket.send(JSON.stringify(loginMessage));

            onConnected(socket, usernameCleaned);
        };
    };

    return (
        <div className="flex items-center justify-center h-full min-h-64">
            <div className="bg-gray-900 border border-gray-800 rounded-lg p-8 w-full max-w-sm">
                <h2 className="text-lg font-semibold mb-6 text-center">Intră în joc</h2>
                <form onSubmit={handleConnect} className="flex flex-col gap-4">
                    <input
                        type="text"
                        placeholder="Porecla ta"
                        value={numeInput}
                        onChange={(e) => setNumeInput(e.target.value)}
                        className="bg-gray-800 border border-gray-700 rounded px-4 py-2 text-white placeholder-gray-500 focus:outline-none focus:border-blue-500"
                    />
                    {loginError && <p className="text-red-400 text-sm">{loginError}</p>}
                    <button
                        type="submit"
                        className="bg-blue-600 hover:bg-blue-500 text-white py-2 rounded font-medium transition-colors"
                    >
                        Conectare
                    </button>
                </form>
            </div>
        </div>
    );
}

export default LoginPage;