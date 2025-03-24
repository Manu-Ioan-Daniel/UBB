cmake_minimum_required(VERSION 3.28)
project(Lab5 C)

set(CMAKE_C_STANDARD 11)

add_executable(Lab5 main.c
        domain/expense.c
        domain/expense.h
        errors/error.c
        errors/error.h
        infrastructure/repo.c
        infrastructure/repo.h
        presentation/ui.c
        presentation/ui.h
        testing/tests.c
        testing/tests.h
        utils/list.c
        utils/list.h
        validation/validator.c
        validation/validator.h
        business/service.c
        business/service.h)
