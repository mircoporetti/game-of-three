package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsole;

public class PlayerPlaysHisGameAutomatically implements PlayGameAutomaticallyUseCase {

    private final GameNotificationPort gameNotificationPort;
    private final GameOfThreeConsole gameOfThreeConsole;

    public PlayerPlaysHisGameAutomatically(GameNotificationPort gameNotificationPort, GameOfThreeConsole gameOfThreeConsole) {
        this.gameNotificationPort = gameNotificationPort;
        this.gameOfThreeConsole = gameOfThreeConsole;
    }

    public void invoke(Game opponentGame, String opponentName) {

        Game nextGame = opponentGame.calculateNextGame();

        if(nextGame.checkIfPlayable())
            gameNotificationPort.notifyGameToTheOpponent(nextGame, opponentName);
        else
           gameOfThreeConsole.print("WIN!!!");
    }
}