
## [`16987 번 : 계란으로 계란치기`](https://www.acmicpc.net/problem/16987)

---

## 🔖 문제 설명

- 주어진 규칙에 따라 행동했을 때, 깰 수 있는 계란의 최대값을 출력하는 문제이다.
- 알고리즘 분류 : `브루트포스`, `백트래킹`

<details><summary> 자세한 규칙</summary>

- 문제의 계란은 내구도와 무게가 존재한다. 계란 `A` 와 계란 `B` 를 부딪혔을 때, `A` 는 `B 의 무게` 만큼 내구도가 감소하고, `B` 는 `A 의 무게` 만큼 내구도가 감소한다.
- 계란 `A` 가 순차적으로 주어지고, 이 순서에 맞춰 계란을 이용해야 한다.
- 또다른 계란 `B` 는 `A` 를 제외한 아무 계란이나 선택할 수 있다. `(내구도 > 0 인 계란)`

</details>


---

## 🛑 문제 제한 조건

- 시간 제한 : $\text{2 s}$
- 계란의 개수 `N` : $1 \leq N \leq 8$

---

## 🍳 스스로 생각한 접근 방식

계란의 수가 `8` 개 이하인 조건을 보았을 때, 해당 문제를 `완전탐색` 으로 해결 가능함을 짐작하였다.

하지만 이를 정작 어떻게 구현할 지 짐작되지 않았고, 여러번 시도하다 결국 다른이의 풀이를 검색하였다.

---


## ❗ 틀린 이유 설명

아래 코드는 [`[1]`](#1--c-백준boj---16987--계란으로-계란치기---howudongs-tistory) 에 제시된 코드이다.

```cpp
#include <iostream>
using namespace std;

struct Egg  {int hp, w;};
int N;
Egg egg[10];    // 계란
int ans;        // 깨진 개수

void backTracking(int x)    {       // x -> 현재 들고 있는 계란 번호
    if (x > N + 1)          return;

    for (int i = 1; i <= N; i++) {  // 1 ~ N개의 계란을 모두 탐색(부딪히게 함)
        if (egg[x].hp <= 0)                 backTracking(x + 1);    // 들고 있는 계란이 이미 깨져 있으면 오른쪽 계란으로 백트래킹
        else if (x == i || egg[i].hp <= 0)  continue;  // 자기 자신이랑 이미 깨진 계란은 패스

        else    {
            egg[x].hp -= egg[i].w;  // 부딪힘
            egg[i].hp -= egg[x].w;
            
            backTracking(x + 1);    // 해당 계란과 부딪힌 경우 백트래킹

            egg[x].hp += egg[i].w;  // 원상복구
            egg[i].hp += egg[x].w;
        }
    }

    int tmp = 0;
    for (int i = 1; i<= N; i++) // 깨진 계란의 개수 세어줌
    if (egg[i].hp <= 0)
    tmp++;

    ans = max(ans,tmp);         // 최댓값 정답 저장
}

int main()      {
    cin >> N;
    for (int i = 1; i <= N; i++) {
        int v1, v2;
        cin >> v1 >> v2;
        egg[i] = {v1,v2};
    }

    backTracking(1);

    cout << ans;
}
```

위 코드에서 크게 2 가지를 배울 수 있었다.

- 이전의 나는 각 계란들의 내구도와 무게를 따로 만들어 풀이하고자 하였따. 하지만 간단히 `Egg` 구조체 배열을 이용해 훨씬 간결하고 직관적이게 문제를 풀이할 수 있었다.
- 이전의 나는 경우의 수에 따른 계란들의 내구도를 `int[]` 에 저장해, 이를 메서드 매개변수로 전달하였다. 하지만 이는 매번 배열을 생성하고 코드가 난잡해지므로 비효율적이다. 반면 위의 코드는 `egg[x] += egg[i].w` 와 같이 `"이전에 탐색하며 진행한 상황을 복구"` 시켜 깔끔하게 해결한다.

하지만 한가지 의아한 부분이 있었는데, 깨진 계란의 개수를 세는 부분이다.

```cpp
for (int i = 1; i <= N; i++) {
    if (egg[x].hp <= 0)                 backTracking(x + 1);      
    else if (x == i || egg[i].hp <= 0)  continue; 

    else    {
        egg[x].hp -= egg[i].w;  
        egg[i].hp -= egg[x].w;
        
        backTracking(x + 1);    

        egg[x].hp += egg[i].w;  
        egg[i].hp += egg[x].w;
    }
}

int tmp = 0;
for (int i = 1; i<= N; i++) 
if (egg[i].hp <= 0)
tmp++;
```

코드에 따르면 `"탐색을 진행하고 복구한 다음, 깨진 계란의 수를 세고"` 있다. 이 부분이 잘 납득가지 않는 것이, `"복구하고 개수를 세면, 마지막 계란으로 인해 계란이 깨진 경우의 수가 누락될 수 있는것 아닌가?"` 라고 생각했기 때문이다.

하지만 확인 결과 `for` 문 내 조건문을 `if`, `else if`, `else` 로 작성하였고, `else if (x == i || egg[i].hp <= 0)` 조건 덕분에 경우의 수가 누락되지 않는 것을 확인하였다.

---

## ✅ 올바른 접근 방식 및 해결 방식

위 코드를 읽고 거의 그대로 구현하였다. 다만 내 생각에 `for` 문을 다음처럼 만드는 게 더 직관적인 것 같아 바꾸었다.

```java
for (int i = 0; i < N; i++) {
    if (i == index)         continue;
    if (eggs[i].hp <= 0)    continue;

    eggs[i].hp -= eggs[index].w;
    eggs[index].hp -= eggs[i].w;

    compSearch(index + 1);

    int count = 0;
    for (Egg egg : eggs)
    if (egg.hp <= 0)
    count++;

    answer = answer < count ? count : answer;

    eggs[i].hp += eggs[index].w;
    eggs[index].hp += eggs[i].w;
}
```

---

## 🛠 자신의 풀이에서 개선할 부분

위 방식대로 진행하면 `"경우의 수 분기마다 매번 깨진 계란을 확인하는 꼴"` 이 된다. 물론 `N` 이 `8` 이하이기 때문에 이번 문제에서 상관은 없지만, [`[1]`](#1--c-백준boj---16987--계란으로-계란치기---howudongs-tistory) 의 방식보다는 더 비효율적인 것이 사실이다.

[`[1]`](#1--c-백준boj---16987--계란으로-계란치기---howudongs-tistory) 처럼 좋은 직관을 가지기 위해선 백트래킹 문제를 다수 풀어야 할 듯 하다.

---

## Reference

- ##### [`[1] : [C++] 백준/BOJ - 16987 : 계란으로 계란치기 - howudong's Tistory`](https://howudong.tistory.com/251)

---


