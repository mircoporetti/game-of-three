package me.mircoporetti.gameofthree.rabbitrest.queue;

import me.mircoporetti.gameofthree.domain.game.QueueRepositoryPort;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class RabbitQueueRestRepository  implements QueueRepositoryPort {

    private final RestTemplate restTemplate;
    private final String rabbitUsername;
    private final String rabbitPassword;
    private final String url;

    public RabbitQueueRestRepository(RestTemplate restTemplate, String rabbitUsername, String rabbitPassword, String url) {
        this.restTemplate = restTemplate;
        this.rabbitUsername = rabbitUsername;
        this.rabbitPassword = rabbitPassword;
        this.url = url;
    }

    public Integer getNumberOfMessagesIn(String queueName) throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders(rabbitUsername, rabbitPassword));

        try{
            ResponseEntity<RabbitQueue> result = restTemplate.exchange(url + "/" + queueName, HttpMethod.GET,entity, RabbitQueue.class);
            return Objects.requireNonNull(result.getBody()).numberOfMessages;
        }catch (HttpClientErrorException e){
            throw new Exception("Queue: " + queueName + "does not exist");
        }

    }

    private HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
