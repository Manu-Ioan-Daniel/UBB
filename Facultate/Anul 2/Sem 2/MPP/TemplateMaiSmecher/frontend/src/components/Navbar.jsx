import React from 'react';
import { Link } from 'react-router-dom';

export default function Navbar() {
    return (
        <nav className="bg-slate-900 text-white px-6 py-4 shadow-md">
            <div className="max-w-7xl mx-auto flex justify-between items-center">
                <div className="text-xl font-bold tracking-wide cursor-pointer text-blue-400">
                    ManugaHouse
                </div>
                <ul className="flex gap-6 text-sm font-medium">
                    <li>
                        <Link to="/" className="hover:text-blue-400 transition-colors">
                            Login
                        </Link>
                    </li>
                    <li>
                        <Link to="/game" className="hover:text-blue-400 transition-colors">
                            Game
                        </Link>
                    </li>
                </ul>
            </div>
        </nav>
    );
}