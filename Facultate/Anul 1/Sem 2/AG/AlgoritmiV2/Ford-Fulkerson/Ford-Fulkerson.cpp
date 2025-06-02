#include <vector>
#include <iostream>
#include <algorithm>
#include <fstream>
#include <string>
#include <queue>

using namespace std;
//Ford-Fulkerson algorithm
bool BFS(const vector<vector<int>>& Graph, const int s, const int t, vector<int>& parent) {
    const int V = Graph.size();
    vector<bool> visited(V, false);
    queue<int> q;
    q.push(s);
    visited[s] = true;

    while (!q.empty()) {
        const int u = q.front();
        q.pop();

        for (int v = 0; v < V; v++) {
            if (!visited[v] && Graph[u][v] > 0) {
                q.push(v);
                visited[v] = true;
                parent[v] = u;
            }
        }
    }
    return visited[t];
}

int Ford_Fulkerson(const vector<vector<int>>& G, const int start,const int t) {
    const int V = G.size();
    vector<vector<int>> rG = G;
    vector<vector<int>> f(V, vector<int>(V, 0)); // fluxul inițializat cu 0

    vector<int> parent(V);
    int maxFlow = 0;

    // cat timp exista un drum s->t in Gf
    while (BFS(rG, start, t, parent)) {
        int cf_p = INT_MAX; // capacitatea minima pe drum

        // aflam cf(p)
        for (int v = t; v != start; v = parent[v]) {
            const int u = parent[v];
            cf_p = std::min(cf_p, rG[u][v]);
        }

        // actualizare fluxuri
        for (int v = t; v != start; v = parent[v]) {
            const int u = parent[v];
            if (G[u][v] > 0) {
                f[u][v] += cf_p;
            } else {
                f[v][u] -= cf_p;
            }
            rG[u][v] -= cf_p;
            rG[v][u] += cf_p;
        }
        maxFlow += cf_p;
    }

    return maxFlow;
}

int main() {
    vector<vector<int>> G;
    ifstream fin("ford-fulkerson_input");
    int n, m;
    fin >> n >> m;
    G.resize(n, vector<int>(n, 0));
    for (int i = 0; i < m; i++) {
        int u, v, c;
        fin >> u >> v >> c;
        G[u][v] = c; // capacitatea de la u la v
    }
    fin.close();
    const int maxFlow = Ford_Fulkerson(G, 0, n-1);
    cout << "Max Flow: " << maxFlow << endl;
    return 0;
}
/*Cauți drum de la sursă la destinație cu BFS, în graful rezidual.

Găsești capacitatea minimă cf_p pe acel drum.

Actualizezi fluxul f[u][v] += cf_p.

Modifici rG — atât pe direcția directă (scazi), cât și pe inversă (crești).

Aduni cf_p la maxFlow.
 *
 *
 */
