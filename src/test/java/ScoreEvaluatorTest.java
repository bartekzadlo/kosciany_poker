package test.java;

import static org.junit.jupiter.api.Assertions.*;

import main.java.ScoreEvaluator;
import org.junit.jupiter.api.Test;

public class ScoreEvaluatorTest {

    @Test
    public void testEvaluatePairComparison() {
        int[] dice1 = {3, 6, 4, 6, 1}; // para 6
        int[] dice2 = {5, 3, 1, 2, 1}; // para 1

        ScoreEvaluator.HandDetail hand1 = ScoreEvaluator.evaluateDetailed(dice1);
        ScoreEvaluator.HandDetail hand2 = ScoreEvaluator.evaluateDetailed(dice2);

        // Para 6 powinna wygrać nad parą 1
        assertTrue(hand1.compareTo(hand2) < 0, "Para 6 powinna być silniejsza niż para 1");
    }

    @Test
    public void testEvaluateTieBreaker() {
        int[] dice1 = {3, 6, 4, 6, 1}; // para 6, tiebreakery: 4,3,1
        int[] dice2 = {6, 3, 4, 1, 1}; // para 1, tiebreakery: 6,4,3

        ScoreEvaluator.HandDetail hand1 = ScoreEvaluator.evaluateDetailed(dice1);
        ScoreEvaluator.HandDetail hand2 = ScoreEvaluator.evaluateDetailed(dice2);

        // Para 6 wygrywa mimo, że tiebreakery mogą być różne
        assertTrue(hand1.compareTo(hand2) < 0);
    }

    @Test
    public void testEvaluateFullHouse() {
        int[] dice1 = {2, 2, 3, 3, 3}; // full house 3 i 2
        int[] dice2 = {4, 4, 4, 1, 1}; // full house 4 i 1

        ScoreEvaluator.HandDetail hand1 = ScoreEvaluator.evaluateDetailed(dice1);
        ScoreEvaluator.HandDetail hand2 = ScoreEvaluator.evaluateDetailed(dice2);

        // full house z trójkami 4 wygrywa nad trójkami 3
        assertTrue(hand2.compareTo(hand1) < 0);
    }

    @Test
    public void testEvaluateFullHouse2() {
        int[] dice1 = {2, 2, 3, 3, 3}; // full house 3 i 2
        int[] dice2 = {3, 3, 3, 4, 4}; // full house 3 i 4

        ScoreEvaluator.HandDetail hand1 = ScoreEvaluator.evaluateDetailed(dice1);
        ScoreEvaluator.HandDetail hand2 = ScoreEvaluator.evaluateDetailed(dice2);

        // full house z parami 4 wygrywa nad parami 2
        assertTrue(hand2.compareTo(hand1) < 0);
    }

    @Test
    public void testEvaluateHighCard() {
        int[] dice1 = {1, 3, 6, 2, 4}; // najwyższa 6
        int[] dice2 = {1, 3, 4, 2, 2}; // para 2

        ScoreEvaluator.HandDetail hand1 = ScoreEvaluator.evaluateDetailed(dice1);
        ScoreEvaluator.HandDetail hand2 = ScoreEvaluator.evaluateDetailed(dice2);

        // para wygrywa nad najwyższą kartą
        assertTrue(hand2.compareTo(hand1) < 0);
    }

    @Test
    public void testEvaluateDifferentThreeOfAKind() {
        // Trójka 5 z dodatkowymi kartami 1 i 2
        int[] dice1 = {5, 5, 5, 1, 2};
        // Trójka 3 z dodatkowymi kartami 6 i 2
        int[] dice2 = {3, 3, 3, 6, 2};

        ScoreEvaluator.HandDetail hand1 = ScoreEvaluator.evaluateDetailed(dice1);
        ScoreEvaluator.HandDetail hand2 = ScoreEvaluator.evaluateDetailed(dice2);

        // Trójka 5 powinna wygrać nad trójką 3
        assertTrue(hand1.compareTo(hand2) < 0, "Trójka 5 powinna być silniejsza niż trójka 3");
    }

    @Test
    public void testEvaluateThreeOfAKindWithTieBreakers() {
        // Trójka 3 z dodatkowymi kartami 6 i 2
        int[] dice3 = {3, 3, 3, 6, 2};
        // Trójka 3 z dodatkowymi kartami 6 i 4
        int[] dice4 = {3, 3, 3, 6, 4};

        ScoreEvaluator.HandDetail hand3 = ScoreEvaluator.evaluateDetailed(dice3);
        ScoreEvaluator.HandDetail hand4 = ScoreEvaluator.evaluateDetailed(dice4);

        // Przy równej trójce wygrywa ta z wyższym tiebreakerem (4 > 2)
        assertTrue(hand4.compareTo(hand3) < 0, "Przy równej trójce wygrywa wyższy tiebreaker");
    }

    @Test
    public void testCompareFourOfAKindHands() {
        int[] fourSixes = {6, 6, 6, 6, 2}; // kareta (4 of a kind) szóstek
        int[] fourFives = {5, 5, 5, 5, 3}; // kareta (4 of a kind) piątek

        ScoreEvaluator.HandDetail handSixes = ScoreEvaluator.evaluateDetailed(fourSixes);
        ScoreEvaluator.HandDetail handFives = ScoreEvaluator.evaluateDetailed(fourFives);

        assertTrue(handSixes.compareTo(handFives) < 0, "Kareta z 6 powinna wygrać nad karetą z 5");
    }

    @Test
    public void testCompareFiveOfAKindHands() {
        int[] fiveSixes = {6, 6, 6, 6, 6}; // poker (5 of a kind) szóstek
        int[] fiveFives = {5, 5, 5, 5, 5}; // poker (5 of a kind) piątek

        ScoreEvaluator.HandDetail handSixes = ScoreEvaluator.evaluateDetailed(fiveSixes);
        ScoreEvaluator.HandDetail handFives = ScoreEvaluator.evaluateDetailed(fiveFives);

        assertTrue(handSixes.compareTo(handFives) < 0, "Poker z 6 powinien wygrać nad pokerem z 5");
    }
}