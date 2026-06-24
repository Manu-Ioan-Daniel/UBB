/**
 * Header — titlul aplicației, afișat sus permanent.
 * Adaptează titlul și subtitlul după problema curentă.
 */
function Header() {
  return (
    <header className="bg-gray-900 border-b border-gray-800 py-4 px-6">
      <h1 className="text-2xl font-bold text-white">
        Numele Jocului {/* ← schimbă asta */}
      </h1>
      <p className="text-sm text-gray-400">
        Subtitlu opțional — descriere scurtă {/* ← sau șterge */}
      </p>
    </header>
  );
}

export default Header;
