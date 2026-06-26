import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import { Client } from '@stomp/stompjs';
import api from "../api/axios.js"
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
                    })
                    .catch(err => console.error("Error fetching state: ", err));
            },

        });

        stompClient.activate();
        return () => stompClient.deactivate();

    }, []);



    return (<></>);
}
