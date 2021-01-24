package me.mircoporetti.gameofthree.domain.game.port;

public interface QueueRepositoryPort {
    Integer getNumberOfMessagesIn(String aQueueName) throws Exception;
}
