
package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
public class PokerGUI_Stol_Styled {

    private JFrame frame;
    private JLabel messageLabel;
    private JButton[] diceButtons;
    private boolean[] selectedDice;
    private int[] currentDice;
    private int rollCount = 0;
    private JButton rollButton, endTurnButton, newGameButton;
    private int[] player1Dice = new int[5];
    private int[] player2Dice = new int[5];
    private String player1Name, player2Name;
    private int player1Money, player2Money;
    private int totalRounds, currentRound = 1;
    private int pula = 0;
    private int aktualnaStawka = 0;
    private int currentBet = 0;
    //to nie wiadomo czy sie przyda
    private JLabel player1Info;
    private JLabel player2Info;
    private JTextArea logArea;

    private int currentPlayer = 1;

    public PokerGUI_Stol_Styled() {
        frame = new JFrame("Kościany Poker - Stół");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);


        player1Name = JOptionPane.showInputDialog(frame, "Podaj imię Gracza 1:");
        if (player1Name == null || player1Name.isBlank()) player1Name = "Gracz 1";

        player2Name = JOptionPane.showInputDialog(frame, "Podaj imię Gracza 2:");
        if (player2Name == null || player2Name.isBlank()) player2Name = "Gracz 2";

        String rundyStr = JOptionPane.showInputDialog(frame, "Podaj liczbę rund (np. 5):");
        try {
            totalRounds = Integer.parseInt(rundyStr);
            if (totalRounds <= 0) totalRounds = 5;
        } catch (Exception e) {
            totalRounds = 5;
        }

        String kwotaStr = JOptionPane.showInputDialog(frame, "Podaj początkową kwotę pieniędzy dla każdego gracza:");
        try {
            player1Money = player2Money = Integer.parseInt(kwotaStr);
            if (player1Money <= 0) player1Money = player2Money = 1000;
        } catch (Exception e) {
            player1Money = player2Money = 1000;
        }


        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.setBackground(new Color(0, 100, 0)); // Zielony stół

        // Gracz 1 - lewa strona
        JPanel player1Panel = new JPanel();
        player1Panel.setPreferredSize(new Dimension(200, 600));
        player1Panel.setBackground(new Color(10, 30, 10));
        player1Panel.setLayout(new BorderLayout());
        player1Info = new JLabel("<html><h2 style='color:white;'>" + player1Name + " (" + player1Money + " zł)</h2></html>", SwingConstants.CENTER);
        player1Panel.add(player1Info, BorderLayout.NORTH);
        pane.add(player1Panel, BorderLayout.WEST);

        // Gracz 2 - prawa strona
        JPanel player2Panel = new JPanel();
        player2Panel.setPreferredSize(new Dimension(200, 600));
        player2Panel.setBackground(new Color(10, 30, 10));
        player2Panel.setLayout(new BorderLayout());
        player2Info = new JLabel("<html><h2 style='color:white;'>" + player2Name + " (" + player2Money + " zł)</h2></html>", SwingConstants.CENTER);
        player2Panel.add(player2Info, BorderLayout.NORTH);
        pane.add(player2Panel, BorderLayout.EAST);

