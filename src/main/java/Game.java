package main.java;

import java.util.Scanner;

public class Game {
    public static void playTwoPlayers() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        System.out.print("Podaj początkową ilość pieniędzy dla każdego gracza: ");
        int startingMoney = scanner.nextInt();
        Player player1 = new Player("Gracz 1", startingMoney);
        Player player2 = new Player("Gracz 2", startingMoney);

        while (playAgain) {
            System.out.println("\nAktualny stan kont:");
            System.out.println(player1.getName() + ": " + player1.getMoney() + " zł");
            System.out.println(player2.getName() + ": " + player2.getMoney() + " zł");

            int stake;
            while (true) {
                System.out.print("\nPodaj stawkę na tę partię: ");
                stake = scanner.nextInt();

                if (stake > 0 && player1.getMoney() >= stake && player2.getMoney() >= stake) {
                    break;
                } else {
                    System.out.println("Nieprawidłowa stawka. Upewnij się, że obaj gracze mają wystarczające środki.");
                }
            }

            FirstThrow firstThrow = new FirstThrow();
            SecondThrow secondThrow = new SecondThrow();

            // Pierwsze rzuty
            player1.firstThrow(firstThrow);
            System.out.println(player1.getName() + ":");
            printDice(player1.getDice());
            System.out.println("Układ: " + ScoreEvaluator.evaluate(player1.getDice()));

            player2.firstThrow(firstThrow);
            System.out.println(player2.getName() + ":");
            printDice(player2.getDice());
            System.out.println("Układ: " + ScoreEvaluator.evaluate(player2.getDice()));

            // Drugie rzuty
            System.out.println("\n" + player1.getName() + ":");
            printDice(player1.getDice());
            player1.secondThrow(secondThrow);
            System.out.println("Układ po drugim rzucie: " + ScoreEvaluator.evaluate(player1.getDice()));

            System.out.println("\n" + player2.getName() + ":");
            printDice(player2.getDice());
            player2.secondThrow(secondThrow);
            System.out.println("Układ po drugim rzucie: " + ScoreEvaluator.evaluate(player2.getDice()));

            // Porównanie wyników
            ScoreEvaluator.HandDetail detail1 = ScoreEvaluator.evaluateDetailed(player1.getDice());
            ScoreEvaluator.HandDetail detail2 = ScoreEvaluator.evaluateDetailed(player2.getDice());

            System.out.println("\nZwycięzca:");
            int result = detail1.compareTo(detail2);
            if (result > 0) {
                System.out.println(player1.getName() + " wygrywa i zdobywa " + stake + " zł!");
                player1.addMoney(stake);
                player2.subtractMoney(stake);
            } else if (result < 0) {
                System.out.println(player2.getName() + " wygrywa i zdobywa " + stake + " zł!");
                player2.addMoney(stake);
                player1.subtractMoney(stake);
            } else {
                System.out.println("Remis! Nikt nie traci pieniędzy.");
            }

            // Sprawdź czy ktoś nie zbankrutował
            if (player1.getMoney() <= 0 || player2.getMoney() <= 0) {
                System.out.println("\nKoniec gry! Jeden z graczy nie ma już pieniędzy.");
                System.out.println("Stan końcowy:");
                System.out.println(player1.getName() + ": " + player1.getMoney() + " zł");
                System.out.println(player2.getName() + ": " + player2.getMoney() + " zł");
                break;
            }

            // Menu
            System.out.println("\nCo chcesz zrobić?");
            System.out.println("1. Zagraj ponownie");
            System.out.println("2. Wróć do menu głównego");
            System.out.println("3. Zakończ grę");

            int decision = scanner.nextInt();
            switch (decision) {
                case 1:
                    break;
                case 2:
                    playAgain = false;
                    break;
                case 3:
                    System.out.println("Do zobaczenia!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Nieprawidłowy wybór, wracamy do menu.");
                    playAgain = false;
                    break;
            }
        }
    }

    private static void printDice(int[] dice) {
        for (int value : dice) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
