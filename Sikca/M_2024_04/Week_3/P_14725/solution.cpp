#include <bits/stdc++.h>
#define sz(x) (int)x.size()
using namespace std;
typedef struct Trie{
    map <string,Trie*> m;
}Node;
Node root;
Node* ROOT = &root;
void insert(vector <string> v, int dex, Node* temp){
    if ((int)v.size() <= dex) return;
    if (!temp->m[v[dex]]){
           Trie* node = new Trie();
           temp->m[v[dex]] = node;
    }
    insert(v,dex+1,temp->m[v[dex]]);
}
void find(int l, Node* temp){
    for (auto w : temp->m){
        for (int j = 0; j < l; j++){
            cout << "-";
        }
        cout << w.first << '\n';
        find(l+2,w.second);
    }
}
int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    // 확실한 생각과 계획 코드  
    int n;
    cin >> n;
    for (int i = 0; i < n; i++){
        int a;
        cin >> a;
        vector <string> temp;
        for (int j = 0; j < a; j++){
            string s;
            cin >> s;
            temp.push_back(s);
        }
        insert(temp,0,ROOT);
    }
    find(0,ROOT);
    return 0;
}