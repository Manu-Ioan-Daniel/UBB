#define INF INT_MAX
#include <climits>
#include <iostream>
#include <vector>
#include <queue>
//Ideea algoritmului:BFS,tinem cont de distante si parinti,reconstruim drumul la final;
void Moore(std::vector<int>& distante,std::vector<int>& parents, const std::vector<std::vector<int>>& graph, const int start) {
    distante[start]=0;
    std::queue<int> q;
    q.push(start);
    while (!q.empty()) {
        const int node=q.front();
        q.pop();
        for (int i = 0;i<graph.size();i++) {
            if (graph[node][i]==1 && distante[i]==INF) {
                distante[i]=distante[node]+1;
                parents[i]=node;
                q.push(i);
            }
        }
    }
    for (int i = 0;i<graph.size();i++) {
        if (i==start || distante[i]==INF) {
            continue;
        }

        std::cout<<"Drumul de la "<<start<<" la "<<i<<" este: ";
        int node=parents[i];
        std::vector<int>path;
        path.push_back(i);
        while (node!=-1) {
            path.push_back(node);
            node=parents[node];
        }
        for (int j = path.size()-1;j>=0;j--) {
            std::cout<<path[j]<<" ";
        }
        std::cout<<std::endl;
    }
}
int main() {
    const std::vector<std::vector<int>> graph = {
        // 0  1  2  3  4
        { 0, 1, 1, 0, 0 },  // 0 -> 1, 0 -> 2
        { 0, 0, 0, 1, 0 },  // 1 -> 3
        { 0, 0, 0, 1, 1 },  // 2 -> 3, 2 -> 4
        { 0, 0, 0, 0, 1 },  // 3 -> 4
        { 0, 0, 0, 0, 0 }   // 4
    };
    std::vector distante(graph.size(), INF);
    std::vector parents(graph.size(), -1);
    Moore(distante, parents, graph, 0);

    return 0;
}