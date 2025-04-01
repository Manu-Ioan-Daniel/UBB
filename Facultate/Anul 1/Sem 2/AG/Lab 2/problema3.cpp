

#include "problema3.h"

#include <cstring>
#include <fstream>
#include <string>
#include <vector>
#include <iostream>
using namespace std;

int dy[]={0,1,0,-1};
int dx[]={1,0,-1,0};
//dreapta,stanga,jos,sus


bool rezolvaLabirint(int x,int y,int pozFinal[2],vector<pair<int,int>>& solution,int marks[][1005],int labirint[][1005],int n,int m) {
    if (x==pozFinal[0] && y==pozFinal[1]) {
        solution.emplace_back(x,y);
        return true;
    }
    marks[x][y]++;
    solution.emplace_back(x,y);
    for (int i = 0;i<4;i++) {
        const int newX=x+dx[i];
        const int newY=y+dy[i];
        if (newX>0 && newY>0 && newX<=n && newY<=m && marks[newX][newY]<2 && labirint[newX][newY]!=1) {
            if (rezolvaLabirint(newX,newY,pozFinal,solution,marks,labirint,n,m)) {
                return true;
            }
        }
    }
    solution.pop_back();
    marks[x][y]++;
    return false;

}

void problema3() {
    ifstream fin("labirint_1.txt");
    int labirint[1005][1005],n=0,pozStart[2],pozFinal[2],m=0,marks[1005][1005];
    vector<pair<int,int>> solution;
    char s[1005];
    while (fin.getline(s,1005)) {
        n++;
        m = strlen(s);
        for (int i = 0;i<strlen(s);i++) {
            if (s[i]=='1') {
                labirint[n][i+1]=1;
            }else if (s[i]==' ') {
                labirint[n][i+1]=0;
            }else if (s[i]=='S') {
                labirint[n][i+1]=69;
                pozStart[0]=n;
                pozStart[1]=i+1;
            }else {
                labirint[n][i+1]=420;
                pozFinal[0]=n;
                pozFinal[1]=i+1;
            }

        }
    }

    if (rezolvaLabirint(pozStart[0],pozStart[1],pozFinal,solution,marks,labirint,n,m)) {
        cout<<"Labirintul are solutie\n";
        for (auto &i : solution) {
            if (i.first==pozStart[0] && i.second==pozStart[1] || i.first==pozFinal[0] && i.second==pozFinal[1]) {
                continue;
            }
            labirint[i.first][i.second]=8;
        }

        for (int i = 1;i<=n;i++) {
            for (int j = 1;j<=m;j++) {
                if (labirint[i][j]==8) {
                    cout<<".";
                }
                else if (labirint[i][j]==1) {
                    cout<<"1";
                }else if (labirint[i][j]==69) {
                    cout<<"S";
                }else if (labirint[i][j]==420) {
                    cout<<"F";
                }else {
                    cout<<" ";
                }

            }
            cout<<endl;
        }
    }else {
        cout<<"Labirintul nu are solutie\n";
    }

}