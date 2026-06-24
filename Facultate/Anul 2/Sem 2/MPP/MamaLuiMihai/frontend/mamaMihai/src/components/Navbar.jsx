import { Link } from "react-router-dom";

export default function Navbar() {
    return (
        <nav style={{ display: "flex", gap: "15px", background: "#333", padding: "10px" }}>
            <Link to="/" style={{ color: "white", textDecoration: "none" }}>Login</Link>
            <Link to="/game" style={{ color: "white", textDecoration: "none" }}>Joc</Link>
        </nav>
    );
}