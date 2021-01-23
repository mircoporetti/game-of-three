package me.mircoporetti.gameofthree.rabbitmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.PlayerPlaysHisGame;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class GameMessageConsumer {

    private final RabbitMessageMapper rabbitMessageMapper;
    private final PlayerPlaysHisGame playerPlaysHisGame;

    public GameMessageConsumer(RabbitMessageMapper rabbitMessageMapper, PlayerPlaysHisGame playerPlaysHisGame) {
        this.rabbitMessageMapper = rabbitMessageMapper;
        this.playerPlaysHisGame = playerPlaysHisGame;
    }

    @RabbitListener(queues = "${game-of-three.player-name}")
    public void listenToAPlay(Message message) {
        try{
            GameMessage opponentMessage = rabbitMessageMapper.toGameOfThreeMessage(message);
            playerPlaysHisGame.invoke(new Game(opponentMessage.getMove()));
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem during the conversion of: " + message);
            e.printStackTrace();
        }
    }
}
