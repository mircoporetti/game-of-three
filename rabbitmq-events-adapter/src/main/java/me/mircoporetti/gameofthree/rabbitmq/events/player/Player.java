package me.mircoporetti.gameofthree.rabbitmq.events.player;

import me.mircoporetti.gameofthree.domain.game.usecase.StartToPlayUseCase;

import javax.annotation.PostConstruct;

public class Player {

    private final String playerName;
    private final String opponentName;
    private final PlayerMode playerMode;

    private final StartToPlayUseCase startToPlay;

    public Player(String playerName, String opponentName, PlayerMode playerMode, StartToPlayUseCase startToPlay) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.playerMode = playerMode;
        this.startToPlay = startToPlay;
    }

    @PostConstruct
    public void startToPlay(){
        startToPlay.invoke(playerName, opponentName);
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public PlayerMode getPlayerMode() {
        return playerMode;
    }
}
