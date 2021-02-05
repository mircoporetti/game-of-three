package me.mircoporetti.gameofthree.rabbitmq.rest.queue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RabbitQueue implements Serializable {

    @JsonProperty(value = "messages")
    public Integer numberOfMessages;

    @Override
    public String toString() {
        return "RabbitQueue{" +
                "numberOfMessages=" + numberOfMessages +
                '}';
    }
}
