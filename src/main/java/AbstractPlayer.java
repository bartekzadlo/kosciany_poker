package main.java;

public abstract class AbstractPlayer implements Player {
    protected String name;
    protected int[] dice = new int[5];
    protected int money;

    public AbstractPlayer(String name, int startingMoney) {
        this.name = name;
        this.money = startingMoney;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int[] getDice() {
        return dice;
    }

    @Override
    public int getMoney() {
        return money;
    }

    @Override
    public void addMoney(int amount) {
        money += amount;
    }

    @Override
    public void subtractMoney(int amount) {
        money -= amount;
    }

    @Override
    public void firstThrow(FirstThrow firstThrow) {
        System.out.println("\n" + name + " wykonuje pierwszy rzut:");
        dice = firstThrow.roll();
    }

    @Override
    public abstract void secondThrow(SecondThrow secondThrow);
}
