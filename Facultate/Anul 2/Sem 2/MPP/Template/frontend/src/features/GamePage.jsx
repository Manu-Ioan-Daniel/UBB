import { useState, useEffect, useRef } from "react";

/**
 * Pagina principală a jocului.
 *
 * Props:
 *   ws          → instanța WebSocket (sau null dacă nu e conectat)
 *   connected   → boolean
 *   porecla     → string — porecla jucătorului curent
 *   onConnect   → fn(numeJucator) — apelat la submit formular conectare
 *
 * ══════════════════════════════════════════════════════════════
 *  ADAPTEZI PER PROBLEMĂ:
 *  - handleMessage() → ce faci cu fiecare tip de mesaj de la server
 *  - sendAction()    → ce trimiți la server pe diferite acțiuni
 *  - State-ul local  → adaugi câmpuri pentru starea jocului
 * ══════════════════════════════════════════════════════════════
 */
function GamePage({ ws, connected, porecla, onConnect }) {

  // ─── State local ──────────────────────────────────────────────────────────────
  const [numeInput, setNumeInput]   = useState("");      // input formular conectare
  const [mesaje, setMesaje]         = useState([]);      // log mesaje de la server
  const [actionInput, setActionInput] = useState("");    // input acțiune jucător
  // Adaugă state specific problemei:
  // const [configuratie, setConfiguratie] = useState(null);
  // const [numarCurent, setNumarCurent]   = useState(null);

  const mesajeEndRef = useRef(null); // auto-scroll la ultimul mesaj

  // ─── Atașează onmessage când ws se schimbă ────────────────────────────────────
  useEffect(() => {
    if (!ws) return;

    ws.onmessage = (event) => {
      const msg = JSON.parse(event.data);
      handleMessage(msg);
    };
  }, [ws]);

  // ─── Auto-scroll log mesaje ───────────────────────────────────────────────────
  useEffect(() => {
    mesajeEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [mesaje]);

  // ─── Handler mesaje de la server ─────────────────────────────────────────────

  /**
   * ══════════════════════════════════════════
   *  MODIFICĂ ASTA PER PROBLEMĂ:
   *  Adaugi case-uri pentru UPDATE-urile specifice
   * ══════════════════════════════════════════
   */
  const handleMessage = (msg) => {
    switch (msg.type) {

      case "UPDATE":
        // Serverul brodcast-ează ceva tuturor
        addLog(`[UPDATE] ${msg.payload}`);
        // Ex: parseezi payload și actualizezi state-ul jocului
        // const data = JSON.parse(msg.payload);
        // setConfiguratie(data.configuratie);
        break;

      case "RESPONSE":
        // Răspuns direct la acțiunea noastră
        addLog(`[SERVER] ${msg.payload}`);
        break;

      case "ERROR":
        addLog(`[EROARE] ${msg.payload}`);
        break;

      case "DISCONNECT":
        addLog("[SERVER] Conexiunea a fost închisă.");
        break;

      default:
        addLog(`[?] ${JSON.stringify(msg)}`);
    }
  };

  // ─── Trimitere acțiune ────────────────────────────────────────────────────────

  /**
   * ══════════════════════════════════════════
   *  MODIFICĂ ASTA PER PROBLEMĂ:
   *  Adaugi butoane specifice care apelează sendAction() cu payload-ul corect
   * ══════════════════════════════════════════
   */
  const sendAction = (payload) => {
    if (!ws || !connected) return;
    ws.send(JSON.stringify({ type: "ACTION", sender: porecla, payload }));
  };

  // ─── Helpers ──────────────────────────────────────────────────────────────────

  const addLog = (text) => {
    setMesaje(prev => [...prev, { text, time: new Date().toLocaleTimeString() }]);
  };

  const handleConnect = (e) => {
    e.preventDefault();
    if (numeInput.trim()) onConnect(numeInput.trim());
  };

  // ─── Render ───────────────────────────────────────────────────────────────────

  // ── Neconectat → formular de intrare ──
  if (!connected) {
    return (
      <div className="flex items-center justify-center h-full min-h-64">
        <div className="bg-gray-900 border border-gray-800 rounded-lg p-8 w-full max-w-sm">
          <h2 className="text-lg font-semibold mb-6 text-center">Intră în joc</h2>
          <form onSubmit={handleConnect} className="flex flex-col gap-4">
            <input
              type="text"
              placeholder="Porecla ta"
              value={numeInput}
              onChange={e => setNumeInput(e.target.value)}
              className="bg-gray-800 border border-gray-700 rounded px-4 py-2 text-white placeholder-gray-500 focus:outline-none focus:border-blue-500"
            />
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

  // ── Conectat → interfața jocului ──
  return (
    <div className="flex flex-col gap-6">

      {/* ── Zona de acțiuni — adaptează butoanele per problemă ── */}
      <div className="bg-gray-900 border border-gray-800 rounded-lg p-6">
        <h2 className="text-sm font-semibold text-gray-400 uppercase mb-4">Acțiuni</h2>

        <div className="flex gap-3">
          {/* Input + trimitere acțiune generică */}
          <input
            type="text"
            placeholder="Scrie o acțiune..."
            value={actionInput}
            onChange={e => setActionInput(e.target.value)}
            onKeyDown={e => {
              if (e.key === "Enter" && actionInput.trim()) {
                sendAction(actionInput.trim());
                setActionInput("");
              }
            }}
            className="flex-1 bg-gray-800 border border-gray-700 rounded px-4 py-2 text-white placeholder-gray-500 focus:outline-none focus:border-blue-500"
          />
          <button
            onClick={() => { sendAction(actionInput.trim()); setActionInput(""); }}
            className="bg-blue-600 hover:bg-blue-500 px-4 py-2 rounded text-white transition-colors"
          >
            Trimite
          </button>
        </div>

        {/* Butoane rapide — adaugă/modifică per problemă */}
        <div className="flex gap-2 mt-3">
          <button
            onClick={() => sendAction("actiune_1")}
            className="text-sm px-3 py-1 rounded bg-gray-700 hover:bg-gray-600 transition-colors"
          >
            Acțiune 1 {/* ← schimbă */}
          </button>
          <button
            onClick={() => sendAction("actiune_2")}
            className="text-sm px-3 py-1 rounded bg-gray-700 hover:bg-gray-600 transition-colors"
          >
            Acțiune 2 {/* ← schimbă */}
          </button>
        </div>
      </div>

      {/* ── Log mesaje de la server ── */}
      <div className="bg-gray-900 border border-gray-800 rounded-lg p-6">
        <h2 className="text-sm font-semibold text-gray-400 uppercase mb-4">Log</h2>
        <div className="h-64 overflow-y-auto flex flex-col gap-1">
          {mesaje.length === 0 && (
            <p className="text-gray-600 text-sm">Niciun mesaj încă...</p>
          )}
          {mesaje.map((m, i) => (
            <div key={i} className="text-sm font-mono">
              <span className="text-gray-600">[{m.time}] </span>
              <span className="text-gray-200">{m.text}</span>
            </div>
          ))}
          <div ref={mesajeEndRef} />
        </div>
      </div>

    </div>
  );
}

export default GamePage;
