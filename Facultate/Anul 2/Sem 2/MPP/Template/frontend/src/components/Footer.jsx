/**
 * Footer — fix jos datorită flex-col + flex-1 pe main din App.
 */
function Footer() {
  return (
    <footer className="bg-gray-900 border-t border-gray-800 py-3 px-6 text-center text-xs text-gray-600">
      © {new Date().getFullYear()} Numele Tău — Proiect Examen
    </footer>
  );
}

export default Footer;
