package me.mircoporetti.gameofthree.rabbitmq.message;

import me.mircoporetti.gameofthree.domain.turn.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class GameProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    private GameMessageProducer underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new GameMessageProducer(rabbitTemplate);
    }

    @Test
    void notifyGame() {
        Game gameToBeNotified = new Game(30);

        underTest.notifyGameToTheOpponent(gameToBeNotified);

        verify(rabbitTemplate).convertAndSend("player1", "{\"move\":30}");
    }
}