#include <vector>
#include <climits>
#include <iostream>
#include <algorithm>
#define INF INT_MAX

using std::vector;
using std::pair;
using std::cout;
using std::min;

// Bellman-Ford implementation (as provided)
bool Bellman_Ford(const vector<vector<pair<int,int>>>& graph, const int sursa, vector<int>& parents, vector<int>& distante) {
    const int V = graph.size();
    distante[sursa] = 0;
    
    for (int i = 0; i < V - 1; i++) {
        for (int u = 0; u < V; u++) {
            for (auto const& [v, cost] : graph[u]) {
                if (distante[u] != INF && distante[v] > distante[u] + cost) {
                    distante[v] = distante[u] + cost;
                    parents[v] = u;
                }
            }
        }
    }

    // Detect negative cycles
    for (int u = 0; u < V; u++) {
        for (auto const& [v, cost] : graph[u]) {
            if (distante[u] != INF && distante[v] > distante[u] + cost) {
                return false;
            }
        }
    }

    return true;
}

// Dijkstra's algorithm implementation (modified to work with adjacency list)
void Dijkstra(const vector<vector<pair<int, int>>>& graph, const int start_node, vector<int>& distances) {
    const int V = graph.size();
    vector visited(V, false);
    distances.assign(V, INF);
    distances[start_node] = 0;

    for (int i = 0; i < V; i++) {
        int min_distance = INF;
        int min_index = -1;

        // Find the unvisited node with the smallest distance
        for (int j = 0; j < V; j++) {
            if (!visited[j] && distances[j] < min_distance) {
                min_distance = distances[j];
                min_index = j;
            }
        }

        if (min_index == -1) break; // no more reachable nodes
        visited[min_index] = true;

        // Update distances for adjacent nodes
        for (const auto& [v, weight] : graph[min_index]) {
            if (!visited[v] && distances[min_index] != INF && 
                distances[v] > distances[min_index] + weight) {
                distances[v] = distances[min_index] + weight;
            }
        }
    }
}


vector<vector<int>> Johnson(const vector<vector<pair<int, int>>>& original_graph) {
    const int V = original_graph.size();
    
    // Step 1: Create modified graph  with new vertex s
    vector<vector<pair<int, int>>> modified_graph = original_graph;
    modified_graph.emplace_back(); // add new vertex with index V

    // Add edges from s to all other vertices with weight 0
    for (int v = 0; v < V; v++) {
        modified_graph[V].emplace_back(v, 0);
    }

    // Step 2: Run Bellman-Ford from s
    vector distances(V + 1, INF);

    if (vector parents(V + 1, -1); !Bellman_Ford(modified_graph, V, parents, distances)) {
        cout << "Graph contains negative weight cycle\n";
        return {};
    }

    // Remove the temporary vertex and its edges
    distances.pop_back(); // we only need h for original vertices
    modified_graph.pop_back();
    // Step 3: Reweight all edges

    for (int u = 0; u < V; u++) {
        for (auto& [v, w] : modified_graph[u]) {
            w= w + distances[u] - distances[v]; // reweighting
        }
    }

    // Step 4: Run Dijkstra for each vertex
    vector<vector<int>> all_pairs_distances(V, vector<int>(V, INF));
    
    for (int u = 0; u < V; u++) {
        vector<int> distances1;
        Dijkstra(modified_graph, u, distances1);

        // Step 5: Adjust distances back to original weights
        for (int v = 0; v < V; v++) {
            if (distances1[v] != INF) {
                all_pairs_distances[u][v] = distances1[v] - distances[u] + distances[v];
            }
        }
    }

    return all_pairs_distances;
}

int main() {
    // Example graph (same as your Bellman-Ford test case)
    const vector<vector<pair<int, int>>> graph = {
        {{1, 4}, {3, 5}},  // 0 -> 1 (4), 0 -> 3 (5)
        {{2, -3}},          // 1 -> 2 (-3)
        {{3, 1}},           // 2 -> 3 (1)
        {}                  // 3 has no outgoing edges
    };

    auto distances = Johnson(graph);

    if (distances.empty()) {
        return 1;
    }

    cout << "All-pairs shortest distances:\n";
    for (auto & distance : distances) {
        for (const int v : distance) {
            if (v == INF) {
                cout << "INF ";
            } else {
                cout << v << " ";
            }
        }
        cout << "\n";
    }

    return 0;
}