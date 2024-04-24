#include <iostream>
#include <cmath> 
using namespace std;

int main() {
    int n, r, c;
    cin >> n >> r >> c;

    int ans = 0;
    int s = 0;
    
    while (n > 0) {
        int idx;
        s = 1 << (n-1); 

        if (c < s && r < s) idx = 0;
        else if (c >= s && r < s) idx = 1;
        else if (c < s && r >= s) idx = 2;
        else if (c >= s && r >= s) idx = 3;

        c %= s;
        r %= s;

        ans += (s * s) * idx; 
        n--;
    }

    cout << ans << endl;
    return 0;
}