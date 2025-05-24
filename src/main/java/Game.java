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

            int currentStake = stake; // aktualna stawka (może być podbita)
            boolean someoneFolded = false;
            Player foldWinner = null; // kto wygrał, gdy ktoś spasował

            // --- Runda pierwszego gracza ---
            player1.firstThrow(firstThrow);
            System.out.println(player1.getName() + ":");
            printDice(player1.getDice());
            System.out.println("Układ: " + ScoreEvaluator.evaluate(player1.getDice()));

            // --- Runda pierwszego rzutu drugiego gracza ---
            player2.firstThrow(firstThrow);
            System.out.println(player2.getName() + ":");
            printDice(player2.getDice());
            System.out.println("Układ: " + ScoreEvaluator.evaluate(player2.getDice()));

            // Teraz pytanie o podbicie stawki, najpierw dla pierwszego gracza
            currentStake = tryRaise(scanner, player1, player2, stake, currentStake);
            if (currentStake == -1) { // oznacza, że drugi gracz spasował
                someoneFolded = true;
                foldWinner = player1;
            }

            // Jeśli nikt nie spasował, pytanie o podbicie dla drugiego gracza
            if (!someoneFolded) {
                currentStake = tryRaise(scanner, player2, player1, stake, currentStake);
                if (currentStake == -1) { // oznacza, że pierwszy gracz spasował
                    someoneFolded = true;
                    foldWinner = player2;
                }
            }

            if (!someoneFolded) {
                // --- Drugi rzut obu graczy ---
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
                    System.out.println(player1.getName() + " wygrywa i zdobywa " + currentStake + " zł!");
                    player1.addMoney(currentStake);
                    player2.subtractMoney(currentStake);
                } else if (result < 0) {
                    System.out.println(player2.getName() + " wygrywa i zdobywa " + currentStake + " zł!");
                    player2.addMoney(currentStake);
                    player1.subtractMoney(currentStake);
                } else {
                    System.out.println("Remis! Nikt nie traci pieniędzy.");
                }
            } else {
                // Gdy ktoś spasował
                System.out.println("\n" + foldWinner.getName() + " wygrywa, ponieważ przeciwnik spasował.");
                foldWinner.addMoney(currentStake);
                if (foldWinner == player1) {
                    player2.subtractMoney(currentStake);
                } else {
                    player1.subtractMoney(currentStake);
                }
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

    // Metoda do obsługi podbicia stawki
    // Zwraca:
    //  - nową stawkę jeśli zaakceptowano podbicie lub brak podbicia
    //  - -1 jeśli przeciwnik spasował
    private static int tryRaise(Scanner scanner, Player raiser, Player opponent, int initialStake, int currentStake) {
        System.out.println("\n" + raiser.getName() + ", czy chcesz podbić stawkę? (aktualna stawka: " + currentStake + " zł)");
        System.out.println("1. Tak");
        System.out.println("2. Nie");
        int choice = scanner.nextInt();

        if (choice == 1) {
            int maxRaise = Math.min(raiser.getMoney() - currentStake, opponent.getMoney() - currentStake);
            if (maxRaise <= 0) {
                System.out.println("Conajmniej jeden z graczy, nie ma wystarczającej ilości pieniędzy.");
                return currentStake;
            }
            int raiseAmount;
            while (true) {
                System.out.print("Podaj kwotę podbicia (max " + maxRaise + " zł): ");
                raiseAmount = scanner.nextInt();

                if (raiseAmount > 0 && raiseAmount <= maxRaise) {
                    break;
                } else {
                    System.out.println("Nieprawidłowa kwota podbicia.");
                }
            }

            int newStake = currentStake + raiseAmount;

            System.out.println(opponent.getName() + ", czy akceptujesz podbicie do " + newStake + " zł?");
            System.out.println("1. Akceptuję");
            System.out.println("2. Puszczam (poddaję się)");

            int opponentChoice = scanner.nextInt();
            if (opponentChoice == 1) {
                System.out.println(opponent.getName() + " zaakceptował podbicie.");
                return newStake;
            } else {
                System.out.println(opponent.getName() + " spasował.");
                return -1; // przeciwnik spasował
            }
        }
        return currentStake; // brak podbicia
    }


    private static void printDice(int[] dice) {
        for (int value : dice) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
