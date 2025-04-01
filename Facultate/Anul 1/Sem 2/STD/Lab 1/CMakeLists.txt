cmake_minimum_required(VERSION 3.28)
project(Lab_1)

set(CMAKE_CXX_STANDARD 20)

include_directories("Proiect C++")
include_directories(Teste)

add_executable(Lab_1


        "Proiect C++/App.cpp"
        "Proiect C++/Dictionar.cpp"
        "Proiect C++/Dictionar.h"
        "Proiect C++/IteratorDictionar.cpp"
        "Proiect C++/IteratorDictionar.h"
        Teste/TestExtins.cpp
        Teste/TestExtins.h
        Teste/TestScurt.cpp
        Teste/TestScurt.h)
