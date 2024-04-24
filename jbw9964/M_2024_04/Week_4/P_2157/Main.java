package jbw9964.M_2024_04.Week_4.P_2157;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    private static int N, M, K;
    private static int[][] costTable;
    private static int[][] DP;

    private static void input() throws IOException  {
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());

        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        costTable = new int[N + 1][N + 1];
        DP = new int[M + 1][N + 1];         // M by N matrix

        for (int i = 0; i < K; i++) {
            tokenizer = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());

            costTable[a][b] = Math.max(costTable[a][b], c);
        }
    }
    
    public static void main(String[] args) throws IOException {
        input();

        DP[2] = costTable[1];       // copy costs that only visited 2 cities : (1 --> ?)

        for (int visited = 3; visited <= M; visited++)  {
            for (int currentCity = visited; currentCity <= N; currentCity++)    {

                for (int prevCity = 2; prevCity < currentCity; prevCity++)  {

                    int prevVisitCost = DP[visited - 1][prevCity];              // cost that visited (visited - 1) cities
                    int prevToCurrentCost = costTable[prevCity][currentCity];   // cost of (visited - 1) to current city

                    // unavailable movement : (visited - 1) movement or (visited - 1) to current city movement is not allowed 
                    if (prevVisitCost == 0 || prevToCurrentCost == 0)   continue;  

                    // store maxima
                    DP[visited][currentCity] = Math.max(DP[visited][currentCity], prevVisitCost + prevToCurrentCost);
                }
            }
        }

        int maxima = 0;
        for (int visited = 2; visited <= M; visited++)
        if (maxima < DP[visited][N])
        maxima = DP[visited][N];

        System.out.print(maxima);
    }
}
