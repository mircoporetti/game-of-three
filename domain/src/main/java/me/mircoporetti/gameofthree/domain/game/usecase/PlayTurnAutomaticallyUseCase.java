package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;

public interface PlayTurnAutomaticallyUseCase {
    void invoke(Game opponentGame, String opponentName);
}
