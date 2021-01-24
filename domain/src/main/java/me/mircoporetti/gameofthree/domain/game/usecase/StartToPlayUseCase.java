package me.mircoporetti.gameofthree.domain.game.usecase;

public interface StartToPlayUseCase {
    void invoke(String playerName, String opponentName);
}
