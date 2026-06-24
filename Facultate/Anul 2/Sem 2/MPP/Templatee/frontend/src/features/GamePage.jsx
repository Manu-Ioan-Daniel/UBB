
function GamePage({ws, porecla, gameStatus}) {

    return (
        <div>
            <p className = "text-center text-shadow-purple-900">{gameStatus === "ready" ? "Poti incepe meciul" : "Jucatori insuficienti"}</p>
        </div>
    )
}

export default GamePage;