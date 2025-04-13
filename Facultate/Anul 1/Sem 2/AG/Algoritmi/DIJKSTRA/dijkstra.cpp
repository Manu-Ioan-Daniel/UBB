#include <climits>
#include <iostream>
#include <vector>
#define INF INT_MAX
using std::vector;
//Ideea algoritmului:vizitam nodul cu distanta minima dintre el si nodul sursa i,dupa care updatam distanta
void dijkstra(const vector<vector<int>>& graph, const int start_node,vector<int>& distances,vector<bool>& visited) {
    distances[start_node]=0;
    for (int i = 0;i<graph.size();i++) {
        int min_distance=INT_MAX;
        int min_index=-1;
        for (int j = 0;j<graph.size();j++) {
            if (!visited[j] && distances[j]<min_distance) {
                min_distance=distances[j];
                min_index=j;
            }
        }
        visited[min_index]=true;
        for (int j = 0;j<graph.size();j++) {
            if (graph[min_index][j]!=INF && !visited[j]) {
                distances[j]=std::min(distances[j],distances[min_index]+graph[min_index][j]);
            }
        }
    }

}
//varianta cu lista de adiacenta
void Dijkstra(const vector<vector<std::pair<int, int>>>& graph, const int start_node, vector<int> &distances)
 {
    const int V = graph.size();
    vector<bool> visited(V, false);
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
int main() {
    const vector<vector<int> > graph = {
        {0, 4, 1, INF},
        {INF, 0, 2, 6},
        {INF, INF, 0, 5},
        {INF, INF, INF, 0}
    };

    constexpr int start = 0;
    vector<int> distances(graph.size(), INF);
    vector<bool> visited(graph.size(), false);

    dijkstra(graph, start, distances, visited);

    std::cout << "Distante minime de la nodul " << start << ":\n";
    for (size_t i = 0; i < distances.size(); i++) {
        if (distances[i] == INF)
            std::cout << "Nod " << i << ": INF\n";
        else
            std::cout << "Nod " << i << ": " << distances[i] << "\n";
    }

    return 0;
}