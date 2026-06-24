import { useState } from "react";
import Navbar from "./components/Navbar.jsx";
import Header from "./components/Header.jsx";
import Footer from "./components/Footer.jsx";
import GamePage from "./features/GamePage.jsx";

/**
 * Root component.
 * Ține conexiunea WebSocket la nivel de App și o pasează jos ca prop.
 *
 * De ce aici și nu în GamePage?
 * → Dacă ai mai multe pagini care folosesc WS, nu reconectezi la fiecare navigate.
 * → Poți folosi și React Context în loc de props dacă ai multe componente.
 */
function App() {
  // ─── State conexiune ──────────────────────────────────────────────────────────
  const [ws, setWs]           = useState(null);   // instanța WebSocket
  const [connected, setConnected] = useState(false);
  const [porecla, setPorecla] = useState("");

  // ─── Conectare la server ──────────────────────────────────────────────────────

  const connect = (numeJucator) => {
    const socket = new WebSocket("ws://localhost:8080/ws");

    socket.onopen = () => {
      console.log("[WS] Conectat");
      setConnected(true);
      setPorecla(numeJucator);
      // Trimite HELLO imediat după conectare
      socket.send(JSON.stringify({ type: "HELLO", sender: numeJucator, payload: null }));
    };

    socket.onclose = () => {
      console.log("[WS] Deconectat");
      setConnected(false);
      setWs(null);
    };

    socket.onerror = (err) => {
      console.error("[WS] Eroare:", err);
    };

    // onmessage e setat în GamePage prin prop — fiecare pagină gestionează ce primește
    setWs(socket);
  };

  const disconnect = () => {
    if (ws) {
      ws.send(JSON.stringify({ type: "DISCONNECT", sender: porecla, payload: "bye" }));
      ws.close();
    }
  };

  // ─── Render ───────────────────────────────────────────────────────────────────

  return (
    <div className="min-h-screen flex flex-col bg-gray-950 text-gray-100">
      <Header />
      <Navbar connected={connected} porecla={porecla} onDisconnect={disconnect} />

      {/* Conținut principal — crește să umple spațiul rămas */}
      <main className="flex-1 container mx-auto px-4 py-8">
        <GamePage
          ws={ws}
          connected={connected}
          porecla={porecla}
          onConnect={connect}
        />
      </main>

      <Footer />
    </div>
  );
}

export default App;
