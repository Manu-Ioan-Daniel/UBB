#define INF INT_MAX
#include <climits>
#include <iostream>
#include <vector>
//Ideea algoritmului:aplicam DFS pe fiecare nod,daca nu a fost vizitat,incrementam numarul de componente conexe
void DFS(const std::vector<std::vector<int>>& graph,const int start,std::vector<bool>& visited,std::vector<int>& parents) {
    visited[start]=true;
    std::cout<<start<<" ";
    for (int i = 0;i<graph[start].size();i++) {
        if (graph[start][i]==1 && !visited[i]) {
            parents[i]=start;
            DFS(graph,i,visited,parents);
        }
    }
}
int nr_comp_conexe(const std::vector<std::vector<int>>& graph) {
    int componente=0;
    std::vector<bool> visited(graph.size(),false);
    std::vector<int> parents(graph.size(),-1);
    for (int i = 0;i<graph.size();i++) {
        if (!visited[i]) {
            componente+=1;
            DFS(graph,i,visited,parents);
        }
    }
    return componente;
}