package me.mircoporetti.gameofthree.rabbitmq;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mircoporetti.gameofthree.domain.turn.GamePostmanPort;
import me.mircoporetti.gameofthree.domain.turn.PlayerPlaysHisGame;
import me.mircoporetti.gameofthree.rabbitmq.message.GameProducer;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Queue playerQueue() {
        return new Queue("player1", false);
    }

    @Bean
    public GamePostmanPort gamePostmanPort(){
        return new GameProducer();
    }

    @Bean
    public PlayerPlaysHisGame playerPlaysHisTurn(GamePostmanPort gamePostman){
        return new PlayerPlaysHisGame(gamePostman);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    }
}
