package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;

public class PlayerPlaysHisGame implements PlayGameUseCase {

    private final GameNotificationPort gameNotificationPort;

    public PlayerPlaysHisGame(GameNotificationPort gameNotificationPort) {
        this.gameNotificationPort = gameNotificationPort;
    }

    public void invoke(Game opponentGame, String opponentName) {

        Game nextGame = opponentGame.calculateNextGame();

        if(nextGame.checkIfPlayable())
            gameNotificationPort.notifyGameToTheOpponent(nextGame, opponentName);
        else
            System.out.println("YOU WIN!");
    }
}
