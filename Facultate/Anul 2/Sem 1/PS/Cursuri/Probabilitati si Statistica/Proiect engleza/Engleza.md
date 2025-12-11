# Introduction
I’m Daniel, and I’ll start our presentation by taking you through the early evolution of programming languages—starting from binary code and assembly, and moving toward the first high-level languages like FORTRAN, COBOL, and ALGOL.”
# Evolution of Programming Languages – Study Notes

## Chapter 01: Binary Beginnings

**General Ideas**  
- Early programming was done **directly in binary** or by wiring computers like ENIAC(Electronic Numerical Integrator And Computer).  
- Programmers used **switches and plugboards**; each instruction was critical due to **limited memory and processing speed**.  
- The ENIAC operating manual (1946) details the use of function tables and master programmer units, foundational to direct programming.(a function table is a table where values of a function are stored,and a master programmer unit is a control module that manages execution of instructions for more complex operations)
- **Assembly language** introduced **symbolic mnemonics**, making code easier to read and maintain.

**Key Concepts**  
- `LDA` = Load Accumulator → loads a value from memory into the main register.  
- `STO` = Store → saves the value from the register back into memory.  
- **Accumulator:** a central register for temporary calculations.

**Example (Machine Code / Assembly)**  
*Machine Code:*  
```
0010 1010  ; Load value from address 10
0011 1100  ; Store result in address 12
```  
*Assembly:*  
```
LDA 10
STO 12
```

**Extra Notes**  
- Early computers had **no operating system**; programmers managed memory, I/O, and timing manually.  
- Assembly language **varied between computers**; no standardization.  
- Debugging was very difficult: **one wrong bit could crash the program**.  
- Programmers were like “hardware whisperers,” flipping switches to control the machine.

**Reference:**  
- Wilkes, M.V. (1951) – *The EDSAC Programming Manual*

---

## Chapter 02: High-Level Leap

**General Ideas**  
- **FORTRAN (1957):** allowed writing **mathematical formulas directly**, without low-level code.  
  - Introduced **subroutines**, **loops**, and **compiler optimizations**.  
  - Programs were **portable** across IBM 704 computers.  
- **COBOL (1959):** English-like syntax, focused on **business applications**, record and file processing, still used in banking.  
- **ALGOL 60 (1963):** academic language that introduced **nested blocks**, **recursive procedures**, and **BNF** for formal grammar definition.

**Examples**  
*FORTRAN:*  
```
C = A + B    ! Sum of two variables
```  

COBOL:
```cobol
IDENTIFICATION DIVISION.
PROGRAM-ID. CALCULATE-PAYROLL.
DATA DIVISION.
WORKING-STORAGE SECTION.
01 HOURS PIC 999.
01 RATE PIC 999.
PROCEDURE DIVISION.
MULTIPLY HOURS BY RATE GIVING PAY.
DISPLAY "PAY IS: " PAY.

```
This COBOL program calculates payroll. It first stores the number of hours worked and the hourly rate, multiplies them to get the total pay, and then displays the result. You can see how COBOL 
uses English-like sentences, making it readable even to people who aren’t programmers.
In COBOL, PIC defines the type and size of a variable, and 01 means it’s a top-level variable. Smaller numbers like 05 or 10 are subfields inside it.
*ALGOL 60:*  
```
begin
    integer a, b, c;
    a := 5;
    b := 3;
    c := a + b;
end
```

**Extra Notes**  
- FORTRAN made **scientific programming accessible**; COBOL made business programming readable.  
- ALGOL’s concepts influence modern languages (C, Pascal, JavaScript).  
- Early compilers were **slow**, compiling could take hours.  
- **Subroutines** allowed code reuse, saving time and reducing errors.  
- Programming paradigms emerging:  
  - **Procedural (FORTRAN)**  
  - **Business-oriented (COBOL)**  
  - **Structured / academic (ALGOL)**  
- Shift from **binary → assembly → high-level** = machine language → human-readable language.  
- Early programmers had to write **efficient code** due to hardware limits.

**References:**  
- Backus, J., et al. (1957) – *IBM 704 FORTRAN Manual*  
- Naur, P., et al. (1963) – *Report on the Algorithmic Language ALGOL 60*