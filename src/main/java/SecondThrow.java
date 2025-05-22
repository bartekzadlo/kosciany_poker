package main.java;

import java.util.Random;
import java.util.Scanner;

public class SecondThrow {
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

    public int[] reroll(int[] dice) {
        System.out.println("\nPodaj numery kości do przerzutu (np. 1 3 5), lub zostaw puste jeśli nie chcesz przerzucać:");

        String input = scanner.nextLine();
        if (!input.isBlank()) {
            String[] tokens = input.trim().split("\\s+");
            for (String token : tokens) {
                try {
                    int index = Integer.parseInt(token) - 1;
                    if (index >= 0 && index < dice.length) {
                        dice[index] = random.nextInt(6) + 1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ignoruję niepoprawny numer: " + token);
                }
            }
        }

        System.out.println("\nWynik po drugim rzucie:");
        for (int i = 0; i < dice.length; i++) {
            System.out.println("Kość " + (i + 1) + ": " + dice[i]);
        }
        return dice;
    }

    public void closeScanner() {
        scanner.close();
    }
}
