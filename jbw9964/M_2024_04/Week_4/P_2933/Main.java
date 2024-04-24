package jbw9964.M_2024_04.Week_4.P_2933;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Mineral   {           // single Mineral
    int r, c;
    Mineral(int r, int c)   {
        this.r = r;
        this.c = c;
    }

    public void moveDown()  {r += 1;}
}

class MineralManager    {       // manage many Minerals
    private int R, C;
    private int counter;        // counter to determine throw (left -> right) or (right -> left)

    private Mineral[][] mineralTable;           // table for existing Minerals
    private static int[] dr = {0, 0, 1, -1};
    private static int[] dc = {1, -1, 0, 0};

    private boolean inRange(int r, int c)   {
        return 0 <= r && r < R && 0 <= c && c < C;
    }

    public MineralManager(int R, int C) {
        this.R = R;
        this.C = C;

        mineralTable = new Mineral[R][C];
    }

    // read table from buffer
    public void assignTable(BufferedReader br) throws IOException {
        for (int i = 0; i < R; i++) {
            char[] lineInput = br.readLine().toCharArray();

            for (int j = 0; j < lineInput.length; j++)
            if (lineInput[j] == 'x')
            mineralTable[i][j] = new Mineral(i, j);
        }
    }

    // return most (left or right) Mineral
    private Mineral getRowMineral(int r)    {
        Mineral selection = null;

        if (counter++ % 2 == 0) {
            for (int i = 0; i < C; i++)
            if (mineralTable[r][i] != null) {
                selection = mineralTable[r][i];
                break;
            }
        }

        else    {
            for (int i = C - 1; i >= 0; i--)
            if (mineralTable[r][i] != null) {
                selection = mineralTable[r][i];
                break;
            }
        }

        return selection;
    }

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

    // remove most (left or right) Mineral from table
    // search falling cluster & drop it
    public void removeMineral(int r) {
        Mineral removal = getRowMineral(r);     // most (L-R) Mineral
        
        if (removal == null)    return;         // no Minerals to remove

        mineralTable[removal.r][removal.c] = null;  // remove Mineral from table

        // System.out.println(this);

        Queue<Mineral> fallingCluster = null;
        for (int i = 0; i < 4; i++) {
            if (!inRange(removal.r + dr[i], removal.c + dc[i]))     continue;

            // selection adjacent Mineral from table
            Mineral adjacent = mineralTable[removal.r + dr[i]][removal.c + dc[i]];
            if (adjacent == null)       continue;

            // check whether selected Minerals to fall
            fallingCluster = getFallingCluster(adjacent);

            if (fallingCluster != null) {       // selected Minerals should fall
                dropCluster(fallingCluster);    // drop cluster
                return;
            }
        }
    }

    // drop selected cluster untill reached ground or other cluster
    private void dropCluster(Queue<Mineral> fallingCluster)  {
        // most below Minerals in falling cluster
        Queue<Mineral> mostBottoMinerals = getMostDownMinerals(fallingCluster);

        boolean flag = false;
        do {
            moveClusterDown(fallingCluster);    // move cluster down

            for (Mineral mineral : mostBottoMinerals)   {
                if (mineral.r == R - 1)                                 {flag = true; break;}   // cluster reached to bottom
                else if (inRange(mineral.r + 1, mineral.c) 
                    && mineralTable[mineral.r + 1][mineral.c] != null)  {flag = true; break;}   // cluster reached to other cluster(Mineral)
            }

        } while (!flag);    // loop untill cluster reach to bottom
    }

    // return Queue that contains most bottom Minerals in given cluster
    private Queue<Mineral> getMostDownMinerals(Queue<Mineral> cluster)  {
        HashMap<Integer, Mineral> columnHashMap = new HashMap<>();

        for (Mineral mineral : cluster) {
            int column = mineral.c;
            if (!columnHashMap.containsKey(column))   {     // no minima stored in current column
                columnHashMap.put(column, mineral);
                continue;
            }

            Mineral prev = columnHashMap.get(column);

            // previous Mineral is above than current
            if (mineral.r > prev.r)             columnHashMap.put(column, mineral);     // replace minima
        }

        Queue<Mineral> mostDownQueue = new LinkedList<>();

        for (Mineral mineral : columnHashMap.values())
        mostDownQueue.add(mineral);     // store minimas to Queue

        return mostDownQueue;
    }

    // move cluster down & sync with table
    private void moveClusterDown(Queue<Mineral> cluster)    {
        for (Mineral mineral : cluster) {
            int r = mineral.r;
            int c = mineral.c;
            mineralTable[r][c] = null;
            mineral.moveDown();
        }

        for (Mineral mineral : cluster) {
            int r = mineral.r;
            int c = mineral.c;
            mineralTable[r][c] = mineral;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (mineralTable[i][j] != null)     sb.append("x");
                else                                sb.append(".");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}


public class Main {
    private static BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    public static void main(String[] args) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());

        int R = Integer.parseInt(tokenizer.nextToken());
        int C = Integer.parseInt(tokenizer.nextToken());

        MineralManager manager = new MineralManager(R, C);
        manager.assignTable(br);

        br.readLine();
        tokenizer = new StringTokenizer(br.readLine());
        while (tokenizer.hasMoreTokens())
        manager.removeMineral(R - Integer.parseInt(tokenizer.nextToken()));

        System.out.print(manager);
    }
}
