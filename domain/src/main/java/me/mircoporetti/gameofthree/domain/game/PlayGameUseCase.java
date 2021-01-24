package me.mircoporetti.gameofthree.domain.game;

public interface PlayGameUseCase {
    void invoke(Game opponentGame, String opponentName);
}
