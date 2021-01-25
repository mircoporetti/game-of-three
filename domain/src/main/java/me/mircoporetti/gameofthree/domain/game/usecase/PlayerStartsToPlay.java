package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsole;
import me.mircoporetti.gameofthree.domain.game.port.QueueRepositoryPort;

import java.util.Random;

public class PlayerStartsToPlay implements StartToPlayUseCase {

    private final QueueRepositoryPort queueRepositoryPort;
    private final GameNotificationPort gameNotificationPort;
    private final GameOfThreeConsole console;

    public PlayerStartsToPlay(QueueRepositoryPort queueRepositoryPort, GameNotificationPort gameNotificationPort, GameOfThreeConsole console) {
        this.queueRepositoryPort = queueRepositoryPort;
        this.gameNotificationPort = gameNotificationPort;
        this.console = console;
    }

    @Override
    public void invoke(String playerName, String opponentName) {
        Integer numberOfOpponentGames;
        Integer numberOfPlayerGames;
        try {
            numberOfOpponentGames = queueRepositoryPort.getNumberOfMessagesIn(playerName);
            numberOfPlayerGames = queueRepositoryPort.getNumberOfMessagesIn(opponentName);
            if(numberOfOpponentGames == 0 && numberOfPlayerGames == 0) {
                Random random = new Random();
                int randomMove = Math.abs(random.nextInt());
                gameNotificationPort.notifyGameToTheOpponent(new Game(randomMove), opponentName);
            }
        } catch (Exception e) {
            console.print("Waiting for the opponent...");
        }

    }
}
