#include <iostream>
#include <fstream>
#include <vector>
#include <climits>
#define INF INT_MAX
using std::vector;

bool BellmanFord(const vector<vector<std::pair<int,int>>>& graph,vector<int>& distances,const int source) {
    distances[source]=0;
    for (int k = 0;k<graph.size()-1;k++) {
        for (int u = 0;u<graph.size();u++) {
            for (auto const& [v,w]:graph[u]) {
                if (distances[u]!=INF && distances[v]>distances[u]+w) {
                    distances[v]=distances[u]+w;
                }
            }
        }
    }
    for (int u = 0;u<graph.size();u++) {
        for (auto const& [v,w]:graph[u]) {
            if (distances[u]!=INF && distances[v]>distances[u]+w) {
                return false;
            }
        }
    }
    return true;
}
void Dijkstra(const vector<vector<std::pair<int,int>>>& graph,vector<int>& distances,const int source) {
    vector visited(graph.size(),false);
    distances[source]=0;
    for (int u = 0;u<graph.size();u++) {
        int minIndex=-1,minDistance=INF;
        for (int v = 0;v<graph.size();v++) {
            if (!visited[v] && distances[v]<minDistance) {
                minDistance=distances[v];
                minIndex=v;
            }
        }
        if (minIndex==-1) {
            break;
        }
        visited[minIndex]=true;
        for (const auto& [v,w]:graph[minIndex]) {
            if (!visited[v] && distances[v]>distances[minIndex]+w) {
                distances[v]=distances[minIndex]+w;
            }
        }
    }

}
vector<vector<int>> Jonathan(vector<vector<std::pair<int,int>>>& graph) {
    const int V=graph.size();
    graph.emplace_back();
    for (int v =0;v<V;v++) {
        graph[V].emplace_back(v,0);
    }
    vector distances(V+1,INF);
    if (!BellmanFord(graph,distances,V)) {
        std::cout<<"-1";
        return {};
    }
    graph.pop_back();
    distances.pop_back();
    for (int u =0;u<V;u++) {
        for (auto& [v,w]:graph[u]) {
            w=w+distances[u]-distances[v];
        }
    }
    vector all_distances(V,vector(V,INF));
    for (int u = 0;u<V;u++) {
        vector distances2(V,INF);
        Dijkstra(graph,distances2,u);
        for (int v = 0;v<V;v++) {
            if (distances2[v]!=INF) {
                all_distances[u][v]=distances2[v]-distances[u]+distances[v];
            }
        }
    }
    return all_distances;
}
void Problema2() {
    std::ifstream fin("graph2.txt");
    int V,E,x,y,w;
    fin>>V>>E;
    vector<vector<std::pair<int,int>>> graph(V);
    while (fin>>x>>y>>w) {
        graph[x].emplace_back(y,w);
    }
   auto all_distances=Jonathan(graph);
   for (int u = 0;u<graph.size();u++) {
        for (const auto& [v,w]:graph[u]) {
            std::cout<<u<<" "<<v<<" "<<w<<std::endl;
        }
   }
   for (int i = 0;i<all_distances.size();i++) {
       for (int j = 0;j<all_distances.size();j++) {
           if (all_distances[i][j]!=INF)
               std::cout<<all_distances[i][j]<<" ";
           else {
               std::cout<<"INF ";
           }
       }
       std::cout<<std::endl;
   }
}
int main() {
    std::ifstream fin("graph.txt");
    int x,y,w,V,E,S;
    fin>>V>>E>>S;
    vector<vector<std::pair<int,int>>> graph(V);
    while (fin>>x>>y>>w) {
        graph[x].emplace_back(y,w);
    }
    vector distances(V,INF);
    if (!BellmanFord(graph,distances,S)) {
        std::cout<<"Avem ciclu negativ";
    }
    for (int i = 0;i<graph.size();i++) {
        if (distances[i]!=INF)
            std::cout<<"Distanta de la nodul "<<S<<" la nodul "<<i<<" este:"<<distances[i]<<std::endl;
        else {
            std::cout<<"Distanta de la nodul "<<S<<" la nodul "<<i<<" este:INF"<<std::endl;
        }
    }
    std::cout<<"SFARSIT PROBLEMA 1"<<std::endl<<std::endl<<std::endl;
    std::cout<<"Inceput problema 2:"<<std::endl;
    Problema2();

    return 0;
}