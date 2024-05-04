package jbw9964.M_2024_05.Week_1.P_2559;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static final BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    private static int N, K;
    private static int[] array;

    public static void main(String[] args) throws IOException {
        input();

        int initSum = 0;
        int index = 0;
        while (index < K)
        initSum += array[index++];

        int maxima = Integer.MIN_VALUE;

        while (index < N)   {
            if (maxima < initSum)   maxima = initSum;

            initSum += array[index];
            initSum -= array[index++ - K];
        }

        if (maxima < initSum)   maxima = initSum;

        System.out.println(maxima);
    }

    private static void input() throws IOException  {
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());

        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());

        array = new int[N];
        tokenizer = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++)
        array[i] = Integer.parseInt(tokenizer.nextToken());
    }
}