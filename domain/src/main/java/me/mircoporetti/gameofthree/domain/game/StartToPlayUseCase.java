package me.mircoporetti.gameofthree.domain.game;

public interface StartToPlayUseCase {
    void invoke(String playerName, String opponentName);
}
