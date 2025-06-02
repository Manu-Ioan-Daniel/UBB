#include <iostream>
#include <vector>
#include <queue>
#include <climits>
using namespace std;
struct Edge {
    int from;
    int to;
    int weight;
};
#define INF INT_MAX
// graph as adjacency list of vectors of Edge
// start: starting node for Prim
void primMST(const vector<vector<Edge>>& graph, int start) {
    int n = graph.size();
    vector<int> key(n, INF);
    vector<int> parent(n, -1);
    vector<bool> inMST(n, false);

    // priority queue holds pairs {key, node} with the smallest key on top
    priority_queue<pair<int,int>, vector<pair<int,int>>, greater<>> pq;

    key[start] = 0;
    pq.emplace(0, start);

    while (!pq.empty()) {
        const int u = pq.top().second;
        pq.pop();

        if (inMST[u]) continue;
        inMST[u] = true;

        for (const Edge& e : graph[u]) {
            int v = e.to;
            int cost = e.weight;
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
    // Graph as adjacency list of Edges
    vector<vector<Edge>> graph = {
        { {0,1,10}, {0,3,5} },
        { {1,0,10}, {1,2,1}, {1,3,2} },
        { {2,1,1}, {2,3,9}, {2,4,4} },
        { {3,0,5}, {3,1,2}, {3,2,9}, {3,4,6} },
        { {4,2,4}, {4,3,6} }
    };

    primMST(graph, 0);
    return 0;
}
