package me.mircoporetti.gameofthree.domain.game;

public interface QueueRepositoryPort {
    Integer getNumberOfMessagesIn(String aQueueName);
}
