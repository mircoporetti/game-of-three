package me.mircoporetti.gameofthree.rabbitmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Component
public class GameOfThreeMessageMapper {

    private final ObjectMapper mapper;

    public GameOfThreeMessageMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public GameMessage toGameOfThreeMessage(Message message) throws JsonProcessingException {
            String messageToBeConverted = new String(message.getBody());
            return mapper.readValue(messageToBeConverted, GameMessage.class);
    }
}
