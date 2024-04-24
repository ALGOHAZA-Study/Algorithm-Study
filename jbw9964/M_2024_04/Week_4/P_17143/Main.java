package jbw9964.M_2024_04.Week_4.P_17143;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;
import java.io.IOException;

class Shark {
    public static class Coord {     // 좌표 지시용 static nested class
        int r, c;
        Coord(int r, int c) {this.r = r; this.c = c;}

        public int getRow() {return this.r;}
        public int getCol() {return this.c;}

        @Override
        public String toString() {
            return String.format("[%d, %d]", r, c);
        }
    }

    static enum Direction   {       // 방향 지시용 enum 상수
        UP(1), DOWN(2), RIGHT(3), LEFT(4);

        private int value;
        private Direction(int value)    {this.value = value;}

        @Override
        public String toString() {
            String str = null;
            switch (value) {
                case 1 : str = "UP";    break;
                case 2 : str = "DOWN";  break;
                case 3 : str = "RIGHT"; break;
                case 4 : str = "LEFT";  break;
            }
            return str;
        }
    }

    private Coord coord;            // 상어 좌표
    private int speed, size;        // 상어 속도, 크기
    private Direction dir;          // 이동 방향

    public Shark(int r, int c, int s, int d, int z) {
        speed = s;
        size = z;

        coord = new Coord(r, c);

        switch (d) {
            case 1 : dir = Direction.UP;    break;
            case 2 : dir = Direction.DOWN;  break;
            case 3 : dir = Direction.RIGHT; break;
            case 4 : dir = Direction.LEFT;  break;
        }
    }

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

    public int getSize()    {return this.size;}
    public Coord getCoord() {return this.coord;}

    @Override
    public String toString() {
        return String.format("%d, %d", speed, size);
    }
}

class SharkManager  {       // 다수의 상어 관리 클래스
    private int R, C;

    private Set<Shark> sharkSet;    // 먹히지 않고 살아있는 상어 저장할 Set
    private Shark[][] sharkTable;   // 낚시터 table

    public SharkManager(int R, int C, int M)    {
        this.R = R;
        this.C = C;

        sharkSet = new HashSet<>(2 * M);
        sharkTable = new Shark[R][C];
    }

    // 상어 추가
    public void addShark(int r, int c, int s, int d, int z) {
        Shark newShark = new Shark(r, c, s, d, z);
        sharkSet.add(newShark);
        
        sharkTable[r][c] = newShark;
    }

    public void moveShark() {                       // 모든 상어 이동
        Shark[][] newTable = new Shark[R][C];       // 모든 이동 마친 table

        Queue<Shark> removalSharks = new LinkedList<>();    // 잡아먹힌 상어 저장할 Queue

        for (Shark shark : sharkSet)    {
            shark.moveShark(R, C);      // 상어 이동

            Shark.Coord coord = shark.getCoord();
            int r = coord.getRow();
            int c = coord.getCol();

            // 상어가 이동한 곳에 또다른 상어가 없음
            if (newTable[r][c] == null)     {newTable[r][c] = shark; continue;}

            // 상어가 이동한 곳에 또다른 상어가 존재
            Shark tempShark = newTable[r][c];
            Shark sharkToAppend = shark.getSize() > tempShark.getSize() ? shark : tempShark;
            Shark sharkToRemove = shark.getSize() < tempShark.getSize() ? shark : tempShark;

            newTable[r][c] = sharkToAppend;         // 크기가 더 큰 상어만 table 에 저장
            removalSharks.add(sharkToRemove);       // 크기가 작은 상어는 Queue 에 저장
        }

        this.sharkTable = newTable;                 // table 교체

        while (!removalSharks.isEmpty())    {               // 잡아먹힌 상어들 Set 에서 삭제
            Shark sharkToRemove = removalSharks.poll();
            sharkSet.remove(sharkToRemove);
        }
    }

    public int catchShark(int c)    {           // c 위치에 존재하는 상어 잡기
        Shark sharkToRemove = null;
        int size = 0;
        
        for (int i = 0; i < R; i++) {
            Shark closestShark = sharkTable[i][c];

            if (closestShark == null)   continue;

            sharkToRemove = closestShark;       // 최상단에 위치한 상어 선택
            break;
        }

        if (sharkToRemove != null)  {
            sharkSet.remove(sharkToRemove);     // 살아있는 상어 Set 에서 잡은 상어 삭제
            size = sharkToRemove.getSize();
        }

        return size;            // 잡은 상어 크기 반환
    }

    @Override
    public String toString()    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++)
            sb.append(sharkTable[i][j]).append("\t\t");
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
        int M = Integer.parseInt(tokenizer.nextToken());

        SharkManager manager = new SharkManager(R, C, M);

        while (M-- > 0) {
            tokenizer = new StringTokenizer(br.readLine());

            int r = Integer.parseInt(tokenizer.nextToken()) - 1;
            int c = Integer.parseInt(tokenizer.nextToken()) - 1;
            int s = Integer.parseInt(tokenizer.nextToken());
            int d = Integer.parseInt(tokenizer.nextToken());
            int z = Integer.parseInt(tokenizer.nextToken());

            manager.addShark(r, c, s, d, z);
        }

        M = 0;

        for (int c = 0; c < C; c++) {
            M += manager.catchShark(c);
            manager.moveShark();
        }

        System.out.println(M);
    }
}