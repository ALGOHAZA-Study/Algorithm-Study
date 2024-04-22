
int dp[1500001];
vector<pair<int, int>> v;
int n;

int recursive(int cur) {
    if (cur >= n) {
        return 0;
    }

    if (dp[cur] != -1)
        return dp[cur];

    int day = v[cur].first;
    int cost = v[cur].second;

    int ans = -1;
    if (cur + day <= n) {
        ans = recursive(cur + day) + cost;
    }
    ans = max(recursive(cur + 1), ans);

    return dp[cur] = ans;
}

void solution() {

    cin >> n;

    for (auto &x: dp)x = -1;
    v = vector<pair<int, int>>(n);

    for (auto &x: v) {
        cin >> x.first >> x.second;
    }
    cout << recursive(0);
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
//    int testcase;
//    cin >> testcase;
//    while (testcase--)
    solution();
}