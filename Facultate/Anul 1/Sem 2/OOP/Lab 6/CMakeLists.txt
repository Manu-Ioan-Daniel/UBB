cmake_minimum_required(VERSION 3.28)
project(Lab_6)

set(CMAKE_CXX_STANDARD 20)

add_executable(Lab_6 main.cpp
        disciplina.cpp
        disciplina.h
        Repo.cpp
        Repo.h
        service.cpp
        service.h
        validator.cpp
        validator.h
        ui.cpp
        ui.h)
