import re
import sys

keywords = [
    'struct', 'int', 'double', 'main', 'return', 'if', 'else', 'while',
    'std::cout', 'std::cin', 'std::endl', '#include', 'iostream'
]
operators = ['==', '!=', '<=', '>=', '<<', '>>', '=', '+', '-', '*', '/', '<', '>']
separators = [';', '.', '(', ')', '{', '}']

operators_found = []
identifiers = []
constants = []
keywords_found = []
separators_found = []


def add_symbol(table, value):
    if value not in table:
        table.append(value)
    return table.index(value)


def analyze(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    kw_sorted = sorted(keywords, key=len, reverse=True)
    kw_regex = '|'.join(re.escape(k) for k in kw_sorted)
    op_sorted = sorted(operators, key=len, reverse=True)
    op_regex = '|'.join(re.escape(op) for op in op_sorted)
    sep_regex = r'[;.\(\){}]'

    token_regex = re.compile(
        rf'({kw_regex})|({op_regex})|({sep_regex})|("(?:\\.|[^"\\])*")|(\b\d+\.\d+\b|\b\d+\b)|(\b[a-zA-Z][a-zA-Z0-9]*\b)'
    )

    for line in lines:
        pos = 0
        while pos < len(line):
            if line[pos].isspace():
                pos += 1
                continue

            match = token_regex.match(line, pos)
            if not match:
                pos += 1
                continue

            kw, op, sep, str_val, num, id_val = match.groups()

            if kw:
                add_symbol(keywords_found, kw)
            elif op:
                add_symbol(operators_found, op)
            elif sep:
                add_symbol(separators_found, sep)
            elif str_val:
                add_symbol(constants, str_val)
            elif num:
                add_symbol(constants, num)
            elif id_val:
                add_symbol(identifiers, id_val)

            pos = match.end()


def display():
    print("\n--- OPERATORI ---")
    for idx, item in enumerate(operators_found):
        print(f"Pozitie: {idx}, Operator: {item}")

    print("\n--- IDENTIFICATORI ---")
    for idx, item in enumerate(identifiers):
        print(f"Pozitie: {idx}, Identificator: {item}")

    print("\n--- CONSTANTE ---")
    for idx, item in enumerate(constants):
        print(f"Pozitie: {idx}, Constanta: {item}")

    print("\n--- CUVINTE CHEIE ---")
    for idx, item in enumerate(keywords_found):
        print(f"Pozitie: {idx}, Cuvant cheie: {item}")

    print("\n--- SEPARATORI ---")
    for idx, item in enumerate(separators_found):
        print(f"Pozitie: {idx}, Separator: {item}")


if __name__ == '__main__':
    file_name = sys.argv[1] if len(sys.argv) > 1 else '../Lab 1/coduri sursa/sursa_1.cpp'
    analyze(file_name)
    display()
