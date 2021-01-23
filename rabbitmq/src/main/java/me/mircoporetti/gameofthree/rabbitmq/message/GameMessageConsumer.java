package me.mircoporetti.gameofthree.rabbitmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.turn.Game;
import me.mircoporetti.gameofthree.domain.turn.PlayerPlaysHisGame;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameMessageConsumer {

    private final GameOfThreeMessageMapper gameOfThreeMessageMapper;
    private final PlayerPlaysHisGame playerPlaysHisGame;

    @Autowired
    public GameMessageConsumer(GameOfThreeMessageMapper gameOfThreeMessageMapper, PlayerPlaysHisGame playerPlaysHisGame) {
        this.gameOfThreeMessageMapper = gameOfThreeMessageMapper;
        this.playerPlaysHisGame = playerPlaysHisGame;
    }

    @RabbitListener(queues = "${game-of-three.player-name}")
    public void listenToAPlay(Message message) {
        try{
            GameMessage opponentMessage = gameOfThreeMessageMapper.toGameOfThreeMessage(message);
            playerPlaysHisGame.invoke(new Game(opponentMessage.getMove()));
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem during the conversion of: " + message);
            e.printStackTrace();
        }
    }
}
