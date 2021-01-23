package me.mircoporetti.gameofthree.rabbitmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.turn.Game;
import me.mircoporetti.gameofthree.domain.turn.PlayerPlaysHisGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class GameGameMessageConsumerTest {

    @Mock
    private PlayerPlaysHisGame playerPlaysHisGame;
    @Mock
    private GameOfThreeMessageMapper gameOfThreeMessageMapper;

    private GameMessageConsumer underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new GameMessageConsumer(gameOfThreeMessageMapper, playerPlaysHisGame);
    }

    @Test
    void receiveAMessage_playYourTurn() throws JsonProcessingException {
        Message anyGivenMessage = new Message("".getBytes(), new MessageProperties());

        doReturn(new GameMessage(60)).when(gameOfThreeMessageMapper).toGameOfThreeMessage(any());

        underTest.listenToAPlay(anyGivenMessage);

        verify(playerPlaysHisGame).invoke(new Game(60));
    }

    @Test
    void cannotReceiveOpponentMessage_dontPlayYourTurn() throws JsonProcessingException {
        Message anyGivenMessage = new Message("".getBytes(), new MessageProperties());

        doThrow(JsonProcessingException.class).when(gameOfThreeMessageMapper).toGameOfThreeMessage(any());

        underTest.listenToAPlay(anyGivenMessage);

        verify(playerPlaysHisGame, never()).invoke(any());
    }
}