//
// Created by Deny on 01-04-2025.
//
#include <vector>
#include <iostream>
#include <fstream>
#include "problema1.h"
#include <climits>
#include <queue>
using namespace std;
void Moore(const vector<vector<int>> &graph,int sursa) {
    int n=graph.size()-1;
    vector distante(n+1,INT_MAX);
    vector parent(n+1,-1);
    vector <int> lant;
    distante[sursa]=0;
    queue <int>q;
    q.push(sursa);
    while (!q.empty()) {
        const int x = q.front();
        q.pop();
        for (int i = 1;i<=n;i++) {
            if (graph[x][i]==1 && distante[i]==INT_MAX) {
                parent[i]=x;
                distante[i]=distante[x]+1;
                q.push(i);
            }
        }

    }
    for (int i = 1;i<=n;i++) {
        if (distante[i]==INT_MAX) {
            cout<<"Nodul "<<i<<" nu este accesibil din nodul "<<sursa;
        }
        else {
            cout<<"Distanta de la "<<sursa<<" la "<<i<<" este "<<distante[i]<<"; ";
            cout<<"Lantul este:";
            int nod=i;
            while (nod!=sursa) {
                lant.push_back(nod);
                nod=parent[nod];
            }
            lant.push_back(sursa);
            for (int j = lant.size()-1;j>=0;j--) {
                cout<<lant[j]<<" ";
            }
            lant.clear();
        }
        cout<<endl;


    }
}
void problema1() {
    ifstream fin("problema1.txt");
    int n,u,v;
    fin>>n;
    vector<vector<int>> graph(n+1,vector(n+1,0));
    while (fin>>u>>v)
        graph[u][v]=1;

    fin.close();
    int sursa;
    cout<<"Sursa: ";
    cin>>sursa;
    Moore(graph,sursa);

}