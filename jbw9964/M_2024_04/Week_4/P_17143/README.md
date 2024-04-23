
---

## [`17143 번 : 낚시왕`](https://www.acmicpc.net/problem/17143)

- 알고리즘 분류 : `구현`

---

## 🔖 문제 설명

- 낚시터에는 다수의 상어가 존재한다. 각 상어는 1 초마다 `speed` 칸 만큼 이동하는 속도를 가지고, 초기에 움직이는 방향이 주어진다.
- 상어가 이동 중 낚시터의 경계에 부딛힐 때 움직이는 방향은 반대 방향으로 전환된다.
- 낚시터의 어부는 현재 존재하는 `column` 에 위치한 상어 중, 가장 수심이 얕은 상어를 낚는다.
- 어부가 `column == 0` 부터 `column == C` 까지 한번씩 이동하며 상어를 잡을 때, 잡은 상어들의 총 무게를 구하여라.

---

## 🛑 문제 제한 조건

- 시간 제한 : $\text{1 s}$
- 낚시터의 크기 `R`, `C` : $2 \leq \text{R, C} \leq 100$
- 존재하는 상어의 수 `M` : $0 \leq \text{M} \leq \text{R} \times \text{C}$
- 상어의 속력 `s` : $0 \leq \text{s} \leq 1,000$

---

## 🍳 스스로 생각한 접근 방식

문제를 접하고 구현 문제임을 짐작하였다. 

각 상어는 `초기 위치`, `속도`, `초기 방향`, `무게` 가 주어지고, 각 상어마다 이동하는 양상이 다를 수 있으므로, 상어를 나타내는 `Shark` 클래스를 만들기로 하였다.

`Shark` 클래스에 각 상어가 알아서 움직이는 `move` 메서드가 필요함을 느꼈다. 또한 다수의 상어가 존재하므로, 문제에 제시된 모든 상어를 제어하는 `SharkManager` 클래스가 필요함을 느꼈다.

즉, `SharkManager` 에서 존재하는 모든 상어들을 움직이는 `moveAll` 메서드를 구현할 필요를 느꼈다.

`Shark` 클래스의 `move` 는 자기 자신만 잘 움직이면 된다. 하지만 `SharkManager` 의 `moveAll` 은 모든 상어를 이동시킨 후 `(Shark.move)`, 동일한 칸에 상어가 존재하면 이를 처리해야 하므로 추가적인 처리가 필요했다.

이를 `Set`, 배열, `Queue` 를 이용해 처리하였다. 

`SharkManager` 의 `Set` 은 현재 살아있는 모든 상어를 저장한 집합으로, 상어가 이동 후 잡아먹히거나 어부에게 잡혔을 때, 해당 상어를 `Set` 에서 제거한다.

배열은 낚시터 내 존재하는 상어의 위치를 나타내는 배열이다. `Queue` 는 상어가 이동 후, 잡아먹힌 상어를 저장하기 위해 사용하였다.

---

## ❗ 틀린 이유 설명

아래 코드는 초기 구현한 코드 중 일부이다.

```java
class Shark {
    private Coord coord;
    private int speed, size;
    private Direction dir;

    /* ... */

    private void moveShark() {
        switch (dir) {
            case UP     : coord.r -= 1; break;
            case DOWN   : coord.r += 1; break;
            case RIGHT  : coord.c += 1; break;
            case LEFT   : coord.c -= 1; break;
        }
    }
    public void moveShark(int R, int C) {
        int count = speed;
        while (count-- > 0) {

            if (coord.r == 0 && dir == Direction.UP)        dir = Direction.DOWN;
            if (coord.r == R - 1 && dir == Direction.DOWN)  dir = Direction.UP;
            if (coord.c == 0 && dir == Direction.LEFT)      dir = Direction.RIGHT;
            if (coord.c == C - 1 && dir == Direction.RIGHT) dir = Direction.LEFT;

            moveShark();
        }
    }

    /* ... */
}

class SharkManager  {
    private int R, C;

    private Set<Shark> sharkSet;
    private int capacity;
    private int numOfSharks;

    private Queue<Shark>[][] sharkTable;

    /* ... */

    @SuppressWarnings("unchecked")
    public void moveShark() {
        Queue<Shark>[][] newTable = new Queue[R][C];

        Queue<Shark> removalSharks = new LinkedList<>();

        for (Shark shark : sharkSet)    {
            shark.moveShark(R, C);

            /* ... select survived sharks to newTable ... */
            /* ... select removed sharks to removalSharks ... */
        }

        this.sharkTable = newTable;

        while (!removalSharks.isEmpty())    {
            Shark sharkToRemove = removalSharks.poll();
            sharkSet.remove(sharkToRemove);
            numOfSharks -= 1;
        }

    }

    /* ... */
}
```

코드를 제출하니 문제를 맞추긴 하였지만 실행 시간이 $\text{2804 ms}$ 로, 매우 느린 것을 확인하였다.

여러 프로파일링 결과, `Shark.moveShark` 메서드가 병목의 주된 원인임을 확인하였다.

---

## ✅ 올바른 접근 방식 및 해결 방식

`Shark.moveShark` 를 보면 주어진 `speed` 만큼 1 칸씩 계속 움직인다. 하지만 문제의 조건을 보면 $0 \leq \text{s} \leq 1,000$ 로, 한 상어당 움직이는데 너무 많은 시간이 걸리는 것을 확인하였다.

그래서 다음처럼 코드를 개선하였다.

```java
class Shark {
    private Coord coord;            // 상어 좌표
    private int speed, size;        // 상어 속도, 크기
    private Direction dir;          // 이동 방향

    private void moveShark() {      // 방향에 따라 1 칸 이동
        switch (dir) {
            case UP     : coord.r -= 1; break;
            case DOWN   : coord.r += 1; break;
            case RIGHT  : coord.c += 1; break;
            case LEFT   : coord.c -= 1; break;
        }
    }
    public void moveShark(int R, int C) {
        // 만약 (2 * R - 2) 또는 (2 * C - 2) 칸을 이동하면
        // 현재 위치한 곳에 그대로 도착함 (방향도 동일)

        // modulo 이용 불필요한 반복 삭제
        int netMoveR = (speed) % (2 * R - 2);
        int netMoveC = (speed) % (2 * C - 2);

        int count = 0;

        switch (dir) {
            case UP :       // R 방향으로 이동할 횟수 지정
            case DOWN :     count = netMoveR;   break;
        
            case LEFT :     // C 방향으로 이동할 횟수 지정
            case RIGHT :    count = netMoveC;   break;
        }

        // R 방향으로 이동
        if (dir == Direction.UP || dir == Direction.DOWN)           {
            while (count-- > 0)     {       // 지정된 횟수만큼 반복
                // 막다른 곳 도착시 방향 전황
                if (coord.r == 0 && dir == Direction.UP)        dir = Direction.DOWN;
                if (coord.r == R - 1 && dir == Direction.DOWN)  dir = Direction.UP;
                moveShark();
            }
        }

        // C 방향으로 이동
        else if (dir == Direction.LEFT || dir == Direction.RIGHT)   {
            while (count-- > 0)     {       // 지정된 횟수만큼 반복
                // 막다른 곳 도착시 방향 전황
                if (coord.c == 0 && dir == Direction.LEFT)      dir = Direction.RIGHT;
                if (coord.c == C - 1 && dir == Direction.RIGHT) dir = Direction.LEFT;
                moveShark();
            }
        }
    }
}
```

낚시터의 크기는 제한되어 있기 때문에, 상어가 아무리 빠르게 이동하더라도 반드시 `"주기성"` 을 갖는다. 확인 결과, `좌, 우` 로 이동하는 상어는 `(2 * R - 2)` 만큼 이동할 때마다, `상, 하` 는 `(2 * C - 2)` 마다 주기성을 갖는 것을 확인하였다.

그래서 `(speed) % (2 * R - 2)` 처럼 처리하여 불필요한 반복을 하지 않도록 제한하였다.

이를 제출한 결과 실행 시간이 $\text{636 ms}$ 로 감소하였다.

---

## 🛠 자신의 풀이에서 개선할 부분

위 같은 방식으로 `move` 를 구현하면, 각 상어는 최대 `2 * (R, C) - 2` 만큼 반복하여 움직이므로, 최대 약 `200` 번의 반복을 갖는다.

하지만 다른 이들의 코드를 보니 나처럼 `반복을 이용해서 구현` 한 방식은 없는 것 같았다. 오히려 어떤 계산식으로 한번에 구할 수 있어 보였다.

나도 그 방식을 이용하고 싶었으나 그 계산식이 왜 그렇게 되는지 이해되지 않아 포기하였다.

이를 이해하고 개선하면 훨씬 빠른 실행 시간을 가질것으로 예상된다.

---
