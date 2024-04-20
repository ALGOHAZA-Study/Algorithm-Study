package jbw9964.M_2024_04.Week_3.P_16987;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main   {
    private static class Egg    {
        int hp, w;
        Egg(int hp, int w)  {this.hp = hp; this.w = w;}
    }
    private static final BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in)
    );

    private static int N;
    private static Egg[] eggs;
    private static int answer;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        eggs = new Egg[N];

        for (int i = 0; i < N; i++) {
            String[] inputs = br.readLine().split(" ");
            eggs[i] = new Egg(
                Integer.parseInt(inputs[0]), 
                Integer.parseInt(inputs[1])
            );
        }

        compSearch(0);

        System.out.println(answer);
    }

    public static void compSearch(int index) {

        if (index >= N)             return;
        if (eggs[index].hp <= 0)    {compSearch(index + 1); return;} 

        for (int i = 0; i < N; i++) {
            if (i == index)         continue;
            if (eggs[i].hp <= 0)    continue;

            eggs[i].hp -= eggs[index].w;
            eggs[index].hp -= eggs[i].w;

            compSearch(index + 1);

            int count = 0;
            for (Egg egg : eggs)
            if (egg.hp <= 0)
            count++;

            answer = answer < count ? count : answer;

            eggs[i].hp += eggs[index].w;
            eggs[index].hp += eggs[i].w;
        }
    }
}