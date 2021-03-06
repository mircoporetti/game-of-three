package me.mircoporetti.gameofthree.rabbitmq.events.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.domain.game.Game;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameEventMapperTest {

    private GameEventMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new GameEventMapper(new ObjectMapper());
    }

    @Test
    void toGameOfThreeMessage() throws JsonProcessingException {
        String givenJson = "{\"move\": 60}";
        Message givenMessage = new Message(givenJson.getBytes(StandardCharsets.UTF_8), new MessageProperties());

        GameEvent result = underTest.toGameOfThreeMessage(givenMessage);

        assertThat(result, CoreMatchers.is(new GameEvent(60)));
    }

    @Test
    void cannotProcessingJson() {
        String givenJson = "{play: aWrongFormattedMessage}";
        Message givenMessage = new Message(givenJson.getBytes(StandardCharsets.UTF_8), new MessageProperties());

        assertThrows(JsonProcessingException.class, () -> underTest.toGameOfThreeMessage(givenMessage));
    }

    @Test
    void toJsonMessage() throws JsonProcessingException {
        Game givenGame = new Game(60);

        String result = underTest.toJsonMessage(givenGame);

        assertThat(result, is("{\"move\":60}"));
    }
}
