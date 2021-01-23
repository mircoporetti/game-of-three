package me.mircoporetti.gameofthree.rabbitmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.turn.PlayerPlaysHisTurn;
import me.mircoporetti.gameofthree.domain.turn.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class GameOfThreeMessageConsumerTest {

    @Mock
    private PlayerPlaysHisTurn playerPlaysHisTurn;
    @Mock
    private GameOfThreeMessageMapper gameOfThreeMessageMapper;

    private MessageConsumer underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new MessageConsumer(gameOfThreeMessageMapper, playerPlaysHisTurn);
    }

    @Test
    void receiveAMessage_playYourTurn() throws JsonProcessingException {
        Message anyGivenMessage = new Message("".getBytes(), new MessageProperties());

        doReturn(new GameOfThreeMessage(60)).when(gameOfThreeMessageMapper).toGameOfThreeMessage(any());

        underTest.listenToAPlay(anyGivenMessage);

        verify(playerPlaysHisTurn).invoke(new Turn(60));
    }

    @Test
    void cannotReceiveOpponentMessage_dontPlayYourTurn() throws JsonProcessingException {
        Message anyGivenMessage = new Message("".getBytes(), new MessageProperties());

        doThrow(JsonProcessingException.class).when(gameOfThreeMessageMapper).toGameOfThreeMessage(any());

        underTest.listenToAPlay(anyGivenMessage);

        verify(playerPlaysHisTurn, never()).invoke(any());
    }
}