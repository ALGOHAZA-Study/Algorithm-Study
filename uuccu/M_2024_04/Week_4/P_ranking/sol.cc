#include <string>
#include <vector>
#include <bits/stdc++.h>

using namespace std;

map<int, vector<int>> win;
map<int, vector<int>> defeat;

bool win_visited[101];
bool defeat_visited[101];

int win_recursive(int num) {
    if (win[num].empty()) {
        return 0;
    }
    int ans = 0;
    for (auto x: win[num]) {
        if (!win_visited[x]) {
            win_visited[x] = true;
            ans += win_recursive(x) + 1;
        }
    }
    return  ans;
}

int defeat_recursive(int num) {
    if (defeat[num].empty()) {
        return 0;
    }
    int ans = 0;
    for (auto x: defeat[num]) {
        if (!defeat_visited[x]) {
            defeat_visited[x] = true;
            ans += defeat_recursive(x) + 1;
        }
    }
    return ans;
}

int solution(int n, vector<vector<int>> results) {
    int answer = 0;

    for (auto x: results) {
        win[x[0]].push_back(x[1]);
        defeat[x[1]].push_back(x[0]);
    }

    for (int i = 1; i <= n; i++) {
        int cnt = 0;
        memset(win_visited,false,sizeof(win_visited));
        memset(defeat_visited,false,sizeof(defeat_visited));
        win_visited[i] = true;
        cnt += win_recursive(i);
        cout << cnt << " ";
        defeat_visited[i] = true;
        cnt += defeat_recursive(i);
        cout << cnt << endl;
        if (cnt == n - 1)
            answer++;
    }

    return answer;
}