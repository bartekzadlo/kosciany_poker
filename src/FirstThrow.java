import java.util.Random;

public class FirstThrow {
    public static void main (String[] args) {
        int[] dice = new int[5];
        Random random = new Random();

        System.out.println("Pierwszy rzut kośćmi:");

        for (int i = 0; i < 5; i++) {
            dice[i] = random.nextInt(6) + 1; // losuje od 1 do 6
            System.out.println("Kość " + (i + 1) + ": " + dice[i]);
        }
    }
}

