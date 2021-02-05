package me.mircoporetti.gameofthree.spring.application;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.console.SystemConsole;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsole;
import me.mircoporetti.gameofthree.domain.game.port.QueueRepositoryPort;
import me.mircoporetti.gameofthree.domain.game.usecase.*;
import me.mircoporetti.gameofthree.rabbitmq.events.game.GameEventConsumer;
import me.mircoporetti.gameofthree.rabbitmq.events.game.GameEventProducer;
import me.mircoporetti.gameofthree.rabbitmq.events.game.GameEventMapper;
import me.mircoporetti.gameofthree.rabbitmq.events.player.Player;
import me.mircoporetti.gameofthree.rabbitmq.events.player.PlayerMode;
import me.mircoporetti.gameofthree.rabbitmq.rest.queue.QueueRestRepository;
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
    private String mode;

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
    public GameOfThreeConsole console(){
        return new SystemConsole();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
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
    public PlayerMode gameOfThreeMode(){
        if (mode.equals("MANUAL")) return PlayerMode.MANUAL;
        else return PlayerMode.AUTO;
    }

    @Bean
    public Player playerGameConfiguration(PlayerMode playerMode, StartToPlayUseCase startToPlayUseCase){
        return new Player(playerName, opponentName, playerMode, startToPlayUseCase);
    }


    @Bean
    public GameEventConsumer gameMessageConsumer(GameEventMapper gameEventMapper, PlayTurnAutomaticallyUseCase playerPlaysHisGame, PlayTurnManuallyUseCase playerPlaysHisGameManually, StartToPlayUseCase playerStartsToPlay, Player player){
        return new GameEventConsumer(gameEventMapper, playerPlaysHisGame, playerPlaysHisGameManually, player);
    }

    @Bean
    public StartToPlayUseCase playerStartsToPlay(QueueRepositoryPort queueRepositoryPort, GameNotificationPort gameNotificationPort, GameOfThreeConsole console){
        return new PlayerStartsToPlay(queueRepositoryPort, gameNotificationPort, console);
    }

    @Bean
    public PlayTurnAutomaticallyUseCase playerPlaysHisGameAutomatically(GameNotificationPort gamePostman, GameOfThreeConsole console){
        return new PlayerPlaysHisTurnAutomatically(gamePostman, console);
    }

    @Bean
    public PlayTurnManuallyUseCase playerPlaysHisGameManually(GameNotificationPort gamePostman, GameOfThreeConsole console){
        return new PlayerPlaysHisTurnManually(gamePostman, console);
    }

    @Bean
    QueueRepositoryPort gameRepositoryPort(RestTemplate restTemplate){
        return new QueueRestRepository(restTemplate, rabbitUsername, rabbitPassword, rabbitUrl);
    }

    @Bean
    public GameNotificationPort gameNotificationPort(GameEventMapper gameEventMapper, RabbitTemplate rabbitTemplate){
        return new GameEventProducer(gameEventMapper, rabbitTemplate);
    }

    @Bean
    public GameEventMapper rabbitMessageMapper(ObjectMapper mapper){
        return new GameEventMapper(mapper);
    }
}
