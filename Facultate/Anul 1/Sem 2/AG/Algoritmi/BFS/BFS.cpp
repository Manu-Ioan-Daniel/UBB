
#define INF INT_MAX
#include <climits>
#include <vector>
#include <queue>
/*Ideea algoritmului:Parcurgem fiecare vecin al nodului curent si daca nu a fost vizitat, il adaugam in coada si il marcam ca vizitat
* apoi trecem la urmatorul nod din coada.Avem vector de parinti optional pentru a putea reconstrui drumul.
*
 */

void BFS(const std::vector<std::vector<int>>& graph, const int start) {
    std::queue<int> q;
    q.push(start);
    std::vector<bool> visited(graph.size(), false);
    std::vector<int> parents(graph.size(), -1);
    visited[start] = true;
    while (!q.empty()) {
        const int node=q.front();
        q.pop();
        for (int i = 0;i<graph[node].size();i++) {
            if (graph[node][i]==1 && !visited[i]) {
                visited[i]=true;
                q.push(i);
                parents[i]=node;
            }
        }
    }

}
int main() {
    std::vector<std::vector<int>> graph = {
        {0, 1, 0, 0, 1},
        {1, 0, 1, 1, 0},
        {0, 1, 0, 0, 1},
        {0, 1, 0, 0, 1},
        {1, 0, 1, 1, 0}
    };
    BFS(graph, 0);
}