
---

## [`2933 번 : 미네랄`](https://www.acmicpc.net/problem/2933)

- 알고리즘 분류 : `구현`, `BFS`

---

## 🔖 문제 설명

- 동굴에는 다양한 형태의 미네랄이 존재한다. 동굴 속 미네랄의 형태가 주어지고, 공중으로 막대기를 던져 미네랄을 파괴시켜 떨어뜨릴 때, 모든 막대기를 던진 후 미네랄의 형태를 출력하는 문제이다.
- 막대는 `(좌 -> 우)`, `(우 -> 좌)` 방향으로 번갈아 던지며, 막대는 오직 1 개의 미네랄만 파괴할 수 있다.
- 미네랄이 파괴될 경우 어느 미네랄은 공중에 뜨게 될 수 있다. 공중에 뜬 미네랄은 곧바로 바닥으로 떨어져 다른 미네랄을 만나거나 바닥에 닿을 때까지 떨어진다.
- 또한 떨어지는 미네랄은 떨어지는 도중, 이후에도 인접한 미네랄과의 형태를 유지한다.

---

## 🛑 문제 제한 조건

- 시간 제한 : $\text{1 s}$
- 동굴 크기 `R`, `C` : $1 \leq \text{R, C} \leq 100$
- 막대를 던진 횟수 `N` : $1 \leq \text{N} \leq 100$

---

## 🍳 스스로 생각한 접근 방식

문제에서 미네랄의 형태는 배열 형태로 주어지고, 막대를 이용해 미네랄을 부순 후 어느 클러스터가 떨어지는지 확인해야 한다.

때문에 해당 문제는 `DFS` 또는 `BFS` 를 이용해야함을 짐작하였다.

어느 미네랄을 부수면, 떨어지는 클러스터는 반드시 부순 미네랄과 연결되어 있던 클러스터이다. 따라서 미네랄을 부수고 `인접한 미네랄` 에 대해 `땅에 연결되어 있는지 확인` 하기로 하였다.

땅에 연결되어 있지 않은 클러스터를 확인하였다면, 해당 클러스터가 더 떨어지지 않을 때까지 하강 시켜야 한다. 이는 클러스터에 속하는 모든 미네랄의 `index` 를 알고 있어야 함을 시사한다. 그래서 앞서 `땅에 연결되어 있는지 확인` 하는 동시에, 방문한 미네랄의 정보를 `Queue` 에 저장하여 이용하는 것이 좋아 보였다.

또한 클러스터가 `더 떨어지지 않을 때까지 하강` 시켜야 되는데, 더 떨어지지 않는 경우는 `클러스터의 최하단 미네랄 중 하나` 가 바닥 또는 다른 클러스터에 착지 하는 경우이다.

즉, `떨어지는 클러스터` 중 `최하단에 위치한 미네랄들의 정보` 를 알고 있어야 하고, 클러스터 전체를 떨어뜨리며 `더 떨어지지 않는지 확인` 해야 한다는 것이다.


---

## ❗ 틀린 이유 설명

해당 문제는 이전에 한번 시도했다 실패한 문제이다. 그래서 이전에 무엇이 문제였는가 적도록 하겠다.

