#include <iostream>
#include <vector>
#include <queue>
#include <climits>

using namespace std;

#define INF INT_MAX

// graph: lista de adiacenta, fiecare element e pair<nod vecin, cost>
// start: nodul de start
void primMST(const vector<vector<pair<int,int>>>& graph, int start) {
    const int n = graph.size();

    vector key(n, INF);      // costul minim pentru a conecta nodul la MST
    vector parent(n, -1);    // parintele in MST
    vector inMST(n, false); // daca nodul e deja in MST

    // priority_queue: pereche {key, nod}, ordonata crescator dupa key
    priority_queue<pair<int,int>, vector<pair<int,int>>, greater<>> pq;

    key[start] = 0;
    pq.emplace(0, start);

    while (!pq.empty()) {
        const int u = pq.top().second;
        pq.pop();

        if (inMST[u]) continue; // daca l-am procesat deja

        inMST[u] = true;

        for (auto const& [v, cost] : graph[u]) {
            if (!inMST[v] && cost < key[v]) {
                key[v] = cost;
                parent[v] = u;
                pq.emplace(key[v], v);
            }
        }
    }

    cout << "Muchiile din arborele de acoperire minim:\n";
    for (int i = 0; i < n; i++) {
        if (parent[i] != -1) {
            cout << parent[i] << " - " << i << " : " << key[i] << "\n";
        }
    }
}

int main() {
    const vector<vector<pair<int,int>>> graph = {
        {{1, 10}, {3, 5}},
        {{0, 10}, {2, 1}, {3, 2}},
        {{1, 1}, {3, 9}, {4, 4}},
        {{0, 5}, {1, 2}, {2, 9}, {4, 6}},
        {{2, 4}, {3, 6}}
    };
    primMST(graph, 0);
    return 0;
}

