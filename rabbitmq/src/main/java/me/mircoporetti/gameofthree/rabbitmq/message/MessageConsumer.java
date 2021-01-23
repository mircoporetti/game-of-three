package me.mircoporetti.gameofthree.rabbitmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.turn.PlayerPlaysHisTurn;
import me.mircoporetti.gameofthree.domain.turn.Turn;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final GameOfThreeMessageMapper gameOfThreeMessageMapper;
    private final PlayerPlaysHisTurn playerPlaysHisTurn;

    @Autowired
    public MessageConsumer(GameOfThreeMessageMapper gameOfThreeMessageMapper, PlayerPlaysHisTurn playerPlaysHisTurn) {
        this.gameOfThreeMessageMapper = gameOfThreeMessageMapper;
        this.playerPlaysHisTurn = playerPlaysHisTurn;
    }

    @RabbitListener(queues = "player1")
    public void listenToAPlay(Message message) {
        try{
            GameOfThreeMessage opponentMessage = gameOfThreeMessageMapper.toGameOfThreeMessage(message);
            playerPlaysHisTurn.invoke(new Turn(opponentMessage.getPlay()));
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem during the conversion of: " + message);
            e.printStackTrace();
        }
    }
}
