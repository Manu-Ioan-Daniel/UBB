#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

const int INF = 10000000;

ifstream f("input.txt");

// Trimite fluxul de la nodul u către nodul v, cât permite capacitatea reziduală și excesul nodului u
void pushFlow(vector<vector<int>>& capacity, vector<vector<int>>& flow, vector<int>& excessFlow, int u, int v) {
    int flowToPush = min(excessFlow[u], capacity[u][v] - flow[u][v]);
    flow[u][v] += flowToPush;
    flow[v][u] -= flowToPush;
    excessFlow[u] -= flowToPush;
    excessFlow[v] += flowToPush;
}

// Ridică înălțimea nodului u pentru a putea trimite flux mai departe
void relabelNode(int numVertices, vector<vector<int>>& capacity, vector<vector<int>>& flow, vector<int>& height, int u) {
    int minHeight = INF;
    for (int v = 0; v < numVertices; v++) {
        if (capacity[u][v] - flow[u][v] > 0) {  // dacă există capacitate reziduală pe muchie
            minHeight = min(minHeight, height[v]);
        }
    }
    if (minHeight < INF) {
        height[u] = minHeight + 1;
    }
}

// Eliberează excesul nodului u încercând să trimită flux sau să-l ridice în înălțime
void dischargeNode(int numVertices, vector<vector<int>>& capacity, vector<vector<int>>& flow, vector<int>& excessFlow, vector<int>& height, vector<int>& nextNeighbor, int u) {
    while (excessFlow[u] > 0) {
        if (nextNeighbor[u] < numVertices) {
            int v = nextNeighbor[u];
            if (capacity[u][v] - flow[u][v] > 0 && height[u] > height[v]) {
                pushFlow(capacity, flow, excessFlow, u, v);
            } else {
                nextNeighbor[u]++;
            }
        } else {
            relabelNode(numVertices, capacity, flow, height, u);
            nextNeighbor[u] = 0;
        }
    }
}

// Inițializează prefluxul de la sursă, saturând muchiile din sursă și setând înălțimea acesteia
void initializePreflow(int numVertices, vector<vector<int>>& capacity, vector<vector<int>>& flow,
                       vector<int>& excessFlow, vector<int>& height, int source) {
    height[source] = numVertices;
    excessFlow[source] = INF;

    for (int v = 0; v < numVertices; ++v) {
        if (capacity[source][v] > 0) {
            pushFlow(capacity, flow, excessFlow, source, v);
        }
    }
}

int pushRelabelMaxFlow(int numVertices, vector<vector<int>>& capacity, int source, int sink) {
    vector<vector<int>> flow(numVertices, vector<int>(numVertices, 0));
    vector<int> excessFlow(numVertices, 0);
    vector<int> height(numVertices, 0);
    vector<int> nextNeighbor(numVertices, 0);

    vector<int> activeVertices;
    for (int i = 0; i < numVertices; ++i) {
        if (i != source && i != sink)
            activeVertices.push_back(i);
    }

    initializePreflow(numVertices, capacity, flow, excessFlow, height, source);

    size_t currentIndex = 0;
    while (currentIndex < activeVertices.size()) {
        int u = activeVertices[currentIndex];
        int oldHeight = height[u];
        dischargeNode(numVertices, capacity, flow, excessFlow, height, nextNeighbor, u);

        if (height[u] > oldHeight) {
            // mută nodul u în fața listei pentru procesare imediată
            rotate(activeVertices.begin(), activeVertices.begin() + currentIndex, activeVertices.begin() + currentIndex + 1);
            currentIndex = 0;
        } else {
            ++currentIndex;
        }
    }

    int maxFlow = 0;
    for (int v = 0; v < numVertices; ++v)
        maxFlow += flow[source][v];

    return maxFlow;
}

int main() {
    int numVertices, numEdges;
    f >> numVertices >> numEdges;

    vector capacity(numVertices, vector<int>(numVertices, 0));

    for (int i = 0; i < numEdges; ++i) {
        int u, v, cap;
        f >> u >> v >> cap;
        capacity[u][v] = cap;
    }

    constexpr int source = 0;
    const int sink = numVertices - 1;

    cout << "Fluxul maxim: " << pushRelabelMaxFlow(numVertices, capacity, source, sink) << '\n';

    return 0;
}
