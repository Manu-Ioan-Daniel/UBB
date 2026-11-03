from math import gcd, isqrt


def putere_modulara(b, exponent, n):
    rezultat = 1
    b %= n
    while exponent > 0:
        if exponent % 2 == 1:
            rezultat = rezultat * b % n
        b = b * b % n
        exponent //= 2
    return rezultat


def este_compus(n):
    return n > 3 and any(n % d == 0 for d in range(2, isqrt(n) + 1))


def baze_pseudoprime_tari(n):
    s = 0
    t = n - 1
    while t % 2 == 0:
        s += 1
        t //= 2

    baze = []
    for b in range(1, n):
        if gcd(b, n) != 1:
            continue
        x = putere_modulara(b, t, n)
        if x == 1 or x == n - 1:
            baze.append(b)
            continue
        for _ in range(s - 1):
            x = x * x % n
            if x == n - 1:
                baze.append(b)
                break
    return baze


def main():
    try:
        n = int(input("n = "))
    except ValueError:
        print("Introdu un numar intreg.")
        return

    if n % 2 == 0 or not este_compus(n):
        print("n trebuie sa fie un numar impar compus.")
        return

    print("Bazele din intervalul [1, n - 1]:")
    print(*baze_pseudoprime_tari(n))
    print("Orice baza intreaga congruenta modulo n cu acestea este valida.")


if __name__ == "__main__":
    main()
