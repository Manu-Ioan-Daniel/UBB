import time

# complexitate logaritmica, cel mai rapid dintre toate
def gcd_euclidean(a, b):
    while b:
        a, b = b, a % b
    return a


def gcd_binary(a, b):
    if not a:
        return b
    if not b:
        return a

    shift = 0
    # cat timp a sau b sunt pare, le impartim la 2, ca sa lucram cu numere mai mici pe care facem eventual gcd
    while (a | b) & 1 == 0:
        a >>= 1
        b >>= 1
        shift += 1

    # cat timp a este par, il impartim la 2
    while a & 1 == 0:
        a >>= 1

    while b:
        # cat timp b este par, il impartim la 2
        while b & 1 == 0:
            b >>= 1
        # scadem din cel mai mare pe cel mai mic
        if a > b:
            a, b = b, a
        b -= a

    return a << shift  # pentru ca gcd(k*a, k*b) = k*gcd(a, b)


def get_prime_factors(n):
    factors = {}
    d = 2
    temp = n
    while d * d <= temp:
        while temp % d == 0:
            factors[d] = factors.get(d, 0) + 1
            temp //= d
        d += 1
    if temp > 1:
        factors[temp] = factors.get(temp, 0) + 1
    return factors


def gcd_prime_factors(a, b):
    if not a:
        return b
    if not b:
        return a

    factors_a = get_prime_factors(a)
    factors_b = get_prime_factors(b)

    # intersectam factorii primi si luam puterea minima
    res = 1
    for p in factors_a:
        if p in factors_b:
            res *= p ** min(factors_a[p], factors_b[p])
    return res


def fibbonaci(n):
    if n <= 0:
        return 0
    elif n == 1:
        return 1
    else:
        return fibbonaci(n - 1) + fibbonaci(n - 2)

inputs = [
    (48, 18),
    (610, 287),
    (123456789, 987654321),
    (13**7 + 9, 7**13 + 11),
    (fibbonaci(20), fibbonaci(21)),
    (fibbonaci(5), fibbonaci(5**2))
]

times_euclid = []
times_binary = []
times_prime = []

for a,b in inputs:
    t0 = time.perf_counter()
    res = gcd_euclidean(a, b)
    t_e = (time.perf_counter() - t0) * 1000
    times_euclid.append(t_e)

    t0 = time.perf_counter()
    gcd_binary(a, b)
    t_b = (time.perf_counter() - t0) * 1000
    times_binary.append(t_b)

    t0 = time.perf_counter()
    r_p = gcd_prime_factors(a, b)
    t_p = (time.perf_counter() - t0) * 1000
    times_prime.append(t_p)

    print(f"Pentru input (a={a}, b={b}):")
    print(f"  Euclid: {t_e:.4f} ms")
    print(f"  Binar: {t_b:.4f} ms")
    print(f"  Factori Primi: {t_p:.4f} ms")
    print(f"  CMMDC: {res}\n")

print("Medie:")
print(f"  Euclid: {sum(times_euclid) / len(times_euclid):.4f} ms")
print(f"  Binar: {sum(times_binary) / len(times_binary):.4f} ms")
print(f"  Factori Primi: {sum(times_prime) / len(times_prime):.4f} ms")