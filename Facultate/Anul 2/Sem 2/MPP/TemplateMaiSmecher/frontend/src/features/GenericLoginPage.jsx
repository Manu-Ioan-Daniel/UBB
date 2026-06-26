import { useState } from "react";
import { formStyles, layoutStyles } from "../styles/tailwindStyles.js";
import InputField from "../components/InputField.jsx";
import Header from "../components/Header.jsx";
import Footer from "../components/Footer.jsx";
import api from "../api/axios.js"
import {useNavigate} from "react-router-dom";
import Button from "../components/Button.jsx";
import Navbar from "../components/Navbar.jsx";

export default function GenericLoginPage() {
    const [username, setUsername] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
        if(!username){
            setError("Enter your username");
            return;
        }
        setError("");
        api.post("/login", {
            username : username
        })
            .then((res) => {
                sessionStorage.setItem("username", username);
                navigate("/game")
            })
            .catch((err) => {
                setError(err.message);
            })
    }
    return (
        <div className = {layoutStyles.mainWrapper}>
            <Header title = "Aplicatie" subtitle = "Examen MPP 2026"></Header>
            <Navbar></Navbar>
            <div className = {layoutStyles.absoluteCenter}>
                <form onSubmit={handleLogin} className={formStyles.container}>
                    <InputField label = "username" type="text" name="username" value={username} onChange={(e) => setUsername(e.target.value)} error = {error}/>
                    <Button text="Autentificare" type="submit" variant="primary" />
                </form>
            </div>
            <Footer footerNote = "Toate drepturile rezervate pentru Manu Ioan Daniel"></Footer>
        </div>
    );
}