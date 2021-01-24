package me.mircoporetti.gameofthree.domain.game;

public class PlayerPlaysHisGame implements PlayGameUseCase {

    private final GameNotificationPort gameNotificationPort;

    public PlayerPlaysHisGame(GameNotificationPort gameNotificationPort) {
        this.gameNotificationPort = gameNotificationPort;
    }

    public void invoke(Game opponentGame, String opponentName) {

        Game nextGame = opponentGame.calculateNextGame();

        if(nextGame.checkIfPlayable())
            gameNotificationPort.notifyGameToTheOpponent(nextGame, opponentName);
    }
}
