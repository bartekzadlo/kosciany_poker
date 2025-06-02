package main.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== KOŚCIANY POKER ===");
            System.out.println("1. Gra z drugim graczem");
            System.out.println("2. Gra kontra komputer (wkrótce)");
            System.out.println("3. Zakończ");
            System.out.print("Wybierz tryb gry (1-3): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Game.playTwoPlayers(); // obsłuży własne zapytanie o kontynuację
                    break;
                case 2:
                    System.out.println("Ten tryb gry nie jest jeszcze dostępny.");
                    break;
                case 3:
                    running = false;
                    System.out.println("Do zobaczenia!");
                    break;
                default:
                    System.out.println("Nieprawidłowy wybór.");
            }
        }

        scanner.close();
    }
}