package me.mircoporetti.gameofthree.rabbitamqp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.domain.game.*;
import me.mircoporetti.gameofthree.rabbitamqp.game.RabbitGameConsumer;
import me.mircoporetti.gameofthree.rabbitamqp.game.RabbitGameProducer;
import me.mircoporetti.gameofthree.rabbitamqp.game.RabbitGameMapper;
import me.mircoporetti.gameofthree.rabbitrest.queue.RabbitQueueRestRepository;
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

    @Value("${game-of-three.rabbitmq.username}")
    private String rabbitUsername;

    @Value("${game-of-three.rabbitmq.password}")
    private String rabbitPassword;

    @Value("${game-of-three.rabbitmq.queues-url}")
    private String rabbitUrl;

    @Bean
    public Queue playerQueue() {
        return new Queue(playerName, false);
    }

    @Bean
    public Queue opponentQueue() {
        return new Queue(opponentName, false);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    }

    @Bean
    public RabbitGameConsumer gameMessageConsumer(RabbitGameMapper rabbitGameMapper, PlayGameUseCase playerPlaysHisGame, StartToPlayUseCase playerStartsToPlay){
        return new RabbitGameConsumer(rabbitGameMapper, playerPlaysHisGame, playerStartsToPlay, playerName, opponentName);
    }

    @Bean
    public GameNotificationPort gameNotificationPort(RabbitGameMapper rabbitGameMapper, RabbitTemplate rabbitTemplate){
        return new RabbitGameProducer(rabbitGameMapper, rabbitTemplate);
    }

    @Bean
    public PlayGameUseCase playerPlaysHisGame(GameNotificationPort gamePostman){
        return new PlayerPlaysHisGame(gamePostman);
    }

    @Bean
    QueueRepositoryPort gameRepositoryPort(){
        return new RabbitQueueRestRepository(rabbitUsername, rabbitPassword, rabbitUrl);
    }

    @Bean
    public StartToPlayUseCase playerStartsToPlay(QueueRepositoryPort queueRepositoryPort, GameNotificationPort gameNotificationPort){
        return new PlayerStartsToPlay(queueRepositoryPort, gameNotificationPort);
    }

    @Bean
    public RabbitGameMapper rabbitMessageMapper(ObjectMapper mapper){
        return new RabbitGameMapper(mapper);
    }
}
