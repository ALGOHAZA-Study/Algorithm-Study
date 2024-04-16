
---

## 🔖 문제 설명

- 주어진 두 수의 최대 공약수와 최소 공배수를 구하는 문제이다.
- `link` : [`click`](https://www.acmicpc.net/problem/2609)

---

## 🍳 스스로 생각한 접근 방식

문제를 보니 유클리드 호제법을 이용하면 좋겠다는 생각이 들었다.

> 💡 유클리드 호제법이란?
    최대 공약수를 구하는 대표적인 알고리즘으로, **“두 수 a, b (a > b) 가 있을 때, 이 둘 간의 서로소 r 은 b 와 (a % b) 의 서로소와 같다”** 는 법칙이다.
    자세한 내용은 [`[1]`](#1--euclidean-algorithm---wikepedia) 을 참고하는 것이 좋을 듯 하다.

호제법을 이용하면 아주 간편히 최대 공약수를 알아낼 수 있다. 그런데 생각해보니 최대 공약수를 이용하면 최대 공배수 또한 알 수 있었다.

두 수 $A, B$ 의 최대 공약수를 $r$ 이라 하면, $A = a \times r, \ \ B = b \times r$ 로 나타낼 수 있다. 

이 때 두 수의 최대 공배수는 $a \times b \times r = A \times B \div r$ 로 나타낼 수 있다.

결국 유클리드 호제법만 잘 구현하면 끝나는 문제인 것이다.

---


## ❗ 틀린 이유 설명

`(올바르게 문제를 풀이했다)`

---


## ✅ 올바른 접근 방식 및 해결 방식

`(올바르게 문제를 풀이했다)`

---

## 🛠 자신의 풀이에서 개선할 부분

유클리드 호제법의 시간 복잡도를 생각해 보았다. 

직관적으로 생각했을 때, $O(\mathbf{log}(N))$ 과 유사할 것이라 생각하였다. 애초에 유클리드 호제법은 두 수의 `modulo` 연산으로 이뤄지기 때문이다.

찾아본 결과 [`[2]`](#2--euclidean-algorithm---codility) 와 같은 글을 볼 수 있었고, 이에 따르면 호제법은 $O(\mathbf{log(max}(a, b)))$ 의 시간 복잡도를 가진다 한다.

---

## Reference

- ##### [`[1] : Euclidean algorithm - Wikepedia`](https://en.wikipedia.org/wiki/Euclidean_algorithm)
- ##### [`[2] : Euclidean algorithm - Codility`](https://codility.com/media/train/10-Gcd.pdf)

---


