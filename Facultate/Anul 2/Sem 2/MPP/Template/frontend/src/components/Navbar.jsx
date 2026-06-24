/**
 * Navbar — afișează statusul conexiunii și buton disconnect.
 *
 * Props:
 *   connected   → boolean — dacă e conectat la WebSocket
 *   porecla     → string  — porecla jucătorului curent
 *   onDisconnect → fn    — apelat la click pe butonul Deconectare
 */
function Navbar({ connected, porecla, onDisconnect }) {
  return (
    <nav className="bg-gray-900 border-b border-gray-800 px-6 py-3 flex items-center justify-between">

      {/* Linkuri navigație — adaugă ce ai nevoie */}
      <div className="flex gap-6 text-sm text-gray-400">
        <a href="#" className="hover:text-white transition-colors">Joc</a>
        <a href="#" className="hover:text-white transition-colors">Scoruri</a>
        {/* <a href="#">Altceva</a> */}
      </div>

      {/* Status conexiune + deconectare */}
      <div className="flex items-center gap-4">
        {connected ? (
          <>
            {/* Punct verde + porecla */}
            <span className="flex items-center gap-2 text-sm text-gray-300">
              <span className="w-2 h-2 rounded-full bg-green-500 inline-block" />
              {porecla}
            </span>
            <button
              onClick={onDisconnect}
              className="text-sm px-3 py-1 rounded bg-red-800 hover:bg-red-700 text-white transition-colors"
            >
              Deconectare
            </button>
          </>
        ) : (
          <span className="flex items-center gap-2 text-sm text-gray-500">
            <span className="w-2 h-2 rounded-full bg-gray-600 inline-block" />
            Neconectat
          </span>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
