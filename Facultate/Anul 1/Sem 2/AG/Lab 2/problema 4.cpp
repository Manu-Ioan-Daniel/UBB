//
// Created by Deny on 01-04-2025.
//

#include "problema 4.h"
#include <fstream>
#include <iostream>
#include <vector>
#include <queue>
using namespace std;

void bfs(const vector<vector<int>>& graph,int start) {
    bool visited[graph.size()];
    vector <int> dist(graph.size(),-1);
    queue<int> q;
    for (int i = 0;i<graph.size();i++) {
        visited[i]=false;
    }
    visited[start]=true;
    q.push(start);
    dist[start]=0;
    while (!q.empty()) {
        const int node=q.front();
        q.pop();
        for (int i = 0;i<graph.size();i++) {
            if (!visited[i]&& graph[node][i]==1) {
                visited[i]=true;
                q.push(i);
                dist[i]=dist[node]+1;
            }
        }
    }
    for (int i = 0;i<graph.size();i++) {
        if (visited[i]) {
            cout<<"Nodul "<< i <<" a fost vizitat!";
        }
        cout<<"Distanta de la nodul "<<start<<" la nodul "<<i<<" este: "<<dist[i]<<endl;
    }
}



void problema4() {
    int n,start;
    ifstream fin("problema4.txt");
    fin>>n;
    vector<vector<int>>graph (n+1,vector<int>(n+1,0));
    int u,v;
    while (fin>>u>>v) {
        graph[u][v]=1;
        graph[v][u]=1;
    }
    cout<<"Introdu nodul sursa: ";
    cin>>start;
    bfs(graph,start);

}