package me.mircoporetti.gameofthree.rabbitamqp.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.GameNotificationPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class GameMessageProducer implements GameNotificationPort {

    private final RabbitMessageMapper mapper;
    private final RabbitTemplate rabbitTemplate;

    public GameMessageProducer(RabbitMessageMapper mapper, RabbitTemplate rabbitTemplate) {
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notifyGameToTheOpponent(Game gameToBeNotified, String opponentName) {
        try {
            String jsonGame = mapper.toJsonMessage(gameToBeNotified);
            rabbitTemplate.convertAndSend(opponentName, jsonGame);
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem during the conversion of: " + gameToBeNotified);
            e.printStackTrace();
        }
    }
}
