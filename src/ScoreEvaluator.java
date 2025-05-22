import java.util.Arrays;

public class ScoreEvaluator {

    public static String evaluate(int[] dice) {
        int[] counts = new int[7]; // indeks 1-6 dla liczby oczek, 0 niewykorzystany
        for (int die : dice) {
            counts[die]++;
        }

        boolean isPoker = false;
        boolean isKareta = false;
        boolean isFull = false;
        boolean isTrójka = false;
        int pairs = 0;

        for (int count : counts) {
            if (count == 5) isPoker = true;
            if (count == 4) isKareta = true;
            if (count == 3) isTrójka = true;
            if (count == 2) pairs++;
        }
        isFull = (pairs == 1 && isTrójka);

        boolean isMałyStrit = checkStraight(dice, 1);
        boolean isDużyStrit = checkStraight(dice, 2);

        if (isPoker) return "Poker";
        if (isKareta) return "Kareta";
        if (isFull) return "Full";
        if (isMałyStrit) return "Mały Strit";
        if (isDużyStrit) return "Duży Strit";
        if (isTrójka) return "Trójka";
        if (pairs == 2) return "Dwie Pary";
        if (pairs == 1) return "Para";

        // Zwracamy najwyższą liczbę oczek, jeśli nie ma żadnego układu
        int maxValue = Arrays.stream(dice).max().orElse(0);
        return "Najwyższa liczba oczek: " + maxValue;
    }

    public static int evaluateStrength(int[] dice) {
        int[] counts = new int[7];
        for (int die : dice) counts[die]++;

        boolean isPoker = false;
        boolean isKareta = false;
        boolean isFull = false;
        boolean isTrójka = false;
        int pairs = 0;

        for (int count : counts) {
            if (count == 5) isPoker = true;
            if (count == 4) isKareta = true;
            if (count == 3) isTrójka = true;
            if (count == 2) pairs++;
        }
        isFull = (pairs == 1 && isTrójka);

        boolean isMałyStrit = checkStraight(dice, 1);
        boolean isDużyStrit = checkStraight(dice, 2);

        if (isPoker) return 8;
        if (isKareta) return 7;
        if (isFull) return 6;
        if (isDużyStrit) return 5;
        if (isMałyStrit) return 4;
        if (isTrójka) return 3;
        if (pairs == 2) return 2;
        if (pairs == 1) return 1;

        return 0; // tylko najwyższa kość
    }

    private static boolean checkStraight(int[] dice, int start) {
        int[] sorted = dice.clone();
        Arrays.sort(sorted);
        for (int i = 0; i < 5; i++) {
            if (sorted[i] != start + i) return false;
        }
        return true;
    }
}
