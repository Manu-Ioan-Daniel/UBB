import React from 'react';

export default function Card({ children, title, variant = 'normal' }) {

    const baseStyles = 'w-full bg-white p-6 rounded-lg shadow-sm border border-gray-200 transition-all';

    const variants = {

        normal: 'text-left',
        centered: 'text-center flex flex-col items-center justify-center gap-4',
        interactive: 'text-left hover:shadow-md hover:border-blue-400 cursor-pointer bg-gradient-to-br from-white to-slate-50'
    };

    return (
        <div className={`${baseStyles} ${variants[variant]}`}>
            {title && (
                <h3 className={`text-lg font-semibold text-gray-900 mb-3 ${variant === 'centered' ? 'w-full' : ''}`}>
                    {title}
                </h3>
            )}
            <div className="w-full text-gray-600 text-sm leading-relaxed">
                {children}
            </div>
        </div>
    );
}