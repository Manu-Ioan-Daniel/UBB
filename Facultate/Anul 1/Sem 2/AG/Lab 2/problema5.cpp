//
// Created by Deny on 01-04-2025.
//

#include "problema5.h"
#include <fstream>
#include <vector>
#include <iostream>
#include <queue>
using namespace std;

void dfs(const vector<vector<int>> &graph, const int node,vector<bool>& visited) {
    visited[node]=true;
    for (int i = 0;i<graph.size();i++) {
        if (!visited[i] && graph[node][i]==1)
            dfs(graph,i,visited);
    }
}
void problema5() {
    int n,start;
    ifstream fin("problema5.txt");
    fin>>n;
    vector graph(n+1,vector<int>(n+1,0));
    int u,v;
    while (fin>>u>>v) {
        graph[u][v]=1;
        graph[v][u]=1;
    }
    cout<<"Introdu nodul sursa: ";
    cin>>start;
    vector visited(graph.size(), false);
    dfs(graph,start,visited);
    cout<<"Pornind de la nodul sursa "<<start<<" nodurile vizitate sunt:";
    for (int i = 0;i<visited.size();i++) {
        if (visited[i])
            cout<<i<<" ";
    }
    cout<<endl;

}