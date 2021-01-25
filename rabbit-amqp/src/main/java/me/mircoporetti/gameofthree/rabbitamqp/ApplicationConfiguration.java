package me.mircoporetti.gameofthree.rabbitamqp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.console.SystemConsole;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsole;
import me.mircoporetti.gameofthree.domain.game.port.QueueRepositoryPort;
import me.mircoporetti.gameofthree.domain.game.usecase.*;
import me.mircoporetti.gameofthree.rabbitamqp.game.RabbitGameConsumer;
import me.mircoporetti.gameofthree.rabbitamqp.game.RabbitGameProducer;
import me.mircoporetti.gameofthree.rabbitamqp.game.RabbitGameMapper;
import me.mircoporetti.gameofthree.rabbitrest.queue.RabbitQueueRestRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class ApplicationConfiguration {

    @Value("${game-of-three.player-name}")
    private String playerName;

    @Value("${game-of-three.opponent-name}")
    private String opponentName;

    @Value("${game-of-three.mode}")
    private String gameOfThreeMode;

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
    public RabbitGameConsumer gameMessageConsumer(RabbitGameMapper rabbitGameMapper, PlayGameAutomaticallyUseCase playerPlaysHisGame, PlayGameManuallyUseCase playerPlaysHisGameManually, StartToPlayUseCase playerStartsToPlay){
        return new RabbitGameConsumer(rabbitGameMapper, playerPlaysHisGame, playerPlaysHisGameManually, playerStartsToPlay, playerName, opponentName, gameOfThreeMode);
    }

    @Bean
    public GameNotificationPort gameNotificationPort(RabbitGameMapper rabbitGameMapper, RabbitTemplate rabbitTemplate){
        return new RabbitGameProducer(rabbitGameMapper, rabbitTemplate);
    }

    @Bean
    public GameOfThreeConsole console(){
        return new SystemConsole();
    }

    @Bean
    public PlayGameAutomaticallyUseCase playerPlaysHisGameAutomatically(GameNotificationPort gamePostman, GameOfThreeConsole console){
        return new PlayerPlaysHisGameAutomatically(gamePostman, console);
    }

    @Bean
    public PlayGameManuallyUseCase playerPlaysHisGameManually(GameNotificationPort gamePostman, GameOfThreeConsole console){
        return new PlayerPlaysHisGameManually(gamePostman, console);
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
        return restTemplate;
    }

    @Bean
    QueueRepositoryPort gameRepositoryPort(RestTemplate restTemplate){
        return new RabbitQueueRestRepository(restTemplate, rabbitUsername, rabbitPassword, rabbitUrl);
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
