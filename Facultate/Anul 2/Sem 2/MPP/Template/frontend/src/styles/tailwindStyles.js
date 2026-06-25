// tailwindStyles.js

/**
 * 1. FORMULARE & INPUT-URI (FORMS & INPUTS)
 */
export const formStyles = {
    container: "w-full max-w-xl mx-auto bg-white p-6 sm:p-8 rounded-xl shadow-sm border border-gray-200 space-y-5",
    group: "flex flex-col gap-1.5 w-full",
    label: "text-sm font-medium text-gray-700 select-none",
    input: "w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm bg-white text-gray-800 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-all text-sm",
    select: "w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm bg-white text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 text-sm h-[40px]",
    checkbox: "h-4 w-4 rounded border-gray-300 text-blue-600 focus:ring-blue-500 cursor-pointer",
    error: "text-xs text-red-500 font-medium mt-1"
};

/**
 * 2. DIV-URI GENERICE & LAYOUT (CONTAINERS & GRID)
 */
export const layoutStyles = {

    mainWrapper: "min-h-screen w-full bg-gray-50 text-gray-800 flex flex-col",
    centerContainer: "max-w-7xl w-full mx-auto px-4 sm:px-6 lg:px-8",
    grid2Cols: "grid grid-cols-1 sm:grid-cols-2 gap-4",
    grid3Cols: "grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6",
    absoluteCenter: "flex items-center justify-center min-h-[300px] w-full",
    formActions: "flex justify-end items-center gap-3 pt-4 border-t border-gray-100"
};

/**
 * 3. ELEMENTE INTERACTIVE & CARDURI (CARDS & INTERACTION)
 */
export const componentStyles = {

    cardNormal: "bg-white p-6 rounded-lg shadow-sm border border-gray-200",
    cardInteractive: "bg-white p-6 rounded-lg shadow-sm border border-gray-200 hover:shadow-md hover:border-blue-400 cursor-pointer transition-all",
    listItemRow: "flex items-center p-4 bg-gray-50 hover:bg-gray-100 border border-gray-200 rounded-lg transition-colors",
    badgeGreen: "inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-green-100 text-green-800",
    avatarCircle: "w-10 h-10 rounded-full bg-blue-100 text-blue-700 flex items-center justify-center font-semibold text-sm select-none"
};

/**
 * 4. MODALE & OVERLAYS (POP-UPS)
 */
export const modalStyles = {

    overlay: "fixed inset-0 bg-black/50 backdrop-blur-sm z-50 flex items-center justify-center p-4",
    content: "bg-white rounded-xl shadow-xl max-w-md w-full p-6 relative"

};