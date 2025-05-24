package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CautiousAIPlayer extends AbstractPlayer {

    public CautiousAIPlayer(String name, int startingMoney) {
        super(name, startingMoney);
    }

    @Override
    public void secondThrow(SecondThrow secondThrow) {
        ScoreEvaluator.HandDetail hand = ScoreEvaluator.evaluateDetailed(dice);

        int strength = hand.strength;
        int primary = hand.primaryValue;

        // Zbierz kości należące do głównego układu (np. para, trójka itp.)
        // oraz przygotuj listę indeksów do przerzutu
        boolean[] keep = new boolean[5];

        int[] counts = new int[7];
        for (int die : dice) counts[die]++;

        switch (strength) {
            case 1: // Para
                // Zachowaj wszystkie kości z wartością pary, przerzuć resztę
                for (int i = 0; i < dice.length; i++) {
                    keep[i] = (dice[i] == primary);
                }
                break;

            case 2: // Dwie Pary
                // Znajdź dwie pary (wartości)
                int firstPair = 0, secondPair = 0;
                for (int i = 6; i >= 1; i--) {
                    if (counts[i] == 2) {
                        if (firstPair == 0) firstPair = i;
                        else if (secondPair == 0) secondPair = i;
                    }
                }
                // Zachowaj tylko kości należące do par, przerzuć pozostałe (1 kość)
                for (int i = 0; i < dice.length; i++) {
                    int val = dice[i];
                    keep[i] = (val == firstPair || val == secondPair);
                }
                break;

            case 3: // Trójka
                // Zachowaj kości trójki, przerzuć pozostałe
                for (int i = 0; i < dice.length; i++) {
                    keep[i] = (dice[i] == primary);
                }
                break;

            case 6: // Full House (trójka + para)
                // Zachowaj trójkę, przerzuć parę (próba ulepszenia do karety lub pokera)
                // Najpierw znajdź wartość pary
                int pairVal = 0;
                for (int i = 6; i >= 1; i--) {
                    if (counts[i] == 2) {
                        pairVal = i;
                        break;
                    }
                }
                for (int i = 0; i < dice.length; i++) {
                    int val = dice[i];
                    keep[i] = (val == primary); // trójka zostaje
                    if (val == pairVal) keep[i] = false; // para przerzucana
                }
                break;

            case 7: // Kareta
                // Zachowaj karete, przerzuć 5-tą kość (nie należącą do karety)
                for (int i = 0; i < dice.length; i++) {
                    keep[i] = (dice[i] == primary);
                }
                break;

            case 8: // Poker (5 jednakowych)
                // Przerzuć wszystko, licząc na poker królewski (5 szóstek)
                // czyli chcemy tylko szóstki
                for (int i = 0; i < dice.length; i++) {
                    keep[i] = (dice[i] == 6);
                }
                break;

            case 4: // Mały strit
                // Przerzucamy wszystkie kości, licząc na lepszy układ
                for (int i = 0; i < dice.length; i++) {
                    keep[i] = false;
                }
                break;
            case 5: // Duży strit
                // Przerzucamy wszystkie kości, licząc na lepszy układ
                for (int i = 0; i < dice.length; i++) {
                    keep[i] = false;
                }
                break;

            case 0: // Brak układu
            default:
                // Zachowaj najwyższą kość, przerzuć resztę
                int max = Arrays.stream(dice).max().orElse(0);
                for (int i = 0; i < dice.length; i++) {
                    keep[i] = (dice[i] == max);
                }
                break;
        }

        // Zbierz indeksy do przerzutu
        int countReroll = 0;
        for (boolean b : keep) if (!b) countReroll++;
        int[] toReroll = new int[countReroll];
        int idx = 0;
        for (int i = 0; i < keep.length; i++) {
            if (!keep[i]) {
                toReroll[idx++] = i;
            }
        }

        dice = secondThrow.reroll(toReroll);
    }
}
