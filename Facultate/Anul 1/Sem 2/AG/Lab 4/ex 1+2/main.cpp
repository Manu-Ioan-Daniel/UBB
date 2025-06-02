#include <vector>
#include <set>
#include <algorithm>
#include <fstream>
#include <iostream>
using namespace std;
//prufer coding and decoding
//input is:
// 7
// -1 0 1 1 2 0 5
void codarePrufer(vector<int> &prufer, const vector<int> &parents, vector<int> &children,set<int> &leaves, const int n) {
    while (!leaves.empty() && prufer.size()<=n-2) {
        const int leaf=*leaves.begin();   // get the smallest leaf
        leaves.erase(leaves.begin()); // remove it from the set
        int parent=parents[leaf]; // get the parent of the leaf
        prufer.push_back(parent); // add the parent to the Prufer code
        children[parent]--; // decrease the number of children of the parent
        if (children[parent] == 0) { // if the parent has no more children
            leaves.insert(parent); // add the parent to the set of leaves
        }
    }
}
int min_leaf(vector<int> &prufer) { // find first smallest number missing from Prufer code
    int i=0;
    while (i<prufer.size()+2) {
        if (find(prufer.begin(), prufer.end(), i) == prufer.end()) {
            return i; // return the first number not in Prufer code
        }
        i++;
    }
    return -1;
}

void decodarePrufer(vector<int> &prufer,vector<int>& parents, const int n) {
    for (int i=0;i<n-1;++i) {
        const int x=prufer[0]; //get first element of Prufer code
        int y=min_leaf(prufer) ; //find the smallest leaf not in prufer code (or smallest number not in prufer code)
        parents[y]=x; // set parent of leaf to x
        prufer.erase(prufer.begin()); // remove first element (x) from Prufer code
        prufer.push_back(y); // add leaf to decoded vector
    }
}

int main() {
    ifstream in("input_prufer");
    int n;
    in >> n;
    vector<int> prufer;
    vector<int> parents(n);
    vector<int> children(n);
    for (int i=0;i<n;++i) {
        in>> parents[i];
        if (parents[i]!=-1) {
            children[parents[i]]++;
        }
    }
    set<int> leaves;
    for (int i=0;i<n;++i) {
        if (children[i]==0) {
            leaves.insert(i);
        }
    }

    codarePrufer(prufer, parents, children,leaves, n);
    cout<<"Codare Prufer:\n";
    cout<<prufer.size()<<"\n";
    for (const int i : prufer) {
        cout<<i<<" ";
    }
    cout<<"\n";
    parents.assign(n,-1);
    decodarePrufer( prufer, parents, n);
    cout<<"Decodare Prufer:\n";
    cout<<n<<"\n";
    for (const int i : parents) {
        cout<<i<<" ";
    }

    return 0;
}