#include <string>
#include <vector>
#include <bits/stdc++.h>

using namespace std;

int solution(int n, vector<vector<int>> edge) {
    int answer = 0;
    
    queue<pair<int,int>> q; // {node 번호 , 1에서의 거리}
    vector<int> visited(n+1,false);
    
    vector<int> e[20001];
    
    for(auto x: edge){
        int a = x[0];
        int b = x[1];
        
        e[a].push_back(b);
        e[b].push_back(a);
    }
    
    q.emplace(1,0);
    visited[1] = true;
    
    int dis = 0;
    
    while(!q.empty()){
        auto cur = q.front();
        q.pop();
        
        if(dis != cur.second){
            answer = 0;
            dis = cur.second;
        }
        answer++;
        
        for(auto next: e[cur.first]){
            if(!visited[next]){
                visited[next] = true;
                q.emplace(next,cur.second+1);
            }
        }
    }
    
    return answer;
}