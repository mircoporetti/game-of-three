package me.mircoporetti.gameofthree.rabbitmq;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.domain.game.*;
import me.mircoporetti.gameofthree.rabbitmq.message.GameMessageConsumer;
import me.mircoporetti.gameofthree.rabbitmq.message.GameMessageProducer;
import me.mircoporetti.gameofthree.rabbitmq.message.RabbitMessageMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Value("${game-of-three.player-name}")
    private String playerName;

    @Value("${game-of-three.opponent-name}")
    private String opponentName;


    @Bean
    public Queue playerQueue() {
        return new Queue(playerName, false);
    }

    @Bean
    public Queue opponentQueue() {
        return new Queue(opponentName, false);
    }

    @Bean
    public Queue gameStartedQueue() {
        return new Queue("startedQueue", false);
    }


    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    }

    @Bean
    public GameMessageConsumer gameMessageConsumer(RabbitMessageMapper rabbitMessageMapper, PlayGameUseCase playerPlaysHisGame,  StartToPlayUseCase playerStartsToPlay){
        return new GameMessageConsumer(rabbitMessageMapper, playerPlaysHisGame, playerStartsToPlay);
    }

    @Bean
    public GameNotificationPort gameNotificationPort(RabbitMessageMapper rabbitMessageMapper, RabbitTemplate rabbitTemplate){
        return new GameMessageProducer(rabbitMessageMapper, rabbitTemplate, opponentName);
    }

    @Bean
    public PlayGameUseCase playerPlaysHisGame(GameNotificationPort gamePostman){
        return new PlayerPlaysHisGame(gamePostman);
    }

    @Bean
    public StartToPlayUseCase playerStartsToPlay(){
        return new PlayerStartsToPlay();
    }

    @Bean
    public RabbitMessageMapper rabbitMessageMapper(ObjectMapper mapper){
        return new RabbitMessageMapper(mapper);
    }
}
