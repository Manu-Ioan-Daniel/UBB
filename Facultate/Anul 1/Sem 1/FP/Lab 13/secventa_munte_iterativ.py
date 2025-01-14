def backtracking(numbers):
    x = [-1] * len(numbers)
    k = 0
    index=[0]*len(numbers)
    while k >= 0:
        if index[k]<len(numbers):
            x[k] = numbers[index[k]]
            index[k]+=1
            if is_consistent(x,k):
                if k == len(numbers) - 1:
                    if is_mountain(x):
                        print_solution(x)
                        x[k] = -1
                        k -= 1
                else:
                    k += 1
        else:
            x[k] = -1
            index[k]=0
            k -= 1



def is_consistent(x, k):

    if k < 2:
        return True
    return is_mountain(x[:k+1])

def is_mountain(numbers):
    """secventa este munte daca elementele cresc pana intr un punct si apoi scad"""
    i=0
    while i<len(numbers)-1 and numbers[i]<numbers[i+1]:
        i+=1
    while i<len(numbers)-1 and numbers[i]>numbers[i+1]:
        i+=1
    return i==len(numbers)-1

def print_solution(lista):
    for x in lista:
        print(x,end=" ")
    print()

def main():
    print("Introdu o lista de numere: ")
    numbers = [int(x) for x in input().split()]
    backtracking(numbers)

main()
