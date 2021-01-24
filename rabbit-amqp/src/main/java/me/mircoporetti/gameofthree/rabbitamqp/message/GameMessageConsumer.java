package me.mircoporetti.gameofthree.rabbitamqp.message;

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
    private final String playerName;
    private final String opponentName;

    public GameMessageConsumer(RabbitMessageMapper rabbitMessageMapper, PlayGameUseCase playerPlaysHisGame, StartToPlayUseCase playerStartsToPlay, String playerName, String opponentName) {
        this.rabbitMessageMapper = rabbitMessageMapper;
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
            GameMessage opponentMessage = rabbitMessageMapper.toGameOfThreeMessage(message);
            playerPlaysHisGame.invoke(new Game(opponentMessage.getMove()), opponentName);
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem during the conversion of: " + message);
            e.printStackTrace();
        }
    }
}
