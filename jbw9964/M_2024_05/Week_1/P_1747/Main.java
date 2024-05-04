package jbw9964.M_2024_05.Week_1.P_1747;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static final BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    private static List<Integer> primeNumbers = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        int N = Integer.parseInt(br.readLine());

        initPrimes(N);

        System.out.println(solve(N));
    }

    private static void initPrimes(int N)   {
        boolean[] table = new boolean[N + 1];       // true : not a prime number
        table[0] = table[1] = true;
        
        int index = 2;

        while (index <= N)  {
            if (!table[index])  {
                for (int i = 2 * index; i <= N; i += index)
                table[i] = true;
            }

            index++;
        }

        for (int i = 2; i < N; i++)
        if (!table[i])   primeNumbers.add(i);
    }

    private static int solve(int N) {
        while (true)    {
            if (isPalindrome(N) && isPrime(N))
            return N;
            N++;
        }
    }

    private static boolean isPrime(int num)    {
        if (num == 1)   return false;

        boolean flag = true;

        for (int prime : primeNumbers)
        if (num % prime == 0)   {
            flag = false;
            break;
        }

        if (flag)   primeNumbers.add(num);

        return flag;
    }

    private static boolean isPalindrome(int num)    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(num));
        sb.reverse();

        return num == Integer.parseInt(sb.toString());
    }
}
