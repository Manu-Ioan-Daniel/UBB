#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

const int INF = 10000000;

ifstream f("input.txt");

// push flow from u to v as much as possible based on residual capacity and excess
void pump(vector<vector<int>>& C, vector<vector<int>>& F, vector<int>& e, int u, int v) {
    int flow = min(e[u], C[u][v] - F[u][v]);  // minimum between excess and available capacity
    F[u][v] += flow;                          // push forward
    F[v][u] -= flow;                          // update reverse flow
    e[u] -= flow;                             // reduce excess at u
    e[v] += flow;                             // increase excess at v
}

// relabel node u by increasing its height to allow further pushes
void heighten(int V, vector<vector<int>>& C, vector<vector<int>>& F, vector<int>& h, int u) {
    int h_min = INF;
    for (int v = 0; v < V; v++) {
        if (C[u][v] - F[u][v] > 0) {          // if there is residual capacity
            h_min = min(h_min, h[v]);         //find the minimum neighbor height
        }
    }
    if (h_min < INF)
        h[u] = h_min + 1;                     // increase u's height just above the lowest neighbor
}

// discharge all excess from u by pushing or relabeling
void unload(int V, vector<vector<int>>& C, vector<vector<int>>& F, vector<int>& e, vector<int>& h, vector<int>& seen, int u) {
    while (e[u] > 0) {
        if (seen[u] < V) {                    // iterate over neighbors
            int v = seen[u];
            if (C[u][v] - F[u][v] > 0 && h[u] > h[v]) {
                pump(C, F, e, u, v);          // push flow if height condition holds
            } else {
                seen[u]++;
            }
        } else {
            heighten(V, C, F, h, u);          // relabel if no push was possible
            seen[u] = 0;                      // reset neighbor pointer
        }
    }
}

// initial preflow: saturate edges from source and set height
void initialize_preflux(int V, vector<vector<int>>& C, vector<vector<int>>& F,
                        vector<int>& e, vector<int>& h, int source) {
    h[source] = V;                            // source height is total number of nodes
    e[source] = INF;                          // give source infinite excess

    for (int v = 0; v < V; ++v) {
        if (C[source][v] > 0) {
            pump(C, F, e, source, v);
        }
    }
}

int topological_pump(int V, vector<vector<int>>& C, int source, int sink) {
    vector<vector<int>> F(V, vector<int>(V, 0)); // flow matrix
    vector<int> e(V, 0), h(V, 0), seen(V, 0);     // excess, height, and neighbor
    vector<int> vertices;

    for (int i = 0; i < V; ++i)
        if (i != source && i != sink)
            vertices.push_back(i);

    initialize_preflux(V, C, F, e, h, source); // start preflow

    size_t k = 0;
    while (k < vertices.size()) {
        int u = vertices[k];
        int old_height = h[u];
        unload(V, C, F, e, h, seen, u);       // try to push/relabel node u

        if (h[u] > old_height) {
            // move u to the front
            rotate(vertices.begin(), vertices.begin() + k, vertices.begin() + k + 1);
            k = 0;
        } else {
            ++k;
        }
    }

    int max_flow = 0;
    // total flow is the sum of all flow going out of source
    for (int v = 0; v < V; ++v)
        max_flow += F[source][v];

    return max_flow;
}

int main() {
    int V, E;
    f >> V >> E;

    vector<vector<int>> C(V, vector<int>(V, 0));

    for (int i = 0; i < E; ++i) {
        int u, v, cap;
        f >> u >> v >> cap;
        C[u][v] = cap;
    }

    int source = 0, sink = V-1;

    cout << "Fluxul maxim: " << topological_pump(V, C, source, sink) << '\n';

    return 0;
}