package me.mircoporetti.gameofthree.rabbitamqp.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class GameProducerTest {

    @Mock
    private RabbitGameMapper mapper;
    @Mock
    private RabbitTemplate rabbitTemplate;

    private String opponentName;

    private RabbitGameProducer underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        opponentName = "anOpponentName";
        underTest = new RabbitGameProducer(mapper, rabbitTemplate);
    }

    @Test
    void notifyGame() throws JsonProcessingException {

        String messageToBeSent = "{\"move\":something}";
        doReturn(messageToBeSent).when(mapper).toJsonMessage(any());

        underTest.notifyGameToTheOpponent(new Game(30),opponentName);

        verify(rabbitTemplate).convertAndSend("anOpponentName", messageToBeSent);
    }
}