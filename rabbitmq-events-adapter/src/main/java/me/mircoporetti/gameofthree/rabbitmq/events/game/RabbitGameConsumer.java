package me.mircoporetti.gameofthree.rabbitmq.events.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayGameAutomaticallyUseCase;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayGameManuallyUseCase;
import me.mircoporetti.gameofthree.rabbitmq.events.player.Player;
import me.mircoporetti.gameofthree.rabbitmq.events.player.PlayerMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RabbitGameConsumer {

    private final RabbitGameMapper rabbitGameMapper;
    private final PlayGameAutomaticallyUseCase playerPlaysHisGameAutomatically;
    private final PlayGameManuallyUseCase playerPlaysHisGameManually;
    private final Player player;


    public RabbitGameConsumer(RabbitGameMapper rabbitGameMapper, PlayGameAutomaticallyUseCase playerPlaysHisGameAutomatically, PlayGameManuallyUseCase playerPlaysHisGameManually, Player player) {

        this.rabbitGameMapper = rabbitGameMapper;
        this.playerPlaysHisGameAutomatically = playerPlaysHisGameAutomatically;
        this.playerPlaysHisGameManually = playerPlaysHisGameManually;
        this.player = player;
    }

    @RabbitListener(queues = "${game-of-three.player-name}")
    public void consumeOpponentGame(Message message) {
        try{
            RabbitGame opponentMessage = rabbitGameMapper.toGameOfThreeMessage(message);
            if(player.getPlayerMode() == PlayerMode.MANUAL){
                playerPlaysHisGameManually.invoke(new Game(opponentMessage.getMove()), player.getOpponentName());
            } else {
                playerPlaysHisGameAutomatically.invoke(new Game(opponentMessage.getMove()), player.getOpponentName());
            }
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem parsing the following message: " + message);
            e.printStackTrace();
        }
    }
}
