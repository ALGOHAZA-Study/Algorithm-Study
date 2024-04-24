package jbw9964.M_2024_04.Week_3.P_14888;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    private static int N;
    private static int[] Numbers;
    private static int[] OPNums = new int[4];       //  {+, -, *, /}
    // private static String[] OPs = {"+", "-", "*", "/"};

    private static long minAnswer = Long.MAX_VALUE;
    private static long maxAnswer = Long.MIN_VALUE;

    private static void input() throws IOException {
        N = Integer.parseInt(br.readLine());
        Numbers = new int[N];

        String[] inputStrings = br.readLine().split(" ");
        for (int i = 0; i < N; i++)
        Numbers[i] = Integer.parseInt(inputStrings[i]);

        inputStrings = br.readLine().split(" ");
        for (int i = 0; i < 4; i++)
        OPNums[i] = Integer.parseInt(inputStrings[i]);
    }

    public static void main(String[] args) throws IOException {
        input();

        compSearch(1, Numbers[0]);

        System.out.println(maxAnswer);
        System.out.println(minAnswer);
    }

    // init index should be 1, init value must be Numbers[0]
    public static void compSearch(int index, long value) {
        if (index == N)             {
            if (value > maxAnswer)  maxAnswer = value;
            if (value < minAnswer)  minAnswer = value;
            
            return;
        }
        
        for (int i = 0; i < 4; i++) {
            if (OPNums[i] <= 0)     continue;
            
            OPNums[i] -= 1;
            
            if (i == 0)         compSearch(index + 1, value + Numbers[index]);
            else if (i == 1)    compSearch(index + 1, value - Numbers[index]);
            else if (i == 2)    compSearch(index + 1, value * Numbers[index]);
            else if (i == 3)    compSearch(index + 1, value / Numbers[index]);

            OPNums[i] += 1;
        }

        return;
    }
}