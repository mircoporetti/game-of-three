package me.mircoporetti.gameofthree.rabbitamqp.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayGameManuallyUseCase;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayGameAutomaticallyUseCase;
import me.mircoporetti.gameofthree.domain.game.usecase.StartToPlayUseCase;
import me.mircoporetti.gameofthree.rabbitamqp.PlayerConfiguration;
import me.mircoporetti.gameofthree.rabbitamqp.PlayerMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import javax.annotation.PostConstruct;

public class RabbitGameConsumer {

    private final RabbitGameMapper rabbitGameMapper;
    private final PlayGameAutomaticallyUseCase playerPlaysHisGameAutomatically;
    private final PlayGameManuallyUseCase playerPlaysHisGameManually;
    private final StartToPlayUseCase playerStartsToPlay;
    private final PlayerConfiguration playerConfiguration;


    public RabbitGameConsumer(RabbitGameMapper rabbitGameMapper, PlayGameAutomaticallyUseCase playerPlaysHisGameAutomatically, PlayGameManuallyUseCase playerPlaysHisGameManually, StartToPlayUseCase playerStartsToPlay, PlayerConfiguration playerConfiguration) {

        this.rabbitGameMapper = rabbitGameMapper;
        this.playerPlaysHisGameAutomatically = playerPlaysHisGameAutomatically;
        this.playerPlaysHisGameManually = playerPlaysHisGameManually;
        this.playerStartsToPlay = playerStartsToPlay;
        this.playerConfiguration = playerConfiguration;
    }

    @PostConstruct
    public void startToPlay(){
        playerStartsToPlay.invoke(playerConfiguration.getPlayerName(), playerConfiguration.getOpponentName());
    }

    @RabbitListener(queues = "${game-of-three.player-name}")
    public void consumeOpponentGame(Message message) {
        try{
            RabbitGame opponentMessage = rabbitGameMapper.toGameOfThreeMessage(message);
            if(playerConfiguration.getPlayerMode() == PlayerMode.MANUAL){
                playerPlaysHisGameManually.invoke(new Game(opponentMessage.getMove()), playerConfiguration.getOpponentName());
            } else {
                playerPlaysHisGameAutomatically.invoke(new Game(opponentMessage.getMove()), playerConfiguration.getOpponentName());
            }
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem parsing the following message: " + message);
            e.printStackTrace();
        }
    }
}
