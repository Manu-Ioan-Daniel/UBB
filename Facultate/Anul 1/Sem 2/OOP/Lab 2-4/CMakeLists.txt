cmake_minimum_required(VERSION 3.28.3)
project(Lab_2_4 C)
set(CMAKE_C_STANDARD 11)
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Wextra")
add_executable(Lab_2_4 main.c ../../untitled/domeniu/lista.c ../../untitled/infrastructura/service.c ../../untitled/ui/ui.c ../../untitled/repository/repo.c ../../untitled/validator/validator.c ../../untitled/errors/errors.c)



