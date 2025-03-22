
#include <iostream>
#include <fstream>

using namespace std;
void citire(int a[][101], int &n) {
    ifstream fin("date.txt");
    fin >> n;
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
            a[i][j] = 0;

    int x, y;
    while (fin >> x >> y) {
        a[x][y] = 1;
        a[y][x] = 1;
    }
    fin.close();
}

void afisare_matrice(int a[][101], int n, int m) {
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++)
            cout << a[i][j] << " ";
        cout << endl;
    }
    cout << endl;
}
void lista_adiacenta(int a[][101], int n, int l[][101]) {
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
            l[i][j] = 0;

    for (int i = 1; i <= n; i++) {
        int k = 1;
        for (int j = 1; j <= n; j++)
            if (a[i][j] != 0) {
                l[i][k] = j;
                k++;
            }
    }
}

void afisare_lista_adiacenta(int l[][101], int n) {
    for (int i = 1; i <= n; i++) {
        std::cout<<i<<": ";
        for (int j = 1; j <= n; j++)
            if (l[i][j] != 0)
                cout <<l[i][j]<< " ";
        cout<<endl;
    }


}
void afisare_noduri_izolate(int a[][101],int n) {
    for (int i = 1; i <= n; i++) {
        int ok=0;
        for (int j = 1; j <= n; j++)
            if (a[i][j] != 0) {
                ok=1;
                break;
            }
        if (ok==0) {
            std::cout<<"Nodul "<<i<<" este izolat\n";
        }
    }
}
bool graf_regulat(int a[][101], int n) {
    int grad_initial = 0;
    for (int j = 1; j <= n; j++) {
        if (a[1][j] != 0) {
            grad_initial++;
        }
    }
    for (int i = 2; i <= n; i++) {
        int grad_curent = 0;
        for (int j = 1; j <= n; j++) {
            if (a[i][j] != 0) {
                grad_curent++;
            }
        }
        if (grad_curent != grad_initial) {
            return false;
        }
    }
    return true;
}


int main() {

    int a[101][101], n, l[101][101], m[101][101], nr_muchii;
    citire(a, n);
    cout << "Matricea de adiacenta este:\n";
    afisare_matrice(a, n, n);
    lista_adiacenta(a, n, l);
    cout << "Lista de adiacenta:\n";
    afisare_lista_adiacenta(l, n);
    cout<<"Matricea de incidenta este:\n";
    afisare_matrice(a,n,n);
    cout<<"Lista de adiacenta:\n";
    afisare_lista_adiacenta(l,n);
    cout<<"Matricea de adiacenta este:\n";
    afisare_matrice(a,n,n);
    lista_adiacenta(a, n, l);
    cout << "Lista de adiacenta:\n";
    afisare_lista_adiacenta(l, n);
    afisare_noduri_izolate(a,n);
    if (graf_regulat(a, n)) {
        std::cout<<("Graful este regulat\n");

    }else {
        std::cout<<("Graful nu este regulat\n");
    }
    return 0;
}