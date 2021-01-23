package me.mircoporetti.gameofthree.domain.game;

public class GameBuilder {

    private Integer move = 0;

    public static GameBuilder aGame() {
        return new GameBuilder();
    }

    public GameBuilder withMove(Integer move) {
        this.move = move;
        return this;
    }

    public Game build() {
        return new Game(move);
    }
}
