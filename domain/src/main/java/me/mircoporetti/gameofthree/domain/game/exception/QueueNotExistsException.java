package me.mircoporetti.gameofthree.domain.game.exception;

public class QueueNotExistsException extends RuntimeException {
    public QueueNotExistsException(String message) {
        super(message);
    }
}
