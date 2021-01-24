package me.mircoporetti.gameofthree.rabbitmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.PlayGameUseCase;
import me.mircoporetti.gameofthree.domain.game.StartToPlayUseCase;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import javax.annotation.PostConstruct;

public class GameMessageConsumer {

    private final RabbitMessageMapper rabbitMessageMapper;
    private final PlayGameUseCase playerPlaysHisGame;
    private final StartToPlayUseCase playerStartsToPlay;

    public GameMessageConsumer(RabbitMessageMapper rabbitMessageMapper, PlayGameUseCase playerPlaysHisGame, StartToPlayUseCase playerStartsToPlay) {
        this.rabbitMessageMapper = rabbitMessageMapper;
        this.playerPlaysHisGame = playerPlaysHisGame;
        this.playerStartsToPlay = playerStartsToPlay;
    }

    @PostConstruct
    public void startToPlay(){
        playerStartsToPlay.invoke();
    }

    @RabbitListener(queues = "${game-of-three.player-name}")
    public void consumeOpponentGame(Message message) {
        try{
            GameMessage opponentMessage = rabbitMessageMapper.toGameOfThreeMessage(message);
            playerPlaysHisGame.invoke(new Game(opponentMessage.getMove()));
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem during the conversion of: " + message);
            e.printStackTrace();
        }
    }
}
