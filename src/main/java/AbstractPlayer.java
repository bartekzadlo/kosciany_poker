package main.java;

public abstract class AbstractPlayer implements Player {
    protected String name;
    protected int[] dice = new int[5];
    protected int money;

    public AbstractPlayer(String name, int startingMoney) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (startingMoney < 0) {
            throw new IllegalArgumentException("Starting money cannot be negative");
        }
        this.name = name;
        this.money = startingMoney;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int[] getDice() {
        // Zwracamy kopię, aby chronić wewnętrzny stan
        return dice.clone();
    }

    @Override
    public int getMoney() {
        return money;
    }

    @Override
    public void addMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to add cannot be negative");
        }
        money += amount;
    }

    @Override
    public void subtractMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to subtract cannot be negative");
        }
        if (money - amount < 0) {
            throw new IllegalStateException("Not enough money to subtract the requested amount");
        }
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
