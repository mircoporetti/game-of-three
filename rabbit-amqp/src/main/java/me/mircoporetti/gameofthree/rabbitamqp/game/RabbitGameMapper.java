package me.mircoporetti.gameofthree.rabbitamqp.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.domain.game.Game;
import org.springframework.amqp.core.Message;

public class RabbitGameMapper {

    private final ObjectMapper mapper;

    public RabbitGameMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public RabbitGame toGameOfThreeMessage(Message message) throws JsonProcessingException {
            String messageToBeConverted = new String(message.getBody());
            return mapper.readValue(messageToBeConverted, RabbitGame.class);
    }

    public String toJsonMessage(Game game) throws JsonProcessingException {
        return mapper.writeValueAsString(game);
    }
}
