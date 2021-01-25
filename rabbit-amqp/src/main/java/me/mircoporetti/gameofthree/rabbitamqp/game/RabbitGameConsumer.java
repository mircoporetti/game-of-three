package me.mircoporetti.gameofthree.rabbitamqp.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayGameManuallyUseCase;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayGameAutomaticallyUseCase;
import me.mircoporetti.gameofthree.domain.game.usecase.StartToPlayUseCase;
import me.mircoporetti.gameofthree.rabbitamqp.PlayerMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import javax.annotation.PostConstruct;

public class RabbitGameConsumer {

    private final RabbitGameMapper rabbitGameMapper;
    private final PlayGameAutomaticallyUseCase playerPlaysHisGameAutomatically;
    private final PlayGameManuallyUseCase playerPlaysHisGameManually;
    private final StartToPlayUseCase playerStartsToPlay;
    private final String playerName;
    private final String opponentName;
    private final PlayerMode playerMode;

    public RabbitGameConsumer(RabbitGameMapper rabbitGameMapper, PlayGameAutomaticallyUseCase playerPlaysHisGameAutomatically, PlayGameManuallyUseCase playerPlaysHisGameManually, StartToPlayUseCase playerStartsToPlay, String playerName, String opponentName, PlayerMode playerMode) {
        this.rabbitGameMapper = rabbitGameMapper;
        this.playerPlaysHisGameAutomatically = playerPlaysHisGameAutomatically;
        this.playerPlaysHisGameManually = playerPlaysHisGameManually;
        this.playerStartsToPlay = playerStartsToPlay;
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.playerMode = playerMode;
    }

    @PostConstruct
    public void startToPlay(){
        playerStartsToPlay.invoke(playerName, opponentName);
    }

    @RabbitListener(queues = "${game-of-three.player-name}")
    public void consumeOpponentGame(Message message) {
        try{
            RabbitGame opponentMessage = rabbitGameMapper.toGameOfThreeMessage(message);
            if(playerMode == PlayerMode.MANUAL){
                playerPlaysHisGameManually.invoke(new Game(opponentMessage.getMove()), opponentName);
            } else {
                playerPlaysHisGameAutomatically.invoke(new Game(opponentMessage.getMove()), opponentName);
            }
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem parsing the following message: " + message);
            e.printStackTrace();
        }
    }
}
