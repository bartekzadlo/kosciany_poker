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
        System.out.println("Układ końcowy: " + ScoreEvaluator.evaluate(player1.getDice()));

        System.out.println(player2.getName() + ":");
        printDice(player2.getDice());
        System.out.println("Układ końcowy: " + ScoreEvaluator.evaluate(player2.getDice()));
    }

    private static void printDice(int[] dice) {
        for (int i = 0; i < dice.length; i++) {
            System.out.print(dice[i] + " ");
        }
        System.out.println();
    }
}
