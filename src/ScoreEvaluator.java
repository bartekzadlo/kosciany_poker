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

    public static class HandDetail implements Comparable<HandDetail> {
        int strength;        // np. 2 = Dwie Pary, 3 = Trójka itd.
        int primaryValue;    // wartość głównego układu (np. para szóstek -> 6)
        int[] tiebreakers;   // pozostałe kości (malejąco)

        public HandDetail(int strength, int primaryValue, int[] tiebreakers) {
            this.strength = strength;
            this.primaryValue = primaryValue;
            this.tiebreakers = tiebreakers;
        }

        @Override
        public int compareTo(HandDetail o) {
            if (this.strength != o.strength) return Integer.compare(this.strength, o.strength);
            if (this.primaryValue != o.primaryValue) return Integer.compare(this.primaryValue, o.primaryValue);
            for (int i = 0; i < Math.min(this.tiebreakers.length, o.tiebreakers.length); i++) {
                if (this.tiebreakers[i] != o.tiebreakers[i])
                    return Integer.compare(this.tiebreakers[i], o.tiebreakers[i]);
            }
            return 0;
        }
    }

    public static HandDetail evaluateDetailed(int[] dice) {
        int[] counts = new int[7];
        for (int die : dice) counts[die]++;

        int strength = 0;
        int primary = 0;

        // Sprawdź kombinacje od najwyższych
        for (int i = 6; i >= 1; i--) {
            if (counts[i] == 5) { strength = 8; primary = i; break; } // Poker
            if (counts[i] == 4) { strength = 7; primary = i; break; } // Kareta
            if (counts[i] == 3) {
                for (int j = 6; j >= 1; j--) {
                    if (j != i && counts[j] >= 2) {
                        strength = 6; primary = i; break; // Full
                    }
                }
                if (strength == 0) {
                    strength = 3; primary = i; // Trójka
                }
                break;
            }
        }

        // Dwie pary / para
        if (strength == 0) {
            int firstPair = 0, secondPair = 0;
            for (int i = 6; i >= 1; i--) {
                if (counts[i] == 2) {
                    if (firstPair == 0) firstPair = i;
                    else if (secondPair == 0) secondPair = i;
                }
            }
            if (firstPair > 0 && secondPair > 0) {
                strength = 2;
                primary = Math.max(firstPair, secondPair);
            } else if (firstPair > 0) {
                strength = 1;
                primary = firstPair;
            }
        }

        // Strity
        if (strength == 0) {
            boolean small = checkStraight(dice, 1);
            boolean big = checkStraight(dice, 2);
            if (big) {
                strength = 5; primary = 5;
            } else if (small) {
                strength = 4; primary = 4;
            }
        }

        // Brak układu – najwyższa wartość
        if (strength == 0) {
            strength = 0;
            primary = Arrays.stream(dice).max().orElse(0);
        }

        // Tiebreakery – reszta oczek (pomijamy oczka układu, jeśli istotne)
        int[] tiebreakers = new int[5];
        int idx = 0;
        for (int i = 6; i >= 1; i--) {
            for (int j = 0; j < counts[i]; j++) {
                if (i != primary || strength == 0) {
                    tiebreakers[idx++] = i;
                    if (idx == 5) break;
                }
            }
            if (idx == 5) break;
        }

        return new HandDetail(strength, primary, Arrays.copyOf(tiebreakers, idx));
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
