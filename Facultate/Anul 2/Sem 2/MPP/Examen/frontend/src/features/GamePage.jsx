function GamePage({ ws, porecla, gameStatus, poreclaAleasa, configs}) {

    const esteAlesul = porecla === poreclaAleasa;
    let configuratieAleasa = false;
    const selecteazaConfig = (config) => {

        if (!esteAlesul) return;

        ws.send(JSON.stringify({
            type: "CONFIG",
            sender: porecla,
            payload:config.numbers
        }));

        configuratieAleasa = config.numbers;
    };

    return (
        <div>
            <p>{gameStatus === "ready" ? "Poti incepe meciul" : "Jucatori insuficienti"}</p>

            {esteAlesul ? (
                <p>Este randul tau sa alegi!</p>
            ) : (
                poreclaAleasa && <p>Asteapta ca {poreclaAleasa} sa aleaga...</p>
            )}

            {(configs.length > 0) && (
                <h3>Configuratii disponibile:</h3>
            )
            }
            <ul>
                {configs && !configuratieAleasa && configs.map((config, index)  => (
                    <li key={index} style={{ margin: "10px 0" }}>
                        <span>Configurarea {index + 1}: {config.numbers} </span>
                        {esteAlesul && (
                            <button className = "border-2 border-white p-2 text-2xl" onClick={() => selecteazaConfig(config)}>
                                Alege
                            </button>
                        )}
                    </li>
                ))}
                {configuratieAleasa && (
                    <li>
                        <span>Configurarea aleasa: {configuratieAleasa}</span>
                    </li>
                )}
            </ul>
        </div>
    );
}

export default GamePage;