package me.mircoporetti.gameofthree.rabbitmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.rabbitmq.message.GameOfThreeMessage;
import me.mircoporetti.gameofthree.rabbitmq.message.GameOfThreeMessageMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameOfThreeMessageMapperTest {

    private GameOfThreeMessageMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new GameOfThreeMessageMapper(new ObjectMapper());
    }

    @Test
    void toGameOfThreeMessage() throws JsonProcessingException {
        String givenJson = "{\"play\": 60}";
        Message givenMessage = new Message(givenJson.getBytes(StandardCharsets.UTF_8), new MessageProperties());

        GameOfThreeMessage result = underTest.toGameOfThreeMessage(givenMessage);

        assertThat(result, is(new GameOfThreeMessage(60)));
    }

    @Test
    void cannotProcessingJson() {
        String givenJson = "{play: aWrongFormattedMessage}";
        Message givenMessage = new Message(givenJson.getBytes(StandardCharsets.UTF_8), new MessageProperties());

        assertThrows(JsonProcessingException.class, () -> underTest.toGameOfThreeMessage(givenMessage));
    }
}