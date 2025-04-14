#include <vector>
#include <climits>
#include <iostream>
#define INF INT_MAX
using std::vector;
using std::pair;
//Ideea algoritmului
//pentru matrice de adiacenta-foarte prost nu folosim(dar avem 3 for uri)
// pentru lista de adiacenta-foarte bine
//pentru fiecare margine (u,v) cu cost c, daca distante[v]>distante[u]+c, atunci distante[v]=distante[u]+c,acest lucru se executa de V-1 ori.unde V reprezinta nr de noduri
//NU uita de ciclu negativ
//folosim std::pair
bool Bellman_Ford(const vector<vector<pair<int,int>>>& graph,const int sursa,vector<int>& parents,vector<int>& distante) {
    const int V=graph.size();
    distante[sursa]=0;
    // for (int k = 0;k<graph.size()-1;k++) {
    //     for (int i=0;i<graph.size();i++)
    //         for (int j= 0;j<graph.size();j++) {
    //             if (graph[i][j]!=INF) {
    //                 if (distante[j]>distante[i]+graph[i][j]) {
    //                     distante[j]=distante[i]+graph[i][j];
    //                     parents[j]=i;
    //                 }
    //             }
    //         }
    // }
    // for (int i=0;i<graph.size();i++)
    //     for (int j = 0;j<graph.size();j++) {
    //         if (graph[i][j]!=INF) {
    //             if (distante[j]>distante[i]+graph[i][j]) {
    //                 return false;
    //             }
    //         }
    //     }
    for (int i = 0; i < V - 1; i++) {
        for (int u = 0; u < V; u++) {
            for (auto const& [v, cost] : graph[u]) {
                if (distante[u] != INF && distante[v] > distante[u] + cost) {
                    distante[v] = distante[u] + cost;
                    parents[v] = u;
                }
            }
        }
    }

    // Detectare ciclu negativ
    for (int u = 0; u < V; u++) {
        for (auto const& [v, cost] : graph[u]) {
            if (distante[u] != INF && distante[v] > distante[u] + cost) {
                return false;
            }
        }
    }

    return true;
}

int main() {
    // const vector<vector<int>> graph = {
    //     {0, 4, INF, 5},
    //     {INF, 0, -3, INF},
    //     {INF, INF, 0, 1},
    //     {INF, INF, INF, 0}
    // };
    vector<vector<std::pair<int, int>>> graph = {
        {{1, 4}, {3, 5}},
        {{2, -3}},
        {{3, 1}},
        {}
    };
    int sursa = 0;
    vector<int> distante(graph.size(),INF);

    if (vector<int> parents(graph.size(),-1); !Bellman_Ford(graph, sursa, parents, distante)) {
        std::cout << "Graful conține cicluri negative!\n";
    } else {
        std::cout << "Distante: ";
        for (int i = 0;i<distante.size();i++) {
            std::cout<<"Distanta de la sursa la nodul "<<i<<" "<<distante[i]<<std::endl;
        }
    }
    return 0;
}