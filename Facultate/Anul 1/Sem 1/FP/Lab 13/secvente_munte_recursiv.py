def backtracking(numbers,k,x):
    """Functia backtracking"""
    for i in range(0,len(numbers)):
        x[k]=numbers[i]
        if k==len(numbers)-1:
            if is_mountain(x):
                print_solution(x)
        else:
            backtracking(numbers,k+1,x)
def is_mountain(numbers):
    """sceventa este munte daca elementele cresc pana intr un punct si apoi scad"""
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
    numbers=[int(x) for x in input().split()]
    x=[0]*len(numbers)
    backtracking(numbers,0,x)
main()