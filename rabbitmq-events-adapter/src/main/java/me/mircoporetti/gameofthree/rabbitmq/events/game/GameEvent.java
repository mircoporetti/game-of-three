package me.mircoporetti.gameofthree.rabbitmq.events.game;

import java.util.Objects;

public class GameEvent {

    private Integer move;

    public GameEvent() {
    }

    public GameEvent(Integer move) {
        this.move = move;
    }

    public Integer getMove() {
        return move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEvent gameEvent = (GameEvent) o;
        return Objects.equals(move, gameEvent.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(move);
    }
}
