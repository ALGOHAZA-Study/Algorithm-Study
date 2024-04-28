package jbw9964.M_2024_04.Week_4.P_1038;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    public static void main(String[] args) throws IOException {
        int N = Integer.parseInt(br.readLine());

        String[] array = genArray();

        System.out.println(array[N] == null ? -1 : array[N]);
    }

    private static String[] genArray()  {
        String[] array = new String[1_000_000 + 1];

        for (int i = 0; i <= 9; i++)
        array[i] = String.valueOf(i);

        int indexHead = 0;
        int indexTail = 10;

        while (indexHead < indexTail)   {

            String current = array[indexHead++];
            char lastDigit = current.charAt(current.length() - 1);

            for (char digit = '0'; digit < lastDigit; digit++)
            array[indexTail++] = current + String.valueOf(digit);
        }

        return array;
    }
}
