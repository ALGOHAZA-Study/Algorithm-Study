
---

## [`5373 번 : 큐빙`](https://www.acmicpc.net/problem/5373)

- 알고리즘 분류 : `구현`, `시뮬레이션`

---

## 🔖 문제 설명

- 루빅스 큐브를 구현해 주어진 연산을 수행한 후, 맨 윗면의 색상을 출력하는 문제이다.

---

## 🛑 문제 제한 조건

- 시간 제한 : $\text{1 s}$

---

## 🍳 스스로 생각한 접근 방식

문제를 보았을 때 구현문제임을 짐작하였다. 루빅스 큐브에는 `54` 개의 칸이 존재한다. 이 때 면을 회전시켰을 때, 특정 칸들의 색상이 바뀌면 되므로, `색상을 교체하는` 부분만 잘 만들면 될 듯 싶었다.

큐브에는 총 `6` 개의 면이 존재하고 각 칸마다 색상이 존재하므로, 이들을 지칭할 `enum 상수` 를 만들었다.

```java
public static enum Face   {
    UP(0), FRONT(1), LEFT(2),
    DOWN(3), BACK(4), RIGHT(5);

    private final int value;
    private Face(int value) {this.value = value;}
    public int getIndex()   {return value;}
}
public static enum Color  {
    WHITE(0), RED(1), GREEN(2),
    YELLOW(3), ORANGE(4), BLUE(5);

    private final int value;
    private Color(int value)                {this.value = value;}
    public static Color create(int index)   {
        Color color = null;
        switch (index) {
            case 0 : color = Color.WHITE;   break;
            case 1 : color = Color.RED;     break;
            case 2 : color = Color.GREEN;   break;
            case 3 : color = Color.YELLOW;  break;
            case 4 : color = Color.ORANGE;  break;
            case 5 : color = Color.BLUE;    break;
        }
        return color;
    }
};
```

문제를 보면 여러개의 `test 케이스` 에 따라 큐브의 색상을 물어본다. 각 케이스마다 큐브를 초기화 시켜야 하므로, 다음과 같은 메서드를 구현하였다.

```java
private Color[][] cube;

public RubiksCube() {
    cube = new Color[6][9];
    initialize();
}
public void initialize() {
    for (Face face : Face.values()) {
        int index = face.getIndex();
        Color color = Color.create(index);
        
        for (int i = 0; i < 9; i++)
        cube[index][i] = color;
    }
}
```

그래서 한 `test 케이스` 가 끝난 후, `initialize` 메서드를 호출해 큐브를 초기상태로 되돌릴 수 있다.

큐브를 회전시키는 행동은 크게 2 가지로 구분지을 수 있다. 큐브가 회전하였을 때,
- `해당 면 내 칸들의 색상이 교체` 되어야 하고,
- `인접한 면 내 칸들의 색상이 교체` 되어야 한다.

각 행동에서 색상이 교체되는 연산을 생각해보면, 이 둘이 사뭇 다르게 작동하는 것을 알 수 있다.

- `i. 해당 면 내 칸들의 색상이 교체`
    
    |||||   |   |   |   |   |   |   |
    |:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
    |`6`|`3`|`0`||`0`|`1`|`2`|   |`2`|`5`|`8`|
    |`7`|`4`|`1`|`<-- 시계`|`3`|`4`|`5`|`반시계 -->`|`1`|`4`|`7`|
    |`8`|`5`|`2`||`6`|`7`|`8`|   |`0`|`3`|`6`|

- `ii. 인접한 면 내 칸들의 색상이 교체`
    |   |   |   |
    |:---:|:---:|:---:|
    |   |`인접 면 1`|   |
    |`인접 면 4`|`해당 면`|`인접 면 2`|
    |   |`인접 면 3`|   |

    - `시계 방향` : `(면 1 내 3 칸)` > `(면 2 내 3 칸)` > `(면 3 내 3 칸)` > `(면 4 내 3 칸)` > `(면 1 내 3 칸)`
    - `시계 방향` : `(면 1 내 3 칸)` > `(면 4 내 3 칸)` > `(면 3 내 3 칸)` > `(면 2 내 3 칸)` > `(면 1 내 3 칸)`


