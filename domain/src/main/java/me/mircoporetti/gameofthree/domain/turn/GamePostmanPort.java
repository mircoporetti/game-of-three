package me.mircoporetti.gameofthree.domain.turn;

public interface GamePostmanPort {
    void notifyGameToTheOpponent(Game game);
}
