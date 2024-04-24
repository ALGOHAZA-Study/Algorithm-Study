# 1074 Z

태그: 백준
유형: 분할정복

2^N × 2^N인 2차원 배열을 Z모양으로 탐색하고 r행 c열을 찾는 상황이라면

4분할을 하고 또 4분할을 하면서

r,c가 사분면 내에 존재하는 경우를 찾는 것을 방법을 생각했다

쉽게 말하면 크게 4등분으로 쪼개고 계속 쪼개면서 답을 찾기!

**사분면 찾기**

```
if (c < s && r < s) idx = 0;
else if (c >= s && r < s) idx = 1;
else if (c < s && r >= s) idx = 2;
else if (c >= s && r >= s) idx = 3;

```

**인덱스 계산 & 좌표 업데이트**

```cpp
    c %= s;
    r %= s;
    ans += (s * s) * idx;
```

**전체 코드**

```cpp
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

```
