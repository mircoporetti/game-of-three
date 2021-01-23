package me.mircoporetti.gameofthree.domain.turn;

public class PlayerPlaysHisGame {

    private final GameNotificationPort gameNotificationPort;

    public PlayerPlaysHisGame(GameNotificationPort gameNotificationPort) {
        this.gameNotificationPort = gameNotificationPort;
    }

    public void invoke(Game opponentGame) {

        Game nextGame = opponentGame.calculateNextGame();

        if(nextGame.checkIfPlayable())
            gameNotificationPort.notifyGameToTheOpponent(nextGame);
    }
}
