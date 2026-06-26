import React from 'react';

export default function Footer({footerNote}) {
    return (
        <footer className="bg-gray-100 text-gray-600 border-t border-gray-200 py-6 px-6 mt-auto">
            <div className="max-w-7xl mx-auto flex flex-col sm:flex-row justify-between items-center gap-4 text-sm">
                <div>
                    &copy; {new Date().getFullYear()} {footerNote}
                </div>
                <div className="flex gap-4">
                    <a href="#" className="hover:underline">Terms</a>
                    <a href="#" className="hover:underline">Privacy</a>
                </div>
            </div>
        </footer>
    );
}