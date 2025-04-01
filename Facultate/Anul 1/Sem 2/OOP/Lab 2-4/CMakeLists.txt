cmake_minimum_required(VERSION 3.28)
project(Lab_2_4_rework C)
set(CMAKE_C_STANDARD 11)
add_executable(Lab_2_4_rework main.c
        lista.c
        lista.h
        oferta.c
        oferta.h
        repo.c
        repo.h
        errors.h
        service.c
        service.h
        validator.c
        validator.h
        ui.c
        ui.h
)
