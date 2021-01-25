package me.mircoporetti.gameofthree.rabbitamqp;

public class PlayerConfiguration {

    private final String playerName;
    private final String opponentName;
    private final PlayerMode playerMode;

    public PlayerConfiguration(String playerName, String opponentName, PlayerMode playerMode) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.playerMode = playerMode;
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
