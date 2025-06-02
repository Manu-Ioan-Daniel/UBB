cmake_minimum_required(VERSION 3.30)
project(AlgoritmiV2)

set(CMAKE_CXX_STANDARD 14)

add_executable(AlgoritmiV2
        Kruskal/Kruskal.cpp
        Ford-Fulkerson/Ford-Fulkerson.cpp
        PompareToplogica/pompare_topologica.cpp)
