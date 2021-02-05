package me.mircoporetti.gameofthree.rabbitmq.events.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayTurnManuallyUseCase;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayTurnAutomaticallyUseCase;
import me.mircoporetti.gameofthree.domain.game.usecase.StartToPlayUseCase;
import me.mircoporetti.gameofthree.rabbitmq.events.player.Player;
import me.mircoporetti.gameofthree.rabbitmq.events.player.PlayerMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class GameEventConsumerTest {

    @Mock
    private PlayTurnAutomaticallyUseCase playerPlaysHisGame;
    @Mock
    private PlayTurnManuallyUseCase playerPlaysHisGameManually;
    @Mock
    private StartToPlayUseCase playerStartsToPlay;
    @Mock
    private GameEventMapper gameEventMapper;

    private String playerName;
    private String opponentName;

    private GameEventConsumer underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        playerName = "aName";
        opponentName = "anOpponentNAme";
        underTest = new GameEventConsumer(gameEventMapper, playerPlaysHisGame, playerPlaysHisGameManually, new Player(playerName, opponentName, PlayerMode.AUTO, playerStartsToPlay));
    }

    @Test
    void receiveAMessage_playYourAutomaticTurn() throws JsonProcessingException {
        Message anyGivenMessage = new Message("".getBytes(), new MessageProperties());

        doReturn(new GameEvent(60)).when(gameEventMapper).toGameOfThreeMessage(any());

        underTest.consumeOpponentGame(anyGivenMessage);

        verify(playerPlaysHisGame).invoke(new Game(60),opponentName);
        verify(playerPlaysHisGameManually, never()).invoke(new Game(60),opponentName);
    }

    @Test
    void receiveAMessage_playYourManualTurn() throws JsonProcessingException {
        Message anyGivenMessage = new Message("".getBytes(), new MessageProperties());

        doReturn(new GameEvent(60)).when(gameEventMapper).toGameOfThreeMessage(any());

        underTest = new GameEventConsumer(gameEventMapper, playerPlaysHisGame, playerPlaysHisGameManually, new Player(playerName, opponentName, PlayerMode.MANUAL, playerStartsToPlay));
        underTest.consumeOpponentGame(anyGivenMessage);

        verify(playerPlaysHisGame, never()).invoke(new Game(60),opponentName);
        verify(playerPlaysHisGameManually).invoke(new Game(60),opponentName);
    }

    @Test
    void cannotReceiveOpponentMessage_dontPlayYourTurn() throws JsonProcessingException {
        Message anyGivenMessage = new Message("".getBytes(), new MessageProperties());

        doThrow(JsonProcessingException.class).when(gameEventMapper).toGameOfThreeMessage(any());

        underTest.consumeOpponentGame(anyGivenMessage);

        verify(playerPlaysHisGame, never()).invoke(any(), any());
    }
}
