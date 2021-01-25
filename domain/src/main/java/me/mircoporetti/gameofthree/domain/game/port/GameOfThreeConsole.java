package me.mircoporetti.gameofthree.domain.game.port;

public interface GameOfThreeConsole {
    Integer readGameOperand();

    void print(String line);
}
