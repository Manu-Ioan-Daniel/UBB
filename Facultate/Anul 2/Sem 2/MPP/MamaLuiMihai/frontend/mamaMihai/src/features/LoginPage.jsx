import {useState} from "react";
import Header from "../components/Header.jsx";
import Navbar from "../components/Navbar.jsx";
import Footer from "../components/Footer.jsx";
import { useNavigate } from "react-router-dom";
import api from "../api/axios.js";

export default function LoginPage() {
    const [porecla, setPorecla] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleConnect = (e) => {
        e.preventDefault();
        setError("");

        if (!porecla.trim()) {
            setError("Username is required");
            return;
        }

        api.post("/login", { porecla: porecla.trim() })
            .then(() => {
                sessionStorage.setItem("porecla", porecla.trim());
                setError("");
                navigate("/game");
            })
            .catch((err) => {
                console.error("Eroare la login:", err);
                if (err.response && err.response.data) {
                    setError(typeof err.response.data === "string"
                        ? err.response.data
                        : "Date de autentificare invalide");
                } else {
                    setError(err.message || "Nu s-a putut conecta la server");
                }
            });
    };

    console.log("Se conectează utilizatorul:", porecla);

    return (
        <div className="flex flex-col min-h-screen font-sans">
            <Header/>
            <Navbar/>

            <main className="flex-1 flex flex-col items-center pt-12">
                <div className="border border-gray-300 p-5 rounded w-70">
                    <h2 className="text-center mt-0 text-xl font-bold mb-4">Autentificare</h2>

                    <form onSubmit={handleConnect} className="flex flex-col gap-3">
                        <input
                            type="text"
                            placeholder="Introdu porecla..."
                            value={porecla}
                            onInput={(e) => setPorecla(e.target.value)}
                            className="p-2 text-base rounded border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                        <button type="submit" className="p-2.5 bg-blue-600 text-white border-none rounded cursor-pointer text-base font-medium hover:bg-blue-700 transition-colors">
                            Conectare
                        </button>
                        {error && <p className="text-red-500 m-0 text-sm">{error}</p>}
                    </form>
                </div>
            </main>

            <Footer/>
        </div>
    )
}