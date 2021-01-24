package me.mircoporetti.gameofthree.rabbitamqp.game;

import java.util.Objects;

public class RabbitGame {

    private Integer move;

    public RabbitGame() {
    }

    public RabbitGame(Integer move) {
        this.move = move;
    }

    public Integer getMove() {
        return move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RabbitGame rabbitGame = (RabbitGame) o;
        return Objects.equals(move, rabbitGame.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(move);
    }
}
