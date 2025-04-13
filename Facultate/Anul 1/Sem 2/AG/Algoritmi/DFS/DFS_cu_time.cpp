#define INF INT_MAX
#include <climits>
#include <iostream>
#include <vector>
//Ideea algoritmului:parcurgem graful in adancime,putem tine cont de parinti pentru a reconstrui drumul
//Tinem cont si de cate ori s a apelat DFS pentru a ajunge la nodul acela,ca un fel de nivel.
void DFS(const std::vector<std::vector<int>>& graph,const int start,std::vector<bool>& visited,std::vector<int>& parents,std::vector<int>& time_vector, int timp) {
    visited[start]=true;
    std::cout<<start<<" ";
    time_vector[start]=timp;
    for (int i = 0;i<graph[start].size();i++) {
        if (graph[start][i]==1 && !visited[i]) {
            parents[i]=start;
            DFS(graph,i,visited,parents,time_vector,timp+1);
        }
    }
}
