package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.QueueRepositoryPort;

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
        Integer numberOfOpponentGames;
        try {
            numberOfOpponentGames = queueRepositoryPort.getNumberOfMessagesIn(playerName);
            if(numberOfOpponentGames == 0) {
                Random random = new Random();
                int randomMove = Math.abs(random.nextInt());
                gameNotificationPort.notifyGameToTheOpponent(new Game(randomMove), opponentName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