<details><summary> 이전 코드</summary>

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {

    private static final BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    private static boolean[][] caveTable;   // true : mineral
    private static int R, C;
    private static int N;
    private static int[] heights;

    private static class Coord {
        int r, c;
        public Coord(int r, int c)  {this.r = r; this.c = c;}
        public Coord(Coord coord, int dr, int dc) {
            this.r = coord.r + dr;
            this.c = coord.c + dc;
        }
    }

    private static boolean inRange(int r, int c)    {
        return 0 <= r && r < R && 0 <= c && c < C;
    }
    private static boolean inRange(Coord coord, int dr, int dc) {
        return inRange(coord.r + dr, coord.c + dc);
    }

    public static void input() throws IOException {
        String[] input = br.readLine().split(" ");
        R = Integer.parseInt(input[0]);
        C = Integer.parseInt(input[1]);

        caveTable = new boolean[R][C];
        for (int i = 0; i < R; i++) {
            input = br.readLine().split("");
            for (int j = 0; j < C; j++) {
                if (input[j].charAt(0) == 'x')  caveTable[i][j] = true;
            }
        }

        N = Integer.parseInt(br.readLine());
        heights = new int[N];
        input = br.readLine().split(" ");
        for (int i = 0; i < N; i++) heights[i] = Integer.parseInt(input[i]);
    }

    public static void main(String[] args) throws IOException {
        input();

        int[] dr = {1, 0, 0, -1};
        int[] dc = {0, -1, 1, 0};

        for (int count = 0; count < N; count++) {
            int r = R - heights[count];
            int c;
            if (count % 2 == 0) {   // left to right
                c = 0;
                while (c < C && !caveTable[r][c]) c++;
            }
            else    {               // right to left
                c = C - 1;
                while (0 < c && !caveTable[r][c]) c--;
            }

            if (!inRange(r, c)) continue;

            caveTable[r][c] = false;
            // displayTable();

            Coord fallingCluster = null;
            for (int i = 0; i < 4; i++) {
                if (!inRange(r + dr[i], c + dc[i]))         continue;
                else if (!caveTable[r + dr[i]][c + dc[i]])  continue;

                if (!DFS(r + dr[i], c + dc[i])) {       // in problem, only one cluster can fall simultaneously
                    fallingCluster = new Coord(r + dr[i], c + dc[i]);
                    break;
                }
            }

            if (fallingCluster == null) continue;   // no cluster should fall

            Coord[] connectedMinerals = BFS(fallingCluster);
            Coord[] mostBelowMinerals = getMostBelowCoords(connectedMinerals);
            // System.out.println("Falling");
            // displayTable();
            do {
                moveMineralsDown(connectedMinerals);
                // displayTable();
            } while (!checkCluster(mostBelowMinerals));
        }

        displayTable();
    }


    public static boolean DFS(int r, int c) {  // check whether cluster on ground, true if cluster on ground
        int[] dr = {-1, 0, 0, 1};
        int[] dc = {0, -1, 1, 0};
        boolean[][] visitTable = new boolean[R][C];

        Stack<Coord> stack = new Stack<Coord>();
        stack.push(new Coord(r, c));
        visitTable[r][c] = true;

        while (!stack.isEmpty()) {
            Coord current = stack.pop();

            if (current.r == R - 1) return true;

            for (int i = 0; i < 4; i++) {
                if (!inRange(current, dr[i], dc[i]))                        continue;
                else if (!caveTable[current.r + dr[i]][current.c + dc[i]])  continue;
                else if (visitTable[current.r + dr[i]][current.c + dc[i]])  continue;

                stack.push(new Coord(current, dr[i], dc[i]));
                visitTable[current.r + dr[i]][current.c + dc[i]] = true;
            }
        }

        return false;
    }
    public static boolean DFS(Coord mineral) {
        return DFS(mineral.r, mineral.c);
    }

    public static Coord[] BFS(int r, int c) {
        int[] dr = {-1, 0, 0, 1};
        int[] dc = {0, -1, 1, 0};
        boolean[][] visitTable = new boolean[R][C];
        Coord temp = new Coord(r, c);

        Queue<Coord> queue = new LinkedList<Coord>();
        Queue<Coord> result = new LinkedList<Coord>();
        queue.add(temp);
        result.add(temp);
        visitTable[r][c] = true;

        while (!queue.isEmpty()) {
            Coord current = queue.poll();

            for (int i = 0; i < 4; i++) {
                if (!inRange(current, dr[i], dc[i]))                        continue;
                else if (!caveTable[current.r + dr[i]][current.c + dc[i]])  continue;
                else if (visitTable[current.r + dr[i]][current.c + dc[i]])  continue;

                visitTable[current.r + dr[i]][current.c + dc[i]] = true;
                current = new Coord(current, dr[i], dc[i]);
                queue.add(current);
                result.add(current);
            }
        }

        Coord[] connectedMinerals = new Coord[result.size()];
        int index = 0;
        while (!result.isEmpty())   connectedMinerals[index++] = result.poll();

        return connectedMinerals;
    }
    public static Coord[] BFS(Coord fallingCluster) {return BFS(fallingCluster.r, fallingCluster.c);}

    public static Coord[] getMostBelowCoords(Coord[] connectedMinerals) {
        int c_min = C, c_max = 0;

        for (Coord mineral : connectedMinerals) {
            if (mineral.c > c_max)  c_max = mineral.c;
            if (mineral.c < c_min)  c_min = mineral.c;
        }

        Coord[] mostBelowMinerals = new Coord[c_max - c_min + 1];
        for (Coord mineral : connectedMinerals) {
            
            if (mostBelowMinerals[mineral.c - c_min] == null || mostBelowMinerals[mineral.c - c_min].r < mineral.r) {
                mostBelowMinerals[mineral.c - c_min] = mineral;
            }
        }

        return mostBelowMinerals;
    }
    public static void moveMineralsDown(Coord[] connectedMinerals) {
        for (Coord mineral : connectedMinerals) {
            caveTable[mineral.r][mineral.c] = false;
        }
        for (Coord mineral : connectedMinerals) {
            caveTable[++mineral.r][mineral.c] = true;
        }
    }
    public static boolean checkCluster(Coord[] mostBelowMinerals) {

        boolean flag = false;
        for (Coord mineral : mostBelowMinerals) {
            if (!inRange(mineral, 1, 0))                {flag = true; break;}
            if (caveTable[mineral.r + 1][mineral.c])    {flag = true; break;}
        }

        return flag;
    }
}
```

</details>

이전 코드의 문제 중 첬째는 너무 더럽다는 것이다. 모든 메서드와 기능을 `Main` 클래스에 집어넣어 다시 읽기도 힘들 정도이다.

두번째 문제점은 `최하단 미네랄을 선택` 하는 과정이 맞는지 모르겠다는 점이다.


```java
public static Coord[] getMostBelowCoords(Coord[] connectedMinerals) {
    int c_min = C, c_max = 0;

    for (Coord mineral : connectedMinerals) {
        if (mineral.c > c_max)  c_max = mineral.c;
        if (mineral.c < c_min)  c_min = mineral.c;
    }

    Coord[] mostBelowMinerals = new Coord[c_max - c_min + 1];
    for (Coord mineral : connectedMinerals) {
        
        if (mostBelowMinerals[mineral.c - c_min] == null || mostBelowMinerals[mineral.c - c_min].r < mineral.r) {
            mostBelowMinerals[mineral.c - c_min] = mineral;
        }
    }

    return mostBelowMinerals;
}
```

코드를 보면 최하단 미네랄을 굳이 배열을 할당하고 어떻게 집어넣는 것을 볼 수 있다. 일단 보기엔 맞아보이지만 어떤 반례가 존재할 것 같아 보인다.

---

## ✅ 올바른 접근 방식 및 해결 방식

이전 풀이의 결정적인 부분은 코드가 난잡해 다시 고치기 어려웠던 점이다.

그래서 좀더 구분지어 구현하기로 하였다.

```java
class Mineral   {           // single Mineral
    int r, c;
    Mineral(int r, int c)   {
        this.r = r;
        this.c = c;
    }

