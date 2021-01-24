package me.mircoporetti.gameofthree.domain.game.port;

import me.mircoporetti.gameofthree.domain.game.Game;

public interface GameOfThreeConsole {
    Game read();

    void print(String line);
}
