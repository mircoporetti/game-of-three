package me.mircoporetti.gameofthree.rabbitamqp.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayGameManuallyUseCase;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayGameAutomaticallyUseCase;
import me.mircoporetti.gameofthree.domain.game.usecase.StartToPlayUseCase;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import javax.annotation.PostConstruct;

public class RabbitGameConsumer {

    private final RabbitGameMapper rabbitGameMapper;
    private final PlayGameAutomaticallyUseCase playerPlaysHisGame;
    private final PlayGameManuallyUseCase playGameManuallyUseCase;
    private final StartToPlayUseCase playerStartsToPlay;
    private final String playerName;
    private final String opponentName;
    private final String gameOfThreeMode;

    public RabbitGameConsumer(RabbitGameMapper rabbitGameMapper, PlayGameAutomaticallyUseCase playerPlaysHisGame, PlayGameManuallyUseCase playGameManuallyUseCase, StartToPlayUseCase playerStartsToPlay, String playerName, String opponentName, String gameOfThreeMode) {
        this.rabbitGameMapper = rabbitGameMapper;
        this.playerPlaysHisGame = playerPlaysHisGame;
        this.playGameManuallyUseCase = playGameManuallyUseCase;
        this.playerStartsToPlay = playerStartsToPlay;
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.gameOfThreeMode = gameOfThreeMode;
    }

    @PostConstruct
    public void startToPlay(){
        playerStartsToPlay.invoke(playerName, opponentName);
    }

    @RabbitListener(queues = "${game-of-three.player-name}")
    public void consumeOpponentGame(Message message) {
        try{
            RabbitGame opponentMessage = rabbitGameMapper.toGameOfThreeMessage(message);
            if(gameOfThreeMode.equals("AUTO"))
                playerPlaysHisGame.invoke(new Game(opponentMessage.getMove()), opponentName);
            else
                playGameManuallyUseCase.invoke(new Game(opponentMessage.getMove()), opponentName);
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem parsing the following message: " + message);
            e.printStackTrace();
        }
    }
}
