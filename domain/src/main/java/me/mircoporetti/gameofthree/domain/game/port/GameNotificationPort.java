package me.mircoporetti.gameofthree.domain.game.port;

import me.mircoporetti.gameofthree.domain.game.Game;

public interface GameNotificationPort {
    void notifyGameToTheOpponent(Game gameToBeNotified, String opponentName);
}
