/*
 1. Fie un fisier ce contine un graf neorientat reprezentat sub forma: prima linie contine numarul nodurilor
    iar urmatoarele randuri muchiile grafului. Sa se scrie un program in C/C++ care sa citeasca fisierul
    si sa reprezinte/stocheze un graf folosind matricea de adiacenta, lista de adiacenta si matricea de incidenta.
    Sa se converteasca un graf dintr-o forma de reprezentare in alta.
Fisier -> matrice de adiacenta -> lista adiacenta -> matrice de incidenta -> lista adiacenta -> matrice de adiacenta -> lista.
 */
#include <iostream>
#include <fstream>
#include <limits>
constexpr int INF = std::numeric_limits<int>::max();
using namespace std;
void problema1();
void citire(int a[][101], int &n) {
    ifstream f("graph.txt");
    f >> n;
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
            a[i][j] = 0;

    int x, y;
    while (f >> x >> y) {
        a[x][y] = 1;
        a[y][x] = 1;
    }
    f.close();
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
    // zerorizare l[][]
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
        int j = 1, ok = 0;
        cout << "Varful " << i << " are vecinii:  ";
        while (l[i][j] != 0 && j <= n) {
            cout << l[i][j] << " ";
            j++;
            ok = 1;
        }
        if (ok == 0)
            cout << " -";
        cout << "\n";
    }
    cout << "\n";
}


void matrice_incidenta(int l[][101], int n, int m[][101], int &nr_muchii) {


    nr_muchii = 0;
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
            m[i][j] = 0;

    for (int i = 1; i <= n; i++) {
        int j = 1;
        while (l[i][j] != 0 && j <= n) {
            if (i < l[i][j]) {
                nr_muchii++;
                m[i][nr_muchii] = 1;
                m[l[i][j]][nr_muchii] = 1;
            }
            j++;
        }
    }
}

void lista_adiacenta_din_matrice_incidenta(int m[][101], int vf, int nr_muchii, int l[][101]) {


    for (int i = 1; i <= vf; i++)
        for (int j = 1; j <= vf; j++)
            l[i][j] = 0;


    for (int j = 1; j <= nr_muchii; j++) {
        int stg = -1, dr = -1;
        for (int i = 1; i <= vf; i++) {
            if (m[i][j] == 1) {
                if (stg == -1)
                    stg = i;
                else
                    dr = i;
            }
        }

        int c;
        c = 1;
        while (l[stg][c] != 0)
            c++;
        l[stg][c] = dr;

        c = 1;
        while (l[dr][c] != 0)
            c++;
        l[dr][c] = stg;
    }
}


void matrice_adiacenta_din_lista_adiacenta(int l[][101], int n, int a[][101]) {
    // zerorizare
    for (int i = 1; i <= n; i++)
        for (int j = 1; j <= n; j++)
            a[i][j] = 0;

    for (int i = 1; i <= n; i++) {
        int j = 1;
        while (l[i][j] != 0 && j <= n) {
            a[i][l[i][j]] = 1;
            j++;
        }
    }
}
void problema2();

