package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;

public interface PlayGameManuallyUseCase {
    void invoke(Game opponentGame, String opponentName);
}