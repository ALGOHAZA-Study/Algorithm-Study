package jbw9964.M_2024_04.Week_4.P_15993;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    private static StringBuilder sb = new StringBuilder();

    private static long[] tableOdd = new long[100_000 + 1];
    private static long[] tableEven = new long[100_000 + 1];
    private static int cursor = 3;
    private static int MOD = 1_000_000_009;

    public static void main(String[] args) throws IOException {
        tableOdd[1] = tableOdd[2] = tableEven[2] = 1;
        tableEven[1] = 0;
        
        tableOdd[3] = tableEven[3] = 2;

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            int n = Integer.parseInt(br.readLine());

            while (cursor < n)  {
                tableOdd[++cursor] = (
                    tableEven[cursor - 1] + tableEven[cursor - 2] + tableEven[cursor - 3]
                ) % MOD;
                
                tableEven[cursor] = (
                    tableOdd[cursor - 1] + tableOdd[cursor - 2] + tableOdd[cursor - 3]
                ) % MOD;
            }

            sb.append(tableOdd[n] + " " + tableEven[n]).append("\n");  
        }

        System.out.print(sb.toString());
    }
}
