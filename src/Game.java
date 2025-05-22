public class Game {
    public static void main(String[] args) {
        FirstThrow firstThrow = new FirstThrow();
        SecondThrow secondThrow = new SecondThrow();

        Player player1 = new Player("Gracz 1");
        Player player2 = new Player("Gracz 2");

        player1.firstThrow(firstThrow);

        player2.firstThrow(firstThrow);

        System.out.println('\n' + player1.getName() + ":");
        printDice(player1.getDice());
        player1.secondThrow(secondThrow);

        System.out.println('\n' + player2.getName() + ":");
        printDice(player2.getDice());
        player2.secondThrow(secondThrow);

        secondThrow.closeScanner();

        System.out.println("\nWyniki końcowe:");

        System.out.println(player1.getName() + ":");
        printDice(player1.getDice());

        System.out.println(player2.getName() + ":");
        printDice(player2.getDice());
    }

    private static void printDice(int[] dice) {
        for (int i = 0; i < dice.length; i++) {
            System.out.print(dice[i] + " ");
        }
        System.out.println();
    }
}
