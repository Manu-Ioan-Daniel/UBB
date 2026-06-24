import {useState} from "react";
import Header from "../components/Header.jsx";
import Navbar from "../components/Navbar.jsx";
import Footer from "../components/Footer.jsx";
import { useNavigate } from "react-router-dom";
import axios from "../api/axios.js";

export default function LoginPage() {
    const [porecla, setPorecla] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();
    const handleConnect = (e) => {
        e.preventDefault();
        if (!porecla.trim()) {
            setError("Username is required");
            return;
        }
        axios.post("/login",{ porecla: porecla })
            .then(() =>{
                localStorage.setItem("porecla", porecla);
                navigate("/game");
            }).catch(err => {
                setError(err);
            })
        }
        console.log("Se conectează utilizatorul:", porecla);


    return (
        <div style={{ display: "flex", flexDirection: "column", minHeight: "100vh", fontFamily: "sans-serif" }}>
            <Header />
            <Navbar />

            <main style={{ flex: 1, display: "flex", flexDirection: "column", alignItems: "center", paddingTop: "50px" }}>
                <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "5px", width: "280px" }}>
                    <h2 style={{ textAlign: "center", marginTop: 0 }}>Autentificare</h2>

                    <form onSubmit={handleConnect} style={{ display: "flex", flexDirection: "column", gap: "12px" }}>
                        <input
                            type="text"
                            placeholder="Introdu porecla..."
                            value={porecla}
                            onChange={(e) => setPorecla(e.target.value)}
                            style={{ padding: "8px", fontSize: "16px", borderRadius: "4px", border: "1px solid #ccc" }}
                        />
                        <button type="submit" style={{ padding: "10px", background: "#007bff", color: "white", border: "none", borderRadius: "4px", cursor: "pointer", fontSize: "16px" }}>
                            Conectare
                        </button>
                        {error && <p style={{ color: "red", margin: 0 }}>{error}</p>}
                    </form>
                </div>
            </main>

            <Footer />
        </div>
    )
}