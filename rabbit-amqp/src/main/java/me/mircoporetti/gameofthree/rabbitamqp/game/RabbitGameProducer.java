package me.mircoporetti.gameofthree.rabbitamqp.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitGameProducer implements GameNotificationPort {

    private final RabbitGameMapper mapper;
    private final RabbitTemplate rabbitTemplate;

    public RabbitGameProducer(RabbitGameMapper mapper, RabbitTemplate rabbitTemplate) {
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notifyGameToTheOpponent(Game gameToBeNotified, String opponentQueue) {
        try {
            String jsonGame = mapper.toJsonMessage(gameToBeNotified);
            rabbitTemplate.convertAndSend(opponentQueue, jsonGame);
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem during the conversion of: " + gameToBeNotified);
            e.printStackTrace();
        }
    }
}
