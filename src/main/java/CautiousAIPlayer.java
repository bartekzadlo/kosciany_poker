package main.java;

import java.util.Random;

public class CautiousAIPlayer extends AbstractPlayer {
    private Random random = new Random();

    public CautiousAIPlayer(String name, int startingMoney) {
        super(name, startingMoney);
    }

    @Override
    public void secondThrow(SecondThrow secondThrow) {
        System.out.println("\n" + name + " (AI ostrożne) wykonuje drugi rzut:");

        // Przykładowa ostrożna logika:
        // Przerzucamy tylko kości, które mają wartość <= 2 (słabe kości)
        for (int i = 0; i < dice.length; i++) {
            if (dice[i] <= 2) {
                dice[i] = random.nextInt(6) + 1;
            }
        }

        System.out.println("Wynik po drugim rzucie:");
        for (int i = 0; i < dice.length; i++) {
            System.out.println("Kość " + (i + 1) + ": " + dice[i]);
        }
    }
}
