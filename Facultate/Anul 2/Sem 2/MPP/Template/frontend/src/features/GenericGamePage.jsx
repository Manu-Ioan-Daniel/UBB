import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import { Client } from '@stomp/stompjs';
import api from "../api/axios.js"
import Header from "../components/Header.jsx";
import Footer from "../components/Footer.jsx";
import Navbar from "../components/Navbar.jsx";
import {layoutStyles, modalStyles, componentStyles} from "../styles/tailwindStyles.js";
import Button from "../components/Button.jsx";
export default function GenericGamePage(){

    const loggedUsername = sessionStorage.getItem("username");
    const navigate = useNavigate();
    const [gameState, setGameState] = useState(null);
    useEffect(() => {
        if(!loggedUsername){
            navigate("/")
        }
    }, [navigate, loggedUsername]);


    useEffect(() => {

        const stompClient = new Client({
            brokerURL: 'http://localhost:8080/ws',
            onConnect: () => {
                console.log('Connected to the ws');
                stompClient.publish({
                    destination: '/app/join',
                    body: sessionStorage.getItem("username")
                });
                stompClient.subscribe('/api/state', (message) => {
                    const newState = JSON.parse(message.body);
                    console.log("New state", newState);
                    setGameState(newState);
                });


                api.get('/game/state')
                    .then(response => {
                        setGameState(response.data);
                        console.log(response.data);
                    })
                    .catch(err => console.error("Error fetching state: ", err));
            },

        });

        stompClient.activate();
        return () => stompClient.deactivate();

    }, []);

    const handleAlege = (config) => {
        api.post('/config', config)
            .then(response => {
                console.log("Succes" + response.data.message);
            })
        .catch(err => console.error("Error fetching config: ", err));
    }

    return (<div className={layoutStyles.mainWrapper}>
        <Header title = "Regele" subtitle="Manuga"/>
        <Navbar />
        <div className={layoutStyles.absoluteCenter}>
            {gameState &&
                (
                    <p className = {modalStyles.content}>{gameState.nrOfPlayers < gameState.n ? "Jucatori Insuficienti" : "Esti regele, se poate incepe"}</p>
                )
            }
            {gameState && !gameState.chosenConfig && gameState.nrOfPlayers === gameState.n && gameState.configs.map((config, index) => (
                <ul className = {layoutStyles.absoluteCenter} key = {index}>
                    <li className = {componentStyles.listItemRow}>{config.numbers} {gameState.chosenOne === loggedUsername && (<Button text= "Alege" onClick={handleAlege(config)}></Button>)}</li>
                </ul>
            ))}

        </div>
        <Footer footerNote={"Logged in as" + loggedUsername} />
    </div>);
}
