package me.mircoporetti.gameofthree.domain.game;

public interface GameNotificationPort {
    void notifyGameToTheOpponent(Game gameToBeNotified, String opponentName);
}
