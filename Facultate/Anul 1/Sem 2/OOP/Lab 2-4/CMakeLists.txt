cmake_minimum_required(VERSION 3.28.3)
project(Lab_2_4 C)
set(CMAKE_C_STANDARD 11)
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Wextra")
add_executable(Lab_2_4 main.c domeniu/lista.c infrastructura/service.c ui/ui.c repository/repo.c validator/validator.c errors/errors.c)



