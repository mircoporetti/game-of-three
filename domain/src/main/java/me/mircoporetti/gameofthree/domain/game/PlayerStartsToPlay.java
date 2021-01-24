package me.mircoporetti.gameofthree.domain.game;

import java.util.Random;

public class PlayerStartsToPlay implements StartToPlayUseCase {

    private final QueueRepositoryPort queueRepositoryPort;
    private final GameNotificationPort gameNotificationPort;

    public PlayerStartsToPlay(QueueRepositoryPort queueRepositoryPort, GameNotificationPort gameNotificationPort) {
        this.queueRepositoryPort = queueRepositoryPort;
        this.gameNotificationPort = gameNotificationPort;
    }

    @Override
    public void invoke(String playerName, String opponentName) {
        Integer numberOfOpponentGames = queueRepositoryPort.getNumberOfMessagesIn(playerName);

        if(numberOfOpponentGames == 0) {
            Random random = new Random();
            int randomMove = Math.abs(random.nextInt());
            gameNotificationPort.notifyGameToTheOpponent(new Game(randomMove), opponentName);
        }
    }
}