        // Środek - stół
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(0, 100, 0));
        centerPanel.setLayout(new BorderLayout());

        // Kości
        JPanel dicePanel = new JPanel();
        dicePanel.setBackground(new Color(0, 100, 0));
        diceButtons = new JButton[5];
        selectedDice = new boolean[5];
        currentDice = new int[5];
        for (int i = 0; i < 5; i++) {
            int idx = i;
            JButton btn = new JButton("🎲");
            btn.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 36));
            btn.setBackground(Color.WHITE);
            btn.addActionListener(e -> toggleDie(idx));
            diceButtons[i] = btn;
            dicePanel.add(btn);
        }
        centerPanel.add(dicePanel, BorderLayout.NORTH);

        // Komunikaty
        messageLabel = new JLabel("Witaj w Kościanym Pokerze!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        messageLabel.setForeground(Color.WHITE);
        centerPanel.add(messageLabel, BorderLayout.CENTER);

        // Log gry
        logArea = new JTextArea(5, 30);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        pane.add(centerPanel, BorderLayout.CENTER);

        // Dół – przyciski
        JPanel controlPanel = new JPanel();
        rollButton = new JButton("Rzuć kośćmi");
        rollButton.addActionListener(e -> rollDice());
        endTurnButton = new JButton("Zakończ turę");
        endTurnButton.setEnabled(false);
        endTurnButton.addActionListener(e -> endTurn());
        newGameButton = new JButton("Nowa gra");
        newGameButton.addActionListener(e -> resetGame());

        controlPanel.add(rollButton);
        controlPanel.add(endTurnButton);
        controlPanel.add(newGameButton);
        pane.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        startNewRound();

    }

    private void toggleDie(int idx) {
        if (rollCount == 0) return;
        selectedDice[idx] = !selectedDice[idx];
        diceButtons[idx].setBackground(selectedDice[idx] ? Color.YELLOW : Color.WHITE);
    }

    private void rollDice() {
        for (int i = 0; i < 5; i++) {
            if (rollCount == 0 || selectedDice[i]) {
                currentDice[i] = (int)(Math.random() * 6) + 1;
            }
            diceButtons[i].setText(diceEmoji(currentDice[i]));
        }
        rollCount++;
        endTurnButton.setEnabled(true);
        if (rollCount >= 2) {
            rollButton.setEnabled(false);
        }
        logArea.append("Gracz " + currentPlayer + " rzuca: " + Arrays.toString(currentDice) + "\n");
    }

    private void startNewRound() {
        if (currentRound > totalRounds) {
            messageLabel.setText("Gra zakończona – rozegrano wszystkie rundy.");
            logArea.append("🎯 Gra zakończona – rozegrano " + totalRounds + " rund.\n");
            rollButton.setEnabled(false);
            endTurnButton.setEnabled(false);
            return;
        }

        // Sprawdzenie czy gracze mają wystarczająco środków
        if (player1Money <= 0 || player2Money <= 0) {
            messageLabel.setText("Gra zakończona – któryś z graczy nie ma pieniędzy.");
            logArea.append("❌ Gra zakończona – brak środków u jednego z graczy.\n");
            rollButton.setEnabled(false);
            endTurnButton.setEnabled(false);
            return;
        }

        // Zapytaj o stawkę
        String input = JOptionPane.showInputDialog(frame,
                "Runda " + currentRound + "\n" + player1Name + " i " + player2Name + ", podajcie stawkę tej rundy (liczba całkowita):",
                "Stawka", JOptionPane.QUESTION_MESSAGE);

        if (input == null || input.trim().isEmpty()) {
            currentBet = 10;
        } else {
            try {
                currentBet = Integer.parseInt(input.trim());
                if (currentBet <= 0) currentBet = 10;
            } catch (NumberFormatException e) {
                currentBet = 10;
            }
        }

        // Sprawdź jeszcze raz środki na tę stawkę
        if (player1Money < currentBet || player2Money < currentBet) {
            JOptionPane.showMessageDialog(frame, "Któryś z graczy nie ma wystarczających środków na stawkę " + currentBet + " zł.");
            currentBet = 0;
            rollButton.setEnabled(false);
            endTurnButton.setEnabled(false);
            return;
        }

        // Potrąć i ustaw pulę
        player1Money -= currentBet;
        player2Money -= currentBet;
        pula = currentBet * 2;

        // Reset kości i tury
        resetRoll();
        currentPlayer = 1;
        rollButton.setEnabled(true);
        endTurnButton.setEnabled(false);

        messageLabel.setText("Runda " + currentRound + " – Tura " + player1Name);
        logArea.append("▶️ Runda " + currentRound + " się rozpoczęła. Stawka: " + currentBet + " zł od gracza. Pula: " + pula + " zł.\n");

        updateMoneyLabels();
        updatePlayerLabels();
    }


    private void endTurn() {
        logArea.append("Gracz " + currentPlayer + " zakończył turę.\n");

        if (currentPlayer == 1) {
            System.arraycopy(currentDice, 0, player1Dice, 0, 5);
            currentPlayer = 2;
            resetRoll();
            messageLabel.setText("Tura " + player2Name);
        } else {
            System.arraycopy(currentDice, 0, player2Dice, 0, 5);

            // KONIEC RUNDY – porównanie wyników
            ScoreEvaluator.HandDetail d1 = ScoreEvaluator.evaluateDetailed(player1Dice);
            ScoreEvaluator.HandDetail d2 = ScoreEvaluator.evaluateDetailed(player2Dice);

            int cmp = d1.compareTo(d2);
            String wynik;
            if (cmp > 0) wynik = "Wygrał " + player2Name + "!";
            else if (cmp < 0) wynik = "Wygrał " + player1Name + "!";
            else wynik = "Remis!";

            if (cmp > 0) {
                player2Money += pula;
                logArea.append("🏆 " + player2Name + " wygrał " + pula + " zł!\n");
            } else if (cmp < 0) {
                player1Money += pula;
                logArea.append("🏆 " + player1Name + " wygrał " + pula + " zł!\n");
            } else {
                int zwrot = pula / 2;
                player1Money += zwrot;
                player2Money += zwrot;
                logArea.append("🤝 Remis! Obaj gracze otrzymują po " + zwrot + " zł.\n");
            }

            updateMoneyLabels();

            String układ1 = ScoreEvaluator.evaluate(player1Dice);
            String układ2 = ScoreEvaluator.evaluate(player2Dice);

            logArea.append(player1Name + ": " + Arrays.toString(player1Dice) + " – " + układ1 + "\n");
            logArea.append(player2Name + ": " + Arrays.toString(player2Dice) + " – " + układ2 + "\n");
            logArea.append("Wynik: " + wynik + "\n");

            messageLabel.setText("Runda zakończona – " + wynik);
            rollButton.setEnabled(false);
            endTurnButton.setEnabled(false);

            // Tu kluczowa zmiana – currentRound++ dopiero PO sprawdzeniu!
            if (currentRound >= totalRounds) {
                logArea.append("\nGra zakończona po " + totalRounds + " rundach.\n");
                String winner;
                if (player1Money > player2Money) winner = player1Name + " WYGRYWA! 🎉";
                else if (player2Money > player1Money) winner = player2Name + " WYGRYWA! 🎉";
                else winner = "REMIS końcowy! 🤝";

                logArea.append("💰 " + player1Name + ": " + player1Money + " zł\n");
                logArea.append("💰 " + player2Name + ": " + player2Money + " zł\n");
                logArea.append("🏁 " + winner + "\n");
            } else {
                currentRound++;
                startNewRound();
            }
        }
    }



    private void resetRoll() {
        rollCount = 0;
        selectedDice = new boolean[5];
        rollButton.setEnabled(true);
        for (int i = 0; i < 5; i++) {
            diceButtons[i].setText("🎲");
            diceButtons[i].setBackground(Color.WHITE);
        }
    }

    private void resetGame() {
        // Zapytaj o początkową kwotę pieniędzy od nowa
        String kwotaStr = JOptionPane.showInputDialog(frame, "Podaj początkową kwotę pieniędzy dla każdego gracza:");

        try {
            player1Money = player2Money = Integer.parseInt(kwotaStr);
            if (player1Money <= 0) player1Money = player2Money = 100;
        } catch (Exception e) {
            player1Money = player2Money = 100;
        }

        // Zapytaj o liczbę rund
        String rundyStr = JOptionPane.showInputDialog(frame, "Podaj liczbę rund (np. 5):");
        try {
            totalRounds = Integer.parseInt(rundyStr);
            if (totalRounds <= 0) totalRounds = 5;
        } catch (Exception e) {
            totalRounds = 5;
        }

        currentRound = 1;
        logArea.setText("");
        updatePlayerLabels(); // od razu zaktualizuj etykiety z kwotami
        startNewRound();
    }







    private String diceEmoji(int val) {
        return switch (val) {
            case 1 -> "⚀";
            case 2 -> "⚁";
            case 3 -> "⚂";
            case 4 -> "⚃";
            case 5 -> "⚄";
            case 6 -> "⚅";
            default -> "🎲";
        };
    }
    //to zobaczymy czy sie przyda
    private void updateMoneyLabels() {
        player1Info.setText(player1Name + " 💸: " + player1Money + " zł");
        player2Info.setText(player2Name + " 💸: " + player2Money + " zł");
    }

    private void updatePlayerLabels() {
        player1Info.setText("<html><h2 style='color:white;'>" + player1Name + "<br>💸 " + player1Money + " zł</h2></html>");
        player2Info.setText("<html><h2 style='color:white;'>" + player2Name + "<br>💸 " + player2Money + " zł</h2></html>");
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(PokerGUI_Stol_Styled::new);
    }
}
