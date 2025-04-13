#include <iostream>
#include <vector>
#include <list>
#include <climits>
//aplicam dfs pe fiecare nod,la final adaugam nodul in vectorul de rezultate.
void DFS(const std::vector<std::vector<int>>& graph, const int vertex, std::vector<bool>& visited, std::vector<int>& result) {
    visited[vertex] = true;
    for (int i = 0; i < graph[vertex].size(); i++) {
        if (graph[vertex][i] == 1 && !visited[i]) {
            DFS(graph, i, visited, result);
        }
    }
    result.push_back(vertex);
}

void topologicalSort(const std::vector<std::vector<int>>& graph) {
    const int V = graph.size();
    std::vector<bool> visited(V, false);
    std::vector<int> result;
    
    for (int i = 0; i < V; i++) {
        if (!visited[i]) {
            DFS(graph, i, visited, result);
        }
    }
    
    std::cout << "Sortare topologica: ";
    for (int i= result.size() - 1; i >= 0; i--) {
        std::cout << result[i]<<" ";
    }
    std::cout << std::endl;
}

int main() {
    // Exemplu de graf (matrice de adiacență)
    const std::vector<std::vector<int>> graph = {
        {0, 1, 1, 0, 0, 0}, // A (0)
        {0, 0, 0, 1, 1, 0}, // B (1)
        {0, 0, 0, 0, 1, 0}, // C (2)
        {0, 0, 0, 0, 0, 1}, // D (3)
        {0, 0, 0, 1, 0, 0}, // E (4)
        {0, 0, 0, 0, 0, 0}  // F (5)
    };
    
    topologicalSort(graph);
    
    return 0;
}