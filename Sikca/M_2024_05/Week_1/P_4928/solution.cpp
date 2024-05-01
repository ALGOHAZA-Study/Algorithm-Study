#include <bits/stdc++.h>
#include <ctype.h>
#define sz(x) (int)x.size()
using namespace std;
string a;
stack <char> s;
stack <char> ans;
int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    //확실한 생각과 계획 코드
    while(1){
        getline(cin,a);
        if (a == "$") return 0;
        s = {};
        for (int i = 0; i < (int)a.size(); i++){
            if (a[i] == '('){
                s.push('(');
            }else if (isdigit(a[i])){
                s.push(a[i]);
            }else if (isalpha(a[i])){
                s.push(a[i]);
            }else if (a[i] == ')'){
                int len = 0;
                string temp;
                int k = 1;
                len += s.top()-'0';
                s.pop();
                while(isdigit(s.top())){
                    len += 10*k * (s.top() -'0');
                    k *= 10;
                    s.pop();
                }
                while(s.top() != '('){
                    temp += s.top();
                    s.pop();
                }
                reverse(temp.begin(),temp.end());
                s.pop();
                
                for (int j = 0; j < len; j++){
                    for (auto w : temp){
                        s.push(w);
                    }
                }
            }
        }
        while(!s.empty()){
            ans.push(s.top());
            s.pop();
        }
        while(!ans.empty()){
            cout << ans.top();
            ans.pop();
        }
        cout << '\n';
    }
    return 0;
}