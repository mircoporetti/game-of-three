package me.mircoporetti.gameofthree.rabbitmq.message;

import me.mircoporetti.gameofthree.domain.turn.Game;
import me.mircoporetti.gameofthree.domain.turn.GamePostmanPort;

public class GameProducer implements GamePostmanPort {

    @Override
    public void send(Game game) {

    }
}
