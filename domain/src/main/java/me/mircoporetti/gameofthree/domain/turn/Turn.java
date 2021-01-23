package me.mircoporetti.gameofthree.domain.turn;

import java.util.Objects;

public class Turn {

    private final Integer move;

    public Turn(Integer move) {
        this.move = move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return Objects.equals(move, turn.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(move);
    }
}
