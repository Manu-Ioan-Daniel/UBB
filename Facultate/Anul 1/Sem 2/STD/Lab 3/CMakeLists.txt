cmake_minimum_required(VERSION 3.28)
project(Lab_3)

set(CMAKE_CXX_STANDARD 14)

include_directories("TAD Matrice/Proiect C++")
include_directories("TAD Matrice/Teste")

add_executable(Lab_3
    "TAD Matrice/Proiect C++/App.cpp"
    "TAD Matrice/Proiect C++/Matrice.cpp"
    "TAD Matrice/Proiect C++/Matrice.h"
    "TAD Matrice/Teste/TestExtins.cpp"
    "TAD Matrice/Teste/TestExtins.h"
    "TAD Matrice/Teste/TestScurt.cpp"
    "TAD Matrice/Teste/TestScurt.h"
        "TAD Matrice/Proiect C++/LinkedList.h")
