from secrets import randbelow


def miller_rabin(n):
    if n < 2:
        return False
    if n == 2 or n == 3:
        return True
    if n % 2 == 0:
        return False

    s = 0
    t = n - 1
    while t % 2 == 0:
        s += 1
        t //= 2

    for _ in range(40):
        b = randbelow(n - 3) + 2
        x = pow(b, t, n)
        if x == 1 or x == n - 1:
            continue
        for _ in range(s - 1):
            x = x * x % n
            if x == n - 1:
                break
        else:
            return False
    return True


def main():
    try:
        n = int(input("n = "))
    except ValueError:
        print("Introdu un numar intreg.")
        return

    if miller_rabin(n):
        print("Numarul este probabil prim.")
    else:
        print("Numarul nu este prim.")


if __name__ == "__main__":
    main()
