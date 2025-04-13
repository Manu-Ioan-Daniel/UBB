#define INF INT_MAX
#include <climits>
#include <iostream>
#include <vector>
//Ideea algoritmului:parcurgem graful in adancime,putem tine cont de parinti pentru a reconstrui drumul
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