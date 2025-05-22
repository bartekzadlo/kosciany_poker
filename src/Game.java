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

        // Drugi rzut gracza 1
        System.out.println('\n' + player1.getName() + ":");
        printDice(player1.getDice());
        player1.secondThrow(secondThrow);
        System.out.println("Układ po drugim rzucie: " + ScoreEvaluator.evaluate(player1.getDice()));

        // Drugi rzut gracza 2
        System.out.println('\n' + player2.getName() + ":");
        printDice(player2.getDice());
        player2.secondThrow(secondThrow);
        System.out.println("Układ po drugim rzucie: " + ScoreEvaluator.evaluate(player2.getDice()));

        secondThrow.closeScanner();

        System.out.println("\nWyniki końcowe:");

        System.out.println(player1.getName() + ":");
        printDice(player1.getDice());
        String hand1 = ScoreEvaluator.evaluate(player1.getDice());
        ScoreEvaluator.HandDetail detail1 = ScoreEvaluator.evaluateDetailed(player1.getDice());
        System.out.println("Układ końcowy: " + hand1);

        System.out.println(player2.getName() + ":");
        printDice(player2.getDice());
        String hand2 = ScoreEvaluator.evaluate(player2.getDice());
        ScoreEvaluator.HandDetail detail2 = ScoreEvaluator.evaluateDetailed(player2.getDice());
        System.out.println("Układ końcowy: " + hand2);

        System.out.println("\nZwycięzca:");
        int result = detail1.compareTo(detail2);
        if (result > 0) {
            System.out.println(player1.getName() + " wygrywa!");
        } else if (result < 0) {
            System.out.println(player2.getName() + " wygrywa!");
        } else {
            System.out.println("Remis!");
        }
    }

    private static void printDice(int[] dice) {
        for (int value : dice) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
