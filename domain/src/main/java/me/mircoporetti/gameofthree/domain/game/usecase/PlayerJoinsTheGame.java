package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsolePort;
import me.mircoporetti.gameofthree.domain.game.port.QueueRepositoryPort;

import java.util.Random;

public class PlayerJoinsTheGame implements JoinTheGameUseCase {

    private final QueueRepositoryPort queueRepositoryPort;
    private final GameNotificationPort gameNotificationPort;
    private final GameOfThreeConsolePort console;

    public PlayerJoinsTheGame(QueueRepositoryPort queueRepositoryPort, GameNotificationPort gameNotificationPort, GameOfThreeConsolePort console) {
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
