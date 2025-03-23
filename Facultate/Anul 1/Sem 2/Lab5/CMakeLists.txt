cmake_minimum_required(VERSION 3.28)
project(Lab5 C)

set(CMAKE_C_STANDARD 11)

add_executable(Lab5 main.c
        utils/list.h
        utils/list.c
        domain/expense.h
        domain/expense.c
        errors/error.h
        errors/error.c
        validation/validator.h
        validation/validator.c
        infrastructure/repo.h
        infrastructure/repo.c
        business/service.h
        business/service.c
        presentation/ui.c
        presentation/ui.h
        testing/tests.h
        testing/tests.c)
