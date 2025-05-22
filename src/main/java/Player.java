package main.java;

public class Player {
    private String name;
    private int[] dice = new int[5];

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int[] getDice() {
        return dice;
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
