package me.mircoporetti.gameofthree.domain.turn;

public interface GameNotificationPort {
    void notifyGameToTheOpponent(Game game);
}
