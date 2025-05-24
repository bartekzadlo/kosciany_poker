package main.java;

public class Player {
    private String name;
    private int[] dice = new int[5];
    private int money;

    public Player(String name, int startingMoney) {
        this.name = name;
        this.money = startingMoney;
    }

    public String getName() {
        return name;
    }

    public int[] getDice() {
        return dice;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public void subtractMoney(int amount) {
        money -= amount;
    }

    public void firstThrow(FirstThrow firstThrow) {
        System.out.println("\n" + name + " wykonuje pierwszy rzut:");
        dice = firstThrow.roll();
    }

    public void secondThrow(SecondThrow secondThrow) {
        System.out.println("\n" + name + " wykonuje drugi rzut:");
        dice = secondThrow.reroll(dice);
    }
}
