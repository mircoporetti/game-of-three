package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsolePort;

public class PlayerPlaysHisTurnAutomatically implements PlayTurnAutomaticallyUseCase {

    private final GameNotificationPort gameNotificationPort;
    private final GameOfThreeConsolePort gameOfThreeConsolePort;

    public PlayerPlaysHisTurnAutomatically(GameNotificationPort gameNotificationPort, GameOfThreeConsolePort gameOfThreeConsolePort) {
        this.gameNotificationPort = gameNotificationPort;
        this.gameOfThreeConsolePort = gameOfThreeConsolePort;
    }

    public void invoke(Game opponentGame, String opponentName) {

        Game nextGame = opponentGame.calculateNextGame();

        if(nextGame.checkIfPlayable())
            gameNotificationPort.notifyGameToTheOpponent(nextGame, opponentName);
        else
           gameOfThreeConsolePort.print("WIN!!!");
    }
}
