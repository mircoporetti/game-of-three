package me.mircoporetti.gameofthree.rabbitamqp.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.PlayGameUseCase;
import me.mircoporetti.gameofthree.domain.game.StartToPlayUseCase;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import javax.annotation.PostConstruct;

public class RabbitGameConsumer {

    private final RabbitGameMapper rabbitGameMapper;
    private final PlayGameUseCase playerPlaysHisGame;
    private final StartToPlayUseCase playerStartsToPlay;
    private final String playerName;
    private final String opponentName;

    public RabbitGameConsumer(RabbitGameMapper rabbitGameMapper, PlayGameUseCase playerPlaysHisGame, StartToPlayUseCase playerStartsToPlay, String playerName, String opponentName) {
        this.rabbitGameMapper = rabbitGameMapper;
        this.playerPlaysHisGame = playerPlaysHisGame;
        this.playerStartsToPlay = playerStartsToPlay;
        this.playerName = playerName;
        this.opponentName = opponentName;
    }

    @PostConstruct
    public void startToPlay(){
        playerStartsToPlay.invoke(playerName, opponentName);
    }

    @RabbitListener(queues = "${game-of-three.player-name}")
    public void consumeOpponentGame(Message message) {
        try{
            RabbitGame opponentMessage = rabbitGameMapper.toGameOfThreeMessage(message);
            playerPlaysHisGame.invoke(new Game(opponentMessage.getMove()), opponentName);
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem parsing the following message: " + message);
            e.printStackTrace();
        }
    }
}
