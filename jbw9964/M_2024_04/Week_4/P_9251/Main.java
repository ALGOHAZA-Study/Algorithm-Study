package jbw9964.M_2024_04.Week_4.P_9251;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    private static int[][] DP;

    public static void main(String[] args) throws IOException {
        char[] str1 = br.readLine().toCharArray();
        char[] str2 = br.readLine().toCharArray();

        DP = new int[str1.length + 1][str2.length + 1];

        for (int i = 1; i <= str1.length; i++)  {
            for (int j = 1; j <= str2.length; j++)  {
                if (str1[i - 1] == str2[j - 1])     DP[i][j] = DP[i - 1][j - 1] + 1;
                else                                DP[i][j] = Math.max(DP[i - 1][j], DP[i][j - 1]);
            }
        }

        System.out.println(DP[str1.length][str2.length]);
    }
}
