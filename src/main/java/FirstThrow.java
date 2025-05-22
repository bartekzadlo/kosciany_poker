package main.java;

import java.util.Random;

public class FirstThrow {
    private Random random = new Random();

    public int[] roll() {
        int[] dice = new int[5];
        System.out.println("Pierwszy rzut:");
        for (int i = 0; i < 5; i++) {
            dice[i] = random.nextInt(6) + 1;
            System.out.println("Kość " + (i + 1) + ": " + dice[i]);
        }
        return dice;
    }
}
