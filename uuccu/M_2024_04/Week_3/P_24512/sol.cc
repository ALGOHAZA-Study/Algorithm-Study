#include <iostream>
#include <cstring>
#include <vector>
#include <algorithm>
#include <set>
#include <map>
#include <queue>
#include <sstream>
#include <stack>


#define endl "\n"
using namespace std;


int matrix[10][10];

int dp[10][1024]; // cur, visited
int n, m;
int ans = 1e9;
vector<int> ans_path;

void recursive(int cur, int visited, int value, vector<int> &path) {

    if (visited == (1 << n) - 1) {
        if(!matrix[cur][1])return;
        value = max(value,matrix[cur][1]);
        if (ans > value) {
            ans = value;
            ans_path = path;
        }
        return;
    }


    for (int i = 1; i <= n; i++) {
        if (!matrix[cur][i])continue;
        if (visited & 1 << (i - 1))continue;
        path.push_back(i);
        recursive(i, visited | (1 << (i - 1)), max(value, matrix[cur][i]), path);
        path.pop_back();
    }

}

void solution() {

    cin >> n >> m;

    memset(matrix, 0, sizeof(matrix));

    for (int i = 0; i < m; i++) {
        int u, v, c;
        cin >> u >> v >> c;
        matrix[u][v] = c;
    }
    vector<int> v;
    v.push_back(1);
    recursive(1, 1, 0, v);
    if(ans ==1e9){
        cout<<-1;
        return;
    }
    cout << ans << endl;
    for (auto x: ans_path) {
        cout << x << " ";
    }
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
//    int testcase;
//    cin >> testcase;
//    while (testcase--)
    solution();
}