int main() {

    problema1();
    problema2();
    return 0;
}
void problema1() {
    int a[101][101], n, l[101][101], m[101][101], nr_muchii;

    // Fisier -> matrice de adiacenta
    citire(a, n);
    cout << "Matricea de adiacenta este:\n";
    afisare_matrice(a, n, n);

    // matrice de adiacenta -> lista adiacenta
    lista_adiacenta(a, n, l);
    cout << "Lista de adiacenta:\n";
    afisare_lista_adiacenta(l, n);

    // lista adiacenta -> matrice de incidenta
    matrice_incidenta(l,n,m,nr_muchii);
    cout<<"Matricea de incidenta este:\n";
    afisare_matrice(m,n,nr_muchii);

    // matrice de incidenta -> lista adiacenta
    lista_adiacenta_din_matrice_incidenta(m,n,nr_muchii,l);
    cout<<"Lista de adiacenta:\n";
    afisare_lista_adiacenta(l,n);

    // lista adiacenta -> matrice de adiacenta
    matrice_adiacenta_din_lista_adiacenta(l,n,a);
    cout<<"Matricea de adiacenta este:\n";
    afisare_matrice(a,n,n);

    // matrice de adiacenta -> lista adiacenta
    lista_adiacenta(a, n, l);
    cout << "Lista de adiacenta:\n";
    afisare_lista_adiacenta(l, n);
}
void afisareNoduriIzolate(int mAdiacenta[][101],int n) {
    for (int i = 1;i<=n;i++) {
        bool izolat=true;
        for (int j = 1;j<=n;j++) {
            if (mAdiacenta[i][j] == 1)
                izolat=false;
        }
        if (izolat) {
            cout << "Nodul " << i << " este izolat\n";
        }else {
            cout<<"Nodul "<<i<<" nu este izolat\n";
        }
    }


}
bool grafRegulat(int mAdiacenta[][101],int n){
    int primulGrad=0,gradCurent = 0;
    for (int j = 1;j<=n;j++) {
        if (mAdiacenta[1][j]==1) {
            primulGrad++;
        }
    }
    for (int i = 2;i<=n;i++) {
        for (int j = 1;j<=n;j++) {
            if (mAdiacenta[i][j]==1) {
                gradCurent++;
            }
        }
        if (primulGrad!=gradCurent) {
            return false;
        }
    }
    return true;

}
void afisareMatriceaDistantelor(int mAdiacenta[][101],int n) {
    int mDistantelor[101][101];
    for (int i = 1;i<=n;i++) {
        for (int j = 1;j<=n;j++) {
            if (mAdiacenta[i][j]==1) {
                mDistantelor[i][j]=1;
            }else {
                mDistantelor[i][j]=INF;
            }
        }
    }
    for (int k = 1;k<=n;k++) {
        for (int i = 1;i<=n;i++) {
            for (int j = 1;j<=n;j++) {
                if (mDistantelor[i][j]>mDistantelor[i][k]+mDistantelor[k][j] && mDistantelor[i][k]!=INF && mDistantelor[k][j]!=INF) {
                    mDistantelor[i][j]=mDistantelor[i][k]+mDistantelor[k][j];
                }
            }
        }
    }
    cout<<"Matricea distantelor este:\n";
    for (int i = 1;i<=n;i++) {
        for (int j = 1;j<=n;j++) {
            if (mDistantelor[i][j]==INF) {
                cout<<"0 ";
            }else {
                cout<<mDistantelor[i][j]<<" ";
            }
        }
        cout<<"\n";
    }
}
void dfs(int nod,int mAdiacenta[][101],int n,bool vizitat[]) {
    vizitat[nod]=true;
    for (int i = 0;i<=n;i++) {
        if (mAdiacenta[i][nod]==1 && !vizitat[i]) {
            dfs(i,mAdiacenta,n,vizitat);
        }
    }
}
bool grafConex(int mAdiacenta[][101],int n) {
    bool vizitat[100];
    for (int i = 1;i<=n;i++) {
        vizitat[i]=false;
    }
    dfs(1,mAdiacenta,n,vizitat);
    for (int i = 1;i<=n;i++) {
        if (!vizitat[i]) {
            return false;
        }
    }
    return true;
}
void problema2() {
    int mAdiacenta[101][101],n;
    citire(mAdiacenta,n);
    afisareNoduriIzolate(mAdiacenta,n);
    if (grafRegulat(mAdiacenta,n)) {
        cout<<"Graful este regulat\n";
    }else {
        cout<<"Graful nu este regulat\n";
    }
    afisareMatriceaDistantelor(mAdiacenta,n);
    if (grafConex(mAdiacenta,n)) {
        cout<<"Graful este conex\n";

    }
    else {
        cout<<"Graful nu este conex\n";
    }

}
