package me.mircoporetti.gameofthree.domain.game;

public class PlayGameHisGame implements PlayGameUseCase {

    private final GameNotificationPort gameNotificationPort;

    public PlayGameHisGame(GameNotificationPort gameNotificationPort) {
        this.gameNotificationPort = gameNotificationPort;
    }

    public void invoke(Game opponentGame) {

        Game nextGame = opponentGame.calculateNextGame();

        if(nextGame.checkIfPlayable())
            gameNotificationPort.notifyGameToTheOpponent(nextGame);
    }
}
