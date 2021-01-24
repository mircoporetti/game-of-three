package me.mircoporetti.gameofthree.rabbitamqp.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.GameNotificationPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class GameMessageProducer implements GameNotificationPort {

    private final RabbitMessageMapper mapper;
    private final RabbitTemplate rabbitTemplate;
    private final String opponentName;

    public GameMessageProducer(RabbitMessageMapper mapper, RabbitTemplate rabbitTemplate, String opponentName) {
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
        this.opponentName = opponentName;
    }

    @Override
    public void notifyGameToTheOpponent(Game gameToBeNotified) {
        System.out.println("Game to be notified: " + gameToBeNotified);
        try {
            String jsonGame = mapper.toJsonMessage(gameToBeNotified);
            rabbitTemplate.convertAndSend(opponentName, jsonGame);
        } catch (JsonProcessingException e) {
            System.out.println("There was a problem during the conversion of: " + gameToBeNotified);
            e.printStackTrace();
        }
    }
}