    public void moveDown()  {r += 1;}
}
```
```java
class MineralManager    {       // manage many Minerals
    private int R, C;
    private Mineral[][] mineralTable;           // table for existing Minerals

    /* ... */

    // return Queue that stores every falling Minerals, using BFS
    // if selected cluster (initSearch) doesn't fall, return null
    private Queue<Mineral> getFallingCluster(Mineral initSearch) {
        boolean[][] visitTable = new boolean[R][C];
        visitTable[initSearch.r][initSearch.c] = true;
        
        Queue<Mineral> cluster = new LinkedList<>();
        Queue<Mineral> BFSQueue = new LinkedList<>();

        BFSQueue.add(initSearch);

        while (!BFSQueue.isEmpty()) {
            Mineral current = BFSQueue.poll();
            cluster.add(current);

            if (current.r == R - 1)     return null;

            for (int i = 0; i < 4; i++) {
                int r = current.r + dr[i];
                int c = current.c + dc[i];

                if (!inRange(r, c))                 continue;
                if (mineralTable[r][c] == null)     continue; 
                if (visitTable[r][c])               continue;

                visitTable[r][c] = true;
                Mineral adjacent = mineralTable[r][c];
                BFSQueue.add(adjacent);
            }
        }

        return cluster;
    }

    // drop selected cluster untill reached ground or other cluster
    private void dropCluster(Queue<Mineral> fallingCluster)  {
        Queue<Mineral> mostBottoMinerals = getMostDownMinerals(fallingCluster);

        boolean flag = false;
        do {
            moveClusterDown(fallingCluster);
            
            // System.out.println(this);

            for (Mineral mineral : mostBottoMinerals)   {
                if (mineral.r == R - 1)                                 {flag = true; break;}
                else if (inRange(mineral.r + 1, mineral.c) 
                    && mineralTable[mineral.r + 1][mineral.c] != null)  {flag = true; break;}
            }

        } while (!flag);
    }

    // return Queue that contains most bottom Minerals in given cluster
    private Queue<Mineral> getMostDownMinerals(Queue<Mineral> cluster)  {
        HashMap<Integer, Mineral> columnHashMap = new HashMap<>();

        for (Mineral mineral : cluster) {
            int column = mineral.c;
            if (!columnHashMap.containsKey(column))   {
                columnHashMap.put(column, mineral);
                continue;
            }

            Mineral prev = columnHashMap.get(column);

            if (mineral.r > prev.r)             columnHashMap.put(column, mineral);
        }

        Queue<Mineral> mostDownQueue = new LinkedList<>();

        for (Mineral mineral : columnHashMap.values())
        mostDownQueue.add(mineral);

        return mostDownQueue;
    }
}
```

이전 코드와 그나마 다른 부분은 `최하단 미네랄` 을 찾는 부분이다.

```java
// return Queue that contains most bottom Minerals in given cluster
private Queue<Mineral> getMostDownMinerals(Queue<Mineral> cluster)  {
    HashMap<Integer, Mineral> columnHashMap = new HashMap<>();

    for (Mineral mineral : cluster) {
        int column = mineral.c;
        if (!columnHashMap.containsKey(column))   {
            columnHashMap.put(column, mineral);
            continue;
        }

        Mineral prev = columnHashMap.get(column);

        if (mineral.r > prev.r)             columnHashMap.put(column, mineral);
    }

    Queue<Mineral> mostDownQueue = new LinkedList<>();

    for (Mineral mineral : columnHashMap.values())
    mostDownQueue.add(mineral);

    return mostDownQueue;
}
```

이전 풀이에서는 복잡하게 배열을 사용하였지만, 이번 풀이에는 직관적으로 `HashMap` 을 이용하였다. 미네랄의 `column` 을 `key` 로 갖고, `Mineral` 의 높이를 비교해 `최하단 미네랄` 들을 `HashMap` 에 저장한다.

---

## 🛠 자신의 풀이에서 개선할 부분

다른 이들의 풀이와 비교해 보아도 큰 차이가 존재하지 않는다.

올바른 방식으로 풀이한 것 같다.

---
