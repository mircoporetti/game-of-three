package me.mircoporetti.gameofthree.rabbitamqp.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.domain.game.Game;
import org.springframework.amqp.core.Message;

public class RabbitMessageMapper {

    private final ObjectMapper mapper;

    public RabbitMessageMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public GameMessage toGameOfThreeMessage(Message message) throws JsonProcessingException {
            String messageToBeConverted = new String(message.getBody());
            return mapper.readValue(messageToBeConverted, GameMessage.class);
    }

    public String toJsonMessage(Game game) throws JsonProcessingException {
        return mapper.writeValueAsString(game);
    }
}
