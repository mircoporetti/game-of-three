package me.mircoporetti.gameofthree.domain.game.port;

public interface GameOfThreeConsolePort {
    Integer readGameOperand();

    void print(String line);
}
