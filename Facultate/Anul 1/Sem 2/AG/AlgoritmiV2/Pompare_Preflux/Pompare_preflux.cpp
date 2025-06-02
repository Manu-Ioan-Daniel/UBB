#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
#include <climits>
using namespace std;

struct Edge {
    int from, to, capacity, flow;
    Edge(int u, int v, int cap) : from(u), to(v), capacity(cap), flow(0) {}
};

class PreflowPush {
private:
    int n, s, t;
    vector<vector<int>> adj;           // Lista de adiacenta (indexi in vectorul de muchii)
    vector<Edge> edges;                // Lista de muchii
    vector<int> height, excess;

    void addEdgeInternal(int u, int v, int capacity) {
        edges.emplace_back(u, v, capacity);
        edges.emplace_back(v, u, 0); // muchie inversa cu capacitate 0
        int m = edges.size();
        adj[u].push_back(m - 2);
        adj[v].push_back(m - 1);
    }

    void push(int id) {
        Edge &e = edges[id];
        int u = e.from, v = e.to;
        int cf = e.capacity - e.flow;
        int delta = min(excess[u], cf);

        if (delta <= 0 || height[u] != height[v] + 1) return;

        e.flow += delta;
        edges[id ^ 1].flow -= delta; // muchia inversa
        excess[u] -= delta;
        excess[v] += delta;
    }

    void relabel(int u) {
        int minHeight = INT_MAX;
        for (int id : adj[u]) {
            Edge &e = edges[id];
            if (e.capacity - e.flow > 0)
                minHeight = min(minHeight, height[e.to]);
        }
        if (minHeight < INT_MAX)
            height[u] = minHeight + 1;
    }

    void discharge(int u) {
        while (excess[u] > 0) {
            bool pushed = false;
            for (int id : adj[u]) {
                int v = edges[id].to;
                if (edges[id].capacity - edges[id].flow > 0 && height[u] == height[v] + 1) {
                    push(id);
                    pushed = true;
                    if (excess[u] == 0) break;
                }
            }
            if (!pushed) {
                relabel(u);
            }
        }
    }

public:
    PreflowPush(int n, int s, int t) : n(n), s(s), t(t), adj(n), height(n), excess(n) {}

    void addEdge(int u, int v, int capacity) {
        addEdgeInternal(u, v, capacity);
    }

    int maxFlow() {
        height[s] = n;
        for (int id : adj[s]) {
            Edge &e = edges[id];
            e.flow = e.capacity;
            edges[id ^ 1].flow = -e.capacity;
            excess[e.to] += e.capacity;
            excess[s] -= e.capacity;
        }

        queue<int> q;
        for (int i = 0; i < n; ++i)
            if (i != s && i != t && excess[i] > 0)
                q.push(i);

        while (!q.empty()) {
            int u = q.front();
            q.pop();
            discharge(u);
            if (excess[u] > 0)
                q.push(u);
        }

        int flow = 0;
        for (int id : adj[s]) {
            flow += edges[id].flow;
        }
        return flow;
    }
};

int main() {
    const int n = 4;
    PreflowPush pf(n, 0, 3);

    pf.addEdge(0, 1, 7);
    pf.addEdge(0, 2, 5);
    pf.addEdge(1, 3, 7);
    pf.addEdge(2, 3, 5);

    cout << "Flux maxim: " << pf.maxFlow() << endl;  // Ar trebui să afișeze 12
    return 0;
}
