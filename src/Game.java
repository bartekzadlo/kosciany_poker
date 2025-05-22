public class Game {
    public static void main(String[] args) {
        FirstThrow firstThrow = new FirstThrow();
        SecondThrow secondThrow = new SecondThrow();

        Player player1 = new Player("Gracz 1");
        Player player2 = new Player("Gracz 2");

        // Pierwszy rzut gracza 1
        player1.firstThrow(firstThrow);
        System.out.println("\n" + player1.getName() + ":");
        printDice(player1.getDice());
        System.out.println("Układ: " + ScoreEvaluator.evaluate(player1.getDice()));

        // Pierwszy rzut gracza 2
        player2.firstThrow(firstThrow);
        System.out.println("\n" + player2.getName() + ":");
        printDice(player2.getDice());
        System.out.println("Układ: " + ScoreEvaluator.evaluate(player2.getDice()));

        // Wyświetlanie kości przed drugim rzutem gracza 1 (zgodnie z oryginałem)
        System.out.println('\n' + player1.getName() + ":");
        printDice(player1.getDice());
        player1.secondThrow(secondThrow);
        System.out.println("Układ po drugim rzucie: " + ScoreEvaluator.evaluate(player1.getDice()));

        // Wyświetlanie kości przed drugim rzutem gracza 2 (zgodnie z oryginałem)
        System.out.println('\n' + player2.getName() + ":");
        printDice(player2.getDice());
        player2.secondThrow(secondThrow);
        System.out.println("Układ po drugim rzucie: " + ScoreEvaluator.evaluate(player2.getDice()));

        secondThrow.closeScanner();

        System.out.println("\nWyniki końcowe:");

        System.out.println(player1.getName() + ":");
        printDice(player1.getDice());
        String hand1 = ScoreEvaluator.evaluate(player1.getDice());
        int strength1 = ScoreEvaluator.evaluateStrength(player1.getDice());
        System.out.println("Układ końcowy: " + hand1);

        System.out.println(player2.getName() + ":");
        printDice(player2.getDice());
        String hand2 = ScoreEvaluator.evaluate(player2.getDice());
        int strength2 = ScoreEvaluator.evaluateStrength(player2.getDice());
        System.out.println("Układ końcowy: " + hand2);

        System.out.println("\nZwycięzca:");
        if (strength1 > strength2) {
            System.out.println(player1.getName() + " wygrywa!");
        } else if (strength2 > strength1) {
            System.out.println(player2.getName() + " wygrywa!");
        } else {
            // Remis – dodatkowo porównujemy najwyższą kość
            int max1 = maxValue(player1.getDice());
            int max2 = maxValue(player2.getDice());
            if (max1 > max2) {
                System.out.println(player1.getName() + " wygrywa dzięki wyższej kości (" + max1 + " vs " + max2 + ")!");
            } else if (max2 > max1) {
                System.out.println(player2.getName() + " wygrywa dzięki wyższej kości (" + max2 + " vs " + max1 + ")!");
            } else {
                System.out.println("Remis!");
            }
        }
    }

    private static void printDice(int[] dice) {
        for (int value : dice) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    private static int maxValue(int[] dice) {
        int max = 0;
        for (int die : dice) {
            if (die > max) {
                max = die;
            }
        }
        return max;
    }
}
