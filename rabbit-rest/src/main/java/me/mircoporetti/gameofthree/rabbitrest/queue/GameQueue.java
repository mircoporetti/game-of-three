package me.mircoporetti.gameofthree.rabbitrest.queue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameQueue implements Serializable {

    @JsonProperty(value = "messages")
    public Integer numberOfMessages;

    @Override
    public String toString() {
        return "GameQueue{" +
                "numberOfMessages=" + numberOfMessages +
                '}';
    }
}
