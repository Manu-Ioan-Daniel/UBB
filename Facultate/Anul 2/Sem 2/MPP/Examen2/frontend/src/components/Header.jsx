import React from 'react';

export default function Header({ title, subtitle }) {
    return (
        <header className="bg-white border-b border-gray-200 py-8 px-6">
            <div className="max-w-7xl mx-auto">
                <h1 className="text-3xl font-bold text-gray-900">{title || 'Welcome Back'}</h1>
                {subtitle && <p className="mt-2 text-sm text-gray-600">{subtitle}</p>}
            </div>
        </header>
    );
}