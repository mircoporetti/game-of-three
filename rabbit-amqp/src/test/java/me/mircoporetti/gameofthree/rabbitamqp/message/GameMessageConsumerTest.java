package me.mircoporetti.gameofthree.rabbitamqp.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.PlayGameUseCase;
import me.mircoporetti.gameofthree.domain.game.StartToPlayUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class GameMessageConsumerTest {

    @Mock
    private PlayGameUseCase playerPlaysHisGame;
    @Mock
    private StartToPlayUseCase playerStartsToPlay;
    @Mock
    private RabbitMessageMapper rabbitMessageMapper;

    private GameMessageConsumer underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new GameMessageConsumer(rabbitMessageMapper, playerPlaysHisGame, playerStartsToPlay);
    }

    @Test
    void receiveAMessage_playYourTurn() throws JsonProcessingException {
        Message anyGivenMessage = new Message("".getBytes(), new MessageProperties());

        doReturn(new GameMessage(60)).when(rabbitMessageMapper).toGameOfThreeMessage(any());

        underTest.consumeOpponentGame(anyGivenMessage);

        verify(playerPlaysHisGame).invoke(new Game(60));
    }

    @Test
    void cannotReceiveOpponentMessage_dontPlayYourTurn() throws JsonProcessingException {
        Message anyGivenMessage = new Message("".getBytes(), new MessageProperties());

        doThrow(JsonProcessingException.class).when(rabbitMessageMapper).toGameOfThreeMessage(any());

        underTest.consumeOpponentGame(anyGivenMessage);

        verify(playerPlaysHisGame, never()).invoke(any());
    }

    @Test
    void playerStartToPlay() {

        underTest.startToPlay();

        verify(playerStartsToPlay).invoke();
    }
}