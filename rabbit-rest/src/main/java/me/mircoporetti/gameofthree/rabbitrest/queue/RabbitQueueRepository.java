package me.mircoporetti.gameofthree.rabbitrest.queue;

import me.mircoporetti.gameofthree.domain.game.GameRepositoryPort;

public class RabbitQueueRepository implements GameRepositoryPort {
    @Override
    public Integer getNumberOfOpponentGamesFromMyQueue() {
        return null;
    }
}
