package me.mircoporetti.gameofthree.domain.game.usecase;

public interface JoinTheGameUseCase {
    void invoke(String playerName, String opponentName);
}
