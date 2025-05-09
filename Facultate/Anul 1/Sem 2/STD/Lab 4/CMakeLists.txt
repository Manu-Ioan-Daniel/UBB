cmake_minimum_required(VERSION 3.30)
project(Lab_4)

set(CMAKE_CXX_STANDARD 14)

include_directories("TAD Lista cu pozitie iterator/Proiect C++")
include_directories("TAD Lista cu pozitie iterator/Teste")

add_executable(Lab_4
    "TAD Lista cu pozitie iterator/Proiect C++/App.cpp"
    "TAD Lista cu pozitie iterator/Proiect C++/IteratorLP.cpp"
    "TAD Lista cu pozitie iterator/Proiect C++/IteratorLP.h"
    "TAD Lista cu pozitie iterator/Proiect C++/Lista.cpp"
    "TAD Lista cu pozitie iterator/Proiect C++/Lista.h"
    "TAD Lista cu pozitie iterator/Teste/TestExtins.cpp"
    "TAD Lista cu pozitie iterator/Teste/TestExtins.h"
    "TAD Lista cu pozitie iterator/Teste/TestScurt.cpp"
    "TAD Lista cu pozitie iterator/Teste/TestScurt.h")
