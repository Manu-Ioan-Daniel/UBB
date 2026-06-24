/**
 * Navbar — afișează statusul conexiunii și buton disconnect.
 *
 * Props:
 *   connected   → boolean — dacă e conectat la WebSocket
 *   porecla     → string  — porecla jucătorului curent
 *   onDisconnect → fn    — apelat la click pe butonul Deconectare
 */
function Navbar() {
  return (
    <nav className="bg-gray-900 border-b border-gray-800 px-6 py-3 flex items-center justify-between">

      {/* Linkuri navigație — adaugă ce ai nevoie */}
      <div className="flex gap-6 text-sm text-gray-400">
        <a href="#" className="hover:text-white transition-colors">Joc</a>
        <a href="#" className="hover:text-white transition-colors">Scoruri</a>
        {/* <a href="#">Altceva</a> */}
      </div>


    </nav>
  );
}

export default Navbar;
