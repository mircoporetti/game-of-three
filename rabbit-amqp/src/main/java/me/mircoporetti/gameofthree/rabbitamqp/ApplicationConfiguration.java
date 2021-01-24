package me.mircoporetti.gameofthree.rabbitamqp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.domain.game.*;
import me.mircoporetti.gameofthree.rabbitamqp.message.GameMessageConsumer;
import me.mircoporetti.gameofthree.rabbitamqp.message.GameMessageProducer;
import me.mircoporetti.gameofthree.rabbitamqp.message.RabbitMessageMapper;
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
    public GameMessageConsumer gameMessageConsumer(RabbitMessageMapper rabbitMessageMapper, PlayGameUseCase playerPlaysHisGame,  StartToPlayUseCase playerStartsToPlay){
        return new GameMessageConsumer(rabbitMessageMapper, playerPlaysHisGame, playerStartsToPlay, playerName, opponentName);
    }

    @Bean
    public GameNotificationPort gameNotificationPort(RabbitMessageMapper rabbitMessageMapper, RabbitTemplate rabbitTemplate){
        return new GameMessageProducer(rabbitMessageMapper, rabbitTemplate);
    }

    @Bean
    public PlayGameUseCase playerPlaysHisGame(GameNotificationPort gamePostman){
        return new PlayerPlaysHisGame(gamePostman);
    }


    @Bean
    QueueRepositoryPort gameRepositoryPort(){
        return new RabbitQueueRestRepository(rabbitUsername, rabbitPassword, rabbitUrl, playerName);
    }

    @Bean
    public StartToPlayUseCase playerStartsToPlay(QueueRepositoryPort queueRepositoryPort, GameNotificationPort gameNotificationPort){
        return new PlayerStartsToPlay(queueRepositoryPort, gameNotificationPort);
    }

    @Bean
    public RabbitMessageMapper rabbitMessageMapper(ObjectMapper mapper){
        return new RabbitMessageMapper(mapper);
    }
}
