cmake_minimum_required(VERSION 3.30)
project(Lab_7)

set(CMAKE_CXX_STANDARD 14)

include_directories("TAD Colectie/Proiect C++/Colectie (cu rel)")
include_directories("TAD Colectie/Teste (cu relatie)")

add_executable(Lab_7
    "TAD Colectie/Proiect C++/Colectie (cu rel)/App.cpp"
    "TAD Colectie/Proiect C++/Colectie (cu rel)/Colectie.cpp"
    "TAD Colectie/Proiect C++/Colectie (cu rel)/Colectie.h"
    "TAD Colectie/Proiect C++/Colectie (cu rel)/IteratorColectie.cpp"
    "TAD Colectie/Proiect C++/Colectie (cu rel)/IteratorColectie.h"
    "TAD Colectie/Teste (cu relatie)/TestExtins.cpp"
    "TAD Colectie/Teste (cu relatie)/TestExtins.h"
    "TAD Colectie/Teste (cu relatie)/TestScurt.cpp"
    "TAD Colectie/Teste (cu relatie)/TestScurt.h")
