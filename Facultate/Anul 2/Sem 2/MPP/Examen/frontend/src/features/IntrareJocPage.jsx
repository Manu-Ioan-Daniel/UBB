import { useState } from "react";
import api from "../api/axios.js"
import { useNavigate } from "react-router-dom";
import { formStyles, layoutStyles } from "../styles/tailwindStyles.js";
import InputField from "../components/InputField.jsx";
import Button from "../components/Button.jsx";
import Header from "../components/Header.jsx";
import Footer from "../components/Footer.jsx";
import Navbar from "../components/Navbar.jsx";

export default function IntrareJocPage() {
    const [porecla, setPorecla] = useState("");
    const [varsta, setVarsta] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
        if (!porecla || !varsta) {
            setError("Va rugam sa introduceti porecla si varsta.");
            return;
        }

        const ageNum = parseInt(varsta, 10);
        if (isNaN(ageNum) || ageNum <= 0) {
            setError("Varsta trebuie sa fie un numar valid pozitiv.");
            return;
        }

        setError("");
        api.post("/login", {
            porecla: porecla,
            varsta: ageNum
        })
            .then((res) => {
                sessionStorage.setItem("porecla", porecla);
                sessionStorage.setItem("varsta", varsta);
                navigate("/game");
            })
            .catch((err) => {
                setError(err.response?.data || "Eroare la conectare.");
            });
    }

    return (
        <div className={layoutStyles.mainWrapper}>
            <Header title="Tari, Orase, Ape, Munti" subtitle="Intrare in joc" />
            <Navbar />
            <div className={layoutStyles.absoluteCenter}>
                <form onSubmit={handleLogin} className={formStyles.container}>
                    <InputField 
                        label="Porecla" 
                        type="text" 
                        placeholder="Porecla..."
                        value={porecla} 
                        onChange={(e) => setPorecla(e.target.value)} 
                        error={error}
                    />
                    
                    <InputField 
                        label="Varsta" 
                        type="number" 
                        placeholder="Varsta..."
                        value={varsta} 
                        onChange={(e) => setVarsta(e.target.value)} 
                    />

                    <div style={{ marginTop: "15px" }}>
                        <Button text="Intra in Joc" type="submit" variant="primary" />
                    </div>
                </form>
            </div>
            <Footer footerNote="Joc realizat pentru Examenul MPP" />
        </div>
    );
}
