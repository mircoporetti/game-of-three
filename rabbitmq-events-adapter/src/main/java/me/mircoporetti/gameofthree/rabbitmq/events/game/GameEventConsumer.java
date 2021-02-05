package me.mircoporetti.gameofthree.rabbitmq.events.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayTurnAutomaticallyUseCase;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayTurnManuallyUseCase;
import me.mircoporetti.gameofthree.rabbitmq.events.player.Player;
import me.mircoporetti.gameofthree.rabbitmq.events.player.PlayerMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class GameEventConsumer {

    private final GameEventMapper gameEventMapper;
    private final PlayTurnAutomaticallyUseCase playerPlaysHisTurnAutomatically;
    private final PlayTurnManuallyUseCase playerPlaysHisTurnManually;
    private final Player player;


    public GameEventConsumer(GameEventMapper gameEventMapper, PlayTurnAutomaticallyUseCase playerPlaysHisTurnAutomatically, PlayTurnManuallyUseCase playerPlaysHisTurnManually, Player player) {

        this.gameEventMapper = gameEventMapper;
        this.playerPlaysHisTurnAutomatically = playerPlaysHisTurnAutomatically;
        this.playerPlaysHisTurnManually = playerPlaysHisTurnManually;
        this.player = player;
    }

    @RabbitListener(queues = "${game-of-three.player-name}")
    public void consumeOpponentGame(Message message) {
        try{
            GameEvent opponentMessage = gameEventMapper.toGameOfThreeMessage(message);
            if(player.getPlayerMode() == PlayerMode.MANUAL){
                playerPlaysHisTurnManually.invoke(new Game(opponentMessage.getMove()), player.getOpponentName());
            } else {
                playerPlaysHisTurnAutomatically.invoke(new Game(opponentMessage.getMove()), player.getOpponentName());
            }
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem parsing the following message: " + message);
            e.printStackTrace();
        }
    }
}
