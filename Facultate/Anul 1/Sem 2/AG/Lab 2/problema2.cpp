//
// Created by Deny on 01-04-2025.
//

#include "problema2.h"
#include <fstream>
#include <iostream>
#include <vector>
using namespace std;
void dfs(const vector<vector<int>> &graph, const int nod,vector<int> &viz) {
    viz[nod]=1;
    for (int i = 0;i<graph.size();i++) {
        if (graph[nod][i]==1 && viz[i]==0) {
            dfs(graph,i,viz);
        }
    }
}
void inchidereTranzitiva(vector<vector<int>> &graph) {
    int n=graph.size()-1;
    vector<int> viz(n+1,0);
    for (int i = 0;i<=n;i++) {
        dfs(graph,i,viz);
        for (int j = 0;j<=n;j++) {
            if (viz[j]==1) {
                graph[i][j]=1;
            }
        }
        viz=vector<int>(n+1,0);
    }
    for (int i = 0;i<=n;i++) {
        for (int j = 0;j<=n;j++) {
            cout<<graph[i][j]<<" ";
        }
        cout<<endl;
    }
}
void problema2() {

    ifstream fin("problema2.txt");
    int n;
    fin>>n;
    std::vector graph(n+1,std::vector(n+1,0));
    int u,v;
    while (fin>>u>>v) {
        graph[u][v]=1;
    }
    inchidereTranzitiva(graph);


}