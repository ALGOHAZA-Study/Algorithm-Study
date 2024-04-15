package assets.example.P_2609;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_2609 {

    private static final BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );


    public static void main(String[] args) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(br.readLine());
        
        int num1 = Integer.parseInt(tokenizer.nextToken());
        int num2 = Integer.parseInt(tokenizer.nextToken());

        int gcd = GCD(num1, num2);
        int lcm = num1 * num2 / gcd;

        System.out.println(gcd);
        System.out.println(lcm);
    }

    public static int GCD(int a, int b) {       // get Greatest common divisor using euclidean algorithm
        while (b != 0) {
            int mod = a % b;
            a = b;
            b = mod;
        }
        
        return a;
    }
}
