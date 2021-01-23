package me.mircoporetti.gameofthree.rabbitmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.domain.turn.Game;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    private final ObjectMapper mapper;

    public MessageMapper(ObjectMapper mapper) {
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
