package main.java;

public interface Player {
    String getName();
    int[] getDice();
    int getMoney();
    void addMoney(int amount);
    void subtractMoney(int amount);
    void firstThrow(FirstThrow firstThrow);
    void secondThrow(SecondThrow secondThrow);
}
