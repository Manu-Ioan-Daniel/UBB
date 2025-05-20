cmake_minimum_required(VERSION 3.30)
project(Lab_6)

set(CMAKE_CXX_STANDARD 20)

include_directories("TAD MultiDictionar/Proiect C++")
include_directories("TAD MultiDictionar/Teste")

add_executable(Lab_6
    "TAD MultiDictionar/Proiect C++/App.cpp"
    "TAD MultiDictionar/Proiect C++/IteratorMD.cpp"
    "TAD MultiDictionar/Proiect C++/IteratorMD.h"
    "TAD MultiDictionar/Proiect C++/MD.cpp"
    "TAD MultiDictionar/Proiect C++/MD.h"
    "TAD MultiDictionar/Teste/TestExtins.cpp"
    "TAD MultiDictionar/Teste/TestExtins.h"
    "TAD MultiDictionar/Teste/TestScurt.cpp"
    "TAD MultiDictionar/Teste/TestScurt.h")
