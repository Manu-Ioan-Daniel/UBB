#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>
//Ideea algoritmului:aplicam DFS pe fiecare nod(dam push pe stack la final de dfs),transpunem graful,si cat timp stiva nu este goala lom
//nodul de pe varful stivei,daca nu este vizitat aplicam DFS_Transposed(la fel ca DFS doar ca fara push pe stack) pe graful transpus,
//incrementam numarul de componente conexe
void DFS(const std::vector<std::vector<int>>& graph, int node, std::vector<bool>& visited, std::stack<int>& stack) {
    visited[node] = true;
    for (int i = 0;i<graph.size();i++) {
        if (graph[node][i]==1 && !visited[i])
            DFS(graph,i,visited,stack);

    }
    stack.push(node);
}

void DFS_transposed(const std::vector<std::vector<int>>& transposed, const int node, std::vector<bool>& visited) {
    visited[node] = true;
    std::cout << node << " ";
    for (int i = 0;i<transposed.size();i++) {
        if (!visited[i] && transposed[node][i]==1) {
            DFS_transposed(transposed, i, visited);
        }
    }
}

int kosaraju(const std::vector<std::vector<int>>& graph) {
    std::vector visited(graph.size(), false);
    std::stack<int> stack;

    // Pasul 1: DFS pe graful original pentru ordonare
    for (int i = 0; i < graph.size(); ++i) {
        if (!visited[i]) {
            DFS(graph, i, visited, stack);
        }
    }

    // Pasul 2: Transpunerea grafului
    std::vector transposed(graph.size(), std::vector(graph.size(), 0));
    for (int i = 0; i < graph.size(); ++i) {
        for (int j = 0;j<transposed.size();++j) {
            if (graph[i][j]==1)
                transposed[j][i]=1;
        }
    }
    std::fill(visited.begin(), visited.end(), false);
    int scc_count = 0;
    while (!stack.empty()) {
        const int node = stack.top();
        stack.pop();
        if (!visited[node]) {
            std::cout << "Componenta tare conexa " << ++scc_count << ": ";
            DFS_transposed(transposed, node, visited);
            std::cout << std::endl;
        }
    }
    return scc_count;
}

int main() {
    const std::vector<std::vector<int>> graph = {
        {0,1,0,0,0},         // 0 → 1
        {0,0,1,0,0},         // 1 → 2
        {1,0,0,1,0},      // 2 → 0, 2 → 3
        {0,0,0,0,1},         // 3 → 4
        {0,0,0,0,0}           // 4 ↛
    };
    kosaraju(graph);
    return 0;
}
