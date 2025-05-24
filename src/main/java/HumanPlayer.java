package main.java;

public class HumanPlayer extends AbstractPlayer {

    public HumanPlayer(String name, int startingMoney) {
        super(name, startingMoney);
    }

    @Override
    public void secondThrow(SecondThrow secondThrow) {
        System.out.println("\n" + name + " wykonuje drugi rzut:");
        dice = secondThrow.reroll(dice);
    }
}
