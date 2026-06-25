import React from 'react';

export default function InputField({ label, type = 'text', placeholder, value, onChange, error }) {
    return (
        <div className="flex flex-col gap-1 w-full max-w-sm">
            {label && <label className="text-sm font-medium text-gray-700">{label}</label>}
            <input
                type={type}
                placeholder={placeholder}
                value={value}
                onChange={onChange}
                className={`px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 w-full transition-all
          ${error
                    ? 'border-red-500 focus:ring-red-500 focus:border-red-500'
                    : 'border-gray-300 focus:ring-blue-500 focus:border-blue-500'
                }`}
            />
            {error && <span className="text-xs text-red-500">{error}</span>}
        </div>
    );
}