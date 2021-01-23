package me.mircoporetti.gameofthree.rabbitmq.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.domain.turn.Game;
import me.mircoporetti.gameofthree.domain.turn.GamePostmanPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameMessageProducer implements GamePostmanPort {

    private final RabbitTemplate rabbitTemplate;

    public GameMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notifyGameToTheOpponent(Game gameToBeNotified) {
        System.out.println("Game to be notified: " + gameToBeNotified);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonGameMessage = mapper.writeValueAsString(gameToBeNotified);
            rabbitTemplate.convertAndSend("player1", jsonGameMessage);
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem during the conversion of: " + gameToBeNotified);
            e.printStackTrace();
        }


    }
}
