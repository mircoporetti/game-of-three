package me.mircoporetti.gameofthree.domain.game;

import java.util.Random;

public class PlayerStartsToPlay implements StartToPlayUseCase {

    private final GameRepositoryPort gameRepositoryPort;
    private final GameNotificationPort gameNotificationPort;

    public PlayerStartsToPlay(GameRepositoryPort gameRepositoryPort, GameNotificationPort gameNotificationPort) {
        this.gameRepositoryPort = gameRepositoryPort;
        this.gameNotificationPort = gameNotificationPort;
    }

    @Override
    public void invoke() {
        Integer numberOfOpponentGames = gameRepositoryPort.getNumberOfOpponentGamesFromMyQueue();

        if(numberOfOpponentGames == 0) {
            Random random = new Random();
            int randomMove = Math.abs(random.nextInt());
            gameNotificationPort.notifyGameToTheOpponent(new Game(randomMove));
        }
    }
}
