package main.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== KOŚCIANY POKER ===");
        System.out.println("1. Gra z drugim graczem");
        System.out.println("2. Gra kontra komputer (wkrótce)");
        System.out.print("Wybierz tryb gry (1-2): ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                Game.playTwoPlayers();
                break;
            case 2:
                System.out.println("Ten tryb gry nie jest jeszcze dostępny.");
                break;
            default:
                System.out.println("Nieprawidłowy wybór.");
        }

        scanner.close();
    }
}
