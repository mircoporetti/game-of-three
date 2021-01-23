package me.mircoporetti.gameofthree.rabbitmq.message;

import java.util.Objects;

public class GameOfThreeMessage {

    private Integer play;

    public GameOfThreeMessage() {
    }

    public GameOfThreeMessage(Integer play) {
        this.play = play;
    }

    public Integer getPlay() {
        return play;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameOfThreeMessage gameOfThreeMessage = (GameOfThreeMessage) o;
        return Objects.equals(play, gameOfThreeMessage.play);
    }

    @Override
    public int hashCode() {
        return Objects.hash(play);
    }
}
