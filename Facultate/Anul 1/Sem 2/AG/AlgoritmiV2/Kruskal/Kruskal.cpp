#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>
using namespace std;

class DisjointSet {
    vector<int> parent, rank;
    public:
    explicit DisjointSet(const int n) {
        parent.resize(n);
        rank.resize(n, 0);
        for (int i = 0; i < n; i++)
            parent[i] = i;
    }

    int find_set(const int v) {
        if (v == parent[v])
            return v;
        return parent[v] = find_set(parent[v]);
    }

    void union_sets(int a, int b) {
        a = find_set(a);
        b = find_set(b);
        if (a != b) {
            if (rank[a] < rank[b])
                swap(a, b);
            parent[b] = a;
            if (rank[a] == rank[b])
                rank[a]++;
        }
    }
};
int main() {
    vector<tuple<int, int, int>> edges = {
        {10, 0, 1},
        {6, 0, 2},
        {5, 0, 3},
        {15, 1, 3},
        {4, 2, 3}
    };
    sort(edges.begin(), edges.end()); // sortăm după pondere
    DisjointSet d(5);
    vector<tuple<int, int, int>> arboreMinimAcoperire;

    for (auto [w, u, v] : edges) {
        if (d.find_set(u) != d.find_set(v)) {
            arboreMinimAcoperire.emplace_back(w, u, v);
            d.union_sets(u, v);
        }
    }

    cout << "Muchiile din arborele de acoperire minim:\n";
    for (auto [w, u, v] : arboreMinimAcoperire) {
        cout << u << " - " << v << " : " << w << '\n';
    }

    return 0;
}
/* Ce rezolvă Kruskal?
Problema:
Dat un graf neorientat, conex, și cu ponderi pe muchii, vrem să găsim Arborele de Acoperire Minimă (Minimum Spanning Tree - MST).
Adică: un subgraf aciclic ce leagă toate nodurile și are cost total minim.

⚙️ Ideea din spate
Kruskal e un algoritm greedy:

Ia cele mai ieftine muchii mai întâi.

Le adaugă în arbore, doar dacă NU formează un ciclu.

Repetă până când arborele are exact n - 1 muchii (unde n = nr. de noduri).

Pentru a detecta ciclurile eficient, folosește o structură Disjoint Set Union (DSU), cunoscută și ca Union-Find.

🔢 Pașii algoritmului
1. Sortează toate muchiile crescător după pondere.
Pentru că vrem să le adăugăm în ordinea costului cel mai mic.

2. Inițializează fiecare nod în propria mulțime (set).
Fiecare nod este singur în componenta lui la început.

3. Parcurge muchiile în ordine:
Pentru fiecare muchie (u, v):

Dacă find_set(u) != find_set(v) → adaug-o în MST și unește mulțimile lor.

Altfel, o ignori (ar forma un ciclu).

4. Când ai n - 1 muchii adăugate, te oprești.
🧮 Complexitate
Sortarea muchiilor: O(E log E)

Fiecare operație find_set și union_sets: aproape O(1) datorită path compression + union by rank

Total: O(E log E), unde E = nr. de muchii

🔧 Cum funcționează Disjoint Set Union (DSU)?
🏷️ make_set(v)
→ Inițializează nodul v ca reprezentant propriu.

🔍 find_set(v)
→ Găsește liderul (rădăcina) componentei din care face parte v. Cu path compression, îl conectăm direct la lider pentru a optimiza viitorul.

🔗 union_sets(a, b)
→ Unește cele două componente, alegând liderul cu rang mai mare. Dacă rangurile sunt egale, alegem unul și îi mărim rangul.

🧪 Exemplu pas cu pas
Graf:

makefile
Copy
Edit
Noduri: 0, 1, 2, 3

Muchii:
(0-1, 10)
(0-2, 6)
(0-3, 5)
(1-3, 15)
(2-3, 4)
1. Sortăm muchiile:
scss
Copy
Edit
(2-3, 4)
(0-3, 5)
(0-2, 6)
(0-1, 10)
(1-3, 15)
2. Inițial: fiecare nod în set propriu.
3. Parcurgem muchiile:
2-3: 4 → Adăugată. (diferite componente)

0-3: 5 → Adăugată. (0 și 3 în componente diferite)

0-2: 6 → Ignorată. (0 și 2 sunt deja conectate prin 3)

0-1: 10 → Adăugată. (1 e izolat)

1-3: 15 → Ignorată. (1 și 3 deja în aceeași componentă)
 *
 *
 *
 */