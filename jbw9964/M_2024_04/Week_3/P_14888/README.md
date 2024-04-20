
---

## [`14888 번 : 연산자 끼워넣기`](https://www.acmicpc.net/problem/14888)

- 알고리즘 분류 : `브루트포스`, `백트래킹`

---

## 🔖 문제 설명

- 주어진 숫자와 연산자를 이용해 조합될 수 있는 식의 최댓값과 최솟값을 출력하는 문제이다.
- 이 때 조합되는 식 내 연산자의 우선순위는 고려되지 않고 오직 `(왼쪽 --> 오른쪽)` 순으로 식이 계산된다.
- 나눗셈 연산은 나눠진 몫만 취하고, 음수를 양수로 나누는 경우 `C++14` 의 기준을 따른다. `(-a / b) --> - (a / b)`

---

## 🛑 문제 제한 조건

- 시간 제한 : $\text{2 s}$
- 수의 개수 `N` : $2 \leq N \leq 11$

---

## 🍳 스스로 생각한 접근 방식

이전 [`[16987 번 : 계란으로 계란치기]`](https://www.acmicpc.net/problem/16987) 문제를 풀어서 그런지, 해당 문제를 `백트래킹` 방식으로 풀 수 있는것을 인지하였다.

가능한 조합의 개수는 최대 $[11! \approx 4 \times 10^{7}]$ 이므로 시간 복잡도 또한 널널하다 생각하였다.

해당 문제는 결국 계산될 숫자는 고정되있고 연산자의 위치 변화로 경우의 수가 달라진다. 그래서 연산자 변화를 `백트래킹` 하면 될 듯 싶었다.

---

## ❗ 틀린 이유 설명

아래의 코드는 처음 구현한 코드 중 일부이다.

```java
// OPNums   : 연산자 개수 저장
// Numbers  : 숫자 저장

// init index should be 1, init value must be Numbers[0]
public static void compSearch(int index, long value) {  
    if (index == N)             {
        if (value > maxAnswer)  maxAnswer = value;
        if (value < minAnswer)  minAnswer = value;
        return;
    }

    for (int i = 0; i < 4; i++) {
        if (OPNums[i] <= 0)     continue;
        
        OPNums[i] -= 1;
        
        if (i == 0)         value += Numbers[index];
        else if (i == 1)    value -= Numbers[index];
        else if (i == 2)    value *= Numbers[index];
        else if (i == 3)    value /= Numbers[index];

        compSearch(index + 1, value);

        OPNums[i] += 1;
    }

    return;
}
```

코드를 보면 어느 연산자를 선택해 `value` 를 알맞게 계산하고 백트래킹 하는 것을 볼 수 있다.

하지만 이를 테스트 케이스로 확인하니 맞지 않았다.

여러번 디버깅 후 다음처럼 바꿔야 됨을 깨달았다.

```java
// OPNums   : 연산자 개수 저장
// Numbers  : 숫자 저장

// init index should be 1, init value must be Numbers[0]
public static void compSearch(int index, long value) {
    if (index == N)             {
        if (value > maxAnswer)  maxAnswer = value;
        if (value < minAnswer)  minAnswer = value;
        return;
    }
    
    for (int i = 0; i < 4; i++) {
        if (OPNums[i] <= 0)     continue;
        
        OPNums[i] -= 1;
        
        if (i == 0)         compSearch(index + 1, value + Numbers[index]);
        else if (i == 1)    compSearch(index + 1, value - Numbers[index]);
        else if (i == 2)    compSearch(index + 1, value * Numbers[index]);
        else if (i == 3)    compSearch(index + 1, value / Numbers[index]);

        OPNums[i] += 1;
    }

    return;
}
```

앞선 코드에서는 `value` 매개변수에 계산된 결과를 저장하였다. 문제는 `for` 반복에서 그 `value` 를 계속 사용한다는 것이다.

예를 들어 `i == 0` 인 경우를 생각하자. 연산자를 만나 계산하였을 때, `value` 는 그 값이 변한다. 이를 `compSearch` 메서드로 제공하는 것은 문제가 없지만, `i == 1` 반복에서 문제가 생긴다.

`i == 1` 반복에서도 `value` 는 `이전 계산값` 을 가르켜야 된다. 하지만 `value` 의 값이 변하며 이를 위반하게 되고, 때문에 이상한 값을 기준으로 `백트래킹` 이 진행된다.

---


## ✅ 올바른 접근 방식 및 해결 방식

위 내용을 확인하여 개선하였다.

---

## 🛠 자신의 풀이에서 개선할 부분

풀이 방법을 올바르게 구현하였지만 디버깅을 사용하였다.

한번에 문제를 풀이하려면 더 많은 문제를 접해야 할 듯 하다.

---
