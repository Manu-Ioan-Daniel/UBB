#include <vector>
#include <algorithm>
#include <iostream>
using namespace std;

class DisjointSet {
    vector<int>parents,ranks;
public:
    explicit DisjointSet(const int& n) {
        parents.resize(n,0);
        ranks.resize(n,0);
        for (int i = 0;i<n;i++) {
            parents[i]=i;
        }
    }
    int find_set(const int& v) {
        if (v==parents[v]) {
            return v;
        }
        return parents[v]=find_set(parents[v]);
    }
    void union_set(int a,int b) {
        a=find_set(a);
        b=find_set(b);
        if (a!=b) {
            if (ranks[a]<ranks[b]) {
                swap(a,b);
            }
            parents[b]=a;
            if (ranks[a]==ranks[b]) {
                ranks[a]++;
            }
        }
    }
};
vector<tuple<int,int,int>> Kruskal(const vector<tuple<int,int,int>>&graph,const int& n) {
    DisjointSet d(n);
    vector<tuple<int,int,int>>arboreMinAcoperire;
    for (auto[u,v,w]:graph){
        if (d.find_set(u)!=d.find_set(v)) {
            arboreMinAcoperire.emplace_back(u,v,w);
            d.union_set(u,v);
        }
    }
    return arboreMinAcoperire;
}
int main() {
    int v=5;
    constexpr int e=8;
    vector<tuple<int,int,int>> graph={
        {0,2,6},{0,3,7},{0,4,9},{1,3,4},{1,4,5},{2,3,9},{2,4,1},{3,4,6}
    };
    sort(graph.begin(),graph.end(),[](const tuple<int,int,int> &t1, const tuple<int,int,int> &t2) {
       return get<2>(t1)<get<2>(t2);
    });
    auto arboreMin=Kruskal(graph,e);
    int costTotal=0,n=arboreMin.size();
    for (const auto& [u,v,w]:arboreMin) {
        costTotal+=w;
        cout <<u<<" "<<v<<"\n";
    }
    cout<<costTotal<<"\n"<<n;

}