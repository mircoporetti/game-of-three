package me.mircoporetti.gameofthree.rabbitmq.events.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.domain.game.Game;
import org.springframework.amqp.core.Message;

public class GameEventMapper {

    private final ObjectMapper mapper;

    public GameEventMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public GameEvent toGameOfThreeMessage(Message message) throws JsonProcessingException {
            String messageToBeConverted = new String(message.getBody());
            return mapper.readValue(messageToBeConverted, GameEvent.class);
    }

    public String toJsonMessage(Game game) throws JsonProcessingException {
        return mapper.writeValueAsString(game);
    }
}