`i.` 의 경우 `0, 1`, `2, 5` 와 같이 `2 개의 칸` 이 짝을 지어 교체된다. 반면 `ii.` 의 경우 `3 개의 칸` 이 짝을 지어 교체된다.

그래서 이 둘을 아래처럼 분리해 구현하였다.

```java
public void rotateFace(Face face, Direction dir)    {
    rotateCurrentFace(face, dir);
    rotateAdjacentFace(face, dir);
}
```

<details><summary> 메서드 세부 구현</summary>

나는 칸들의 색상 교체가 `Queue` 를 이용하면 조금 쉬워질 수 있다 생각했다.

이런 칸들의 색상 교체가 `배열 내 두 원소를 swap` 하는 것과 유사하므로, `"swap 시켜줄 index"` 를 `Queue` 에 잘 담으면 될 것이라 생각했기 때문이다.

또한 이처럼 `index` 를 `Queue` 에 담는 형태로 구현하면, 회전 방향에 따라 `Queue` 의 삽입 순서만 잘 바꿔주면 된다.
- `i. 시계 방향` : `{0, 1}` `-->` `{2, 5}` `-->` `{8, 7}` `-->` `{6, 3}`
- `i. 반시계 방향` : `{0, 1}` `-->` `{6, 3}` `-->` `{8, 7}` `-->` `{2, 5}`

다만 한가지 걸리는 부분이 있었는 데, `i.` 는 위처럼 `Queue` 에 간단히 넣을 수 있지만, `ii.` 는 매우 복잡하다는 것이다.

`ii.` 의 경우, 회전하는 면에 따라서 `Queue` 에 넣을 `index` 가 달라진다. `U` 면을 회전시키면 `B`, `R`, `F`, `L` 면의 최상단 칸들을 넣어야 하고, `D` 면을 회전시키면 최하단 칸들을 넣어야 한다.

하지만 이를 대체할 방안이 떠오르지 않아 결국 `모든 case 에 따라 잘 지정` 하기로 하였다.

```java
private static <T> void changeDirection(Deque<T> deque) {
    Deque<T> tempDeque = new ArrayDeque<>();
    
    while (deque.size() > 1)
    tempDeque.add(deque.pollLast());

    deque.addAll(tempDeque);
}
```

- `i. 해당 면 내 칸들의 색상이 교체`

    ```java
    private void rotateCurrentFace(Face face, Direction dir)    {
        int faceIndex = face.getIndex();
        Color[] faceColors = cube[faceIndex];

        Deque<int[]> indexDeque = new ArrayDeque<>(4);
        indexDeque.add(new int[] {0, 1});
        indexDeque.add(new int[] {2, 5});
        indexDeque.add(new int[] {8, 7});
        indexDeque.add(new int[] {6, 3});

        if (dir == Direction.CW)
        changeDirection(indexDeque);

        int[] prevIndex = indexDeque.poll();

        Color[] reserveColors = new Color[] {
            faceColors[prevIndex[0]], 
            faceColors[prevIndex[1]]
        };

        while (!indexDeque.isEmpty())   {
            int[] currentIndex = indexDeque.poll();

            for (int i = 0; i < 2; i++)
            faceColors[prevIndex[i]] = faceColors[currentIndex[i]];

            prevIndex = currentIndex;
        }

        for (int i = 0; i < 2; i++)
        faceColors[prevIndex[i]] = reserveColors[i];
    }
    ```

- `ii. 인접한 면 내 칸들의 색상이 교체`

    ```java
    @SuppressWarnings("unchecked")
    private void rotateAdjacentFace(Face face, Direction dir)   {
        Deque<?>[] queueArray = getAdjacentFaceAndIndex(face, dir);

        Deque<Face> adjacentFaceQueue = (Deque<Face>) queueArray[0];
        Deque<int[]> colorSwapIndexQueue = (Deque<int[]>) queueArray[1];

        Face prevFace = adjacentFaceQueue.poll();
        int[] prevIndex = colorSwapIndexQueue.poll();

        Color[] reserveColors = new Color[] {
            cube[prevFace.getIndex()][prevIndex[0]],
            cube[prevFace.getIndex()][prevIndex[1]],
            cube[prevFace.getIndex()][prevIndex[2]],
        };

        while (!adjacentFaceQueue.isEmpty())    {
            Face currentFace = adjacentFaceQueue.poll();
            int[] currentIndex = colorSwapIndexQueue.poll();

            for (int i = 0; i < 3; i++)
            cube[prevFace.getIndex()][prevIndex[i]] = cube[currentFace.getIndex()][currentIndex[i]];

            prevFace = currentFace;
            prevIndex = currentIndex;
        }

        for (int i = 0; i < 3; i++)
        cube[prevFace.getIndex()][prevIndex[i]] = reserveColors[i];
    }
    ```

    <details><summary> getAdjacentFaceAndIndex</summary>

    ```java
    private Deque<?>[] getAdjacentFaceAndIndex(Face face, Direction dir)  {
        Deque<Face> adjacentFaceQueue = new ArrayDeque<>(4);
        Deque<int[]> colorSwapIndexQueue = new ArrayDeque<>(4);
        
        switch (face) {     // store indexes on clock-wise  --> works on counter clock-wise
            case UP :
                adjacentFaceQueue.add(Face.FRONT);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});
                
                adjacentFaceQueue.add(Face.LEFT);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});
                
                adjacentFaceQueue.add(Face.BACK);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});

                adjacentFaceQueue.add(Face.RIGHT);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});
                break;
        
            case FRONT :
                adjacentFaceQueue.add(Face.UP);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});
                
                adjacentFaceQueue.add(Face.RIGHT);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});

                adjacentFaceQueue.add(Face.DOWN);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});

                adjacentFaceQueue.add(Face.LEFT);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});
                break;

            case LEFT :
                adjacentFaceQueue.add(Face.UP);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});
                
                adjacentFaceQueue.add(Face.FRONT);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});

                adjacentFaceQueue.add(Face.DOWN);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});

                adjacentFaceQueue.add(Face.BACK);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});
                break;

            case DOWN :
                adjacentFaceQueue.add(Face.FRONT);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});
                
                adjacentFaceQueue.add(Face.RIGHT);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});

                adjacentFaceQueue.add(Face.BACK);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});

                adjacentFaceQueue.add(Face.LEFT);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});
                break;
            
            case BACK :
                adjacentFaceQueue.add(Face.UP);
                colorSwapIndexQueue.add(new int[] {2, 1, 0});
                
                adjacentFaceQueue.add(Face.LEFT);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});

                adjacentFaceQueue.add(Face.DOWN);
                colorSwapIndexQueue.add(new int[] {6, 7, 8});

                adjacentFaceQueue.add(Face.RIGHT);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});
                break;

            case RIGHT :
                adjacentFaceQueue.add(Face.UP);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});
                
                adjacentFaceQueue.add(Face.BACK);
                colorSwapIndexQueue.add(new int[] {0, 3, 6});

                adjacentFaceQueue.add(Face.DOWN);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});

                adjacentFaceQueue.add(Face.FRONT);
                colorSwapIndexQueue.add(new int[] {8, 5, 2});
                break;
        }

        if (dir == Direction.CW)    {
            changeDirection(adjacentFaceQueue);
            changeDirection(colorSwapIndexQueue);
        }

        return new Deque[] {adjacentFaceQueue, colorSwapIndexQueue};
    }
    ```

    </details>


</details>

---

## 🛠 자신의 풀이에서 개선할 부분

해당 문제를 풀이하고 `ii.` 부분의 소스가 너무 길고 무식하다 느꼈다.

하지만 다른 이들의 소스를 참고하여도 개선할 `방향` 이 보이지 않았다. `(다들 어쩔 수 없이 경우의 수 마다 잘 선택하는 방식으로 구현하였음)`

분명히 `ii.` 부분을 개선하면 더 좋은 풀이가 되겠지만 개선할 방향은 찾지 못하였다.

\+ 반면 `i.` 부분은 `회전 행렬` [`[1]`](#1--rotation-matrix) 을 이용하면 더 간편할 것 같았다.

---

## Reference

- ##### [`[1] : Rotation matrix`](https://en.wikipedia.org/wiki/Rotation_matrix)

---


