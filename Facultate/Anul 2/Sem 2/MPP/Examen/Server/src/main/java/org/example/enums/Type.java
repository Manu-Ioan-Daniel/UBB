package org.example.enums;

public enum Type {
    // Browser → Server
    LOGIN,
    ACTION,      // acțiunea unui jucător (alegere, decizie, input)

    // Server → Browser (broadcast sau direct)
    UPDATE,      // stare nouă trimisă tuturor (ex: toți văd același lucru)
    RESPONSE,    // răspuns direct unui singur jucător
    ERROR,       // eroare de logică sau validare

    SUCCES, // Ambele sensuri
    STATUS, CONFIG, DISCONNECT   // deconectare curată
}
