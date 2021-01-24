package me.mircoporetti.gameofthree.rabbitamqp.message;

import java.util.Objects;

public class GameMessage {

    private Integer move;

    public GameMessage() {
    }

    public GameMessage(Integer move) {
        this.move = move;
    }

    public Integer getMove() {
        return move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameMessage gameMessage = (GameMessage) o;
        return Objects.equals(move, gameMessage.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(move);
    }
}
