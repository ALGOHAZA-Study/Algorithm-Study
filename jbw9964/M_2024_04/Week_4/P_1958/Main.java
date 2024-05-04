package jbw9964.M_2024_04.Week_4.P_1958;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    private static int[][][] DP;

    public static void main(String[] args) throws IOException {
        char[] str1 = br.readLine().toCharArray();
        char[] str2 = br.readLine().toCharArray();
        char[] str3 = br.readLine().toCharArray();

        DP = new int[str1.length + 1][str2.length + 1][str3.length + 1];

        for (int i = 1; i <= str1.length; i++)   {
            for (int j = 1; j <= str2.length; j++)   {
                for (int k = 1; k <= str3.length; k++)   {
                    if (str1[i - 1] == str2[j - 1] && str2[j - 1] == str3[k - 1])
                    DP[i][j][k] = DP[i - 1][j - 1][k - 1] + 1;
                    else
                    DP[i][j][k] = max(DP[i - 1][j][k], DP[i][j - 1][k], DP[i][j][k - 1]);
                }
            }
        }

        System.out.println(DP[str1.length][str2.length][str3.length]);
    }

    private static int max(int ... values)  {
        int maxima = Integer.MIN_VALUE;
        for (int value : values)
        if (maxima < value)
        maxima = value;

        return maxima;
    }
}

