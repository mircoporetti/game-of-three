package me.mircoporetti.gameofthree.domain.turn;

public class PlayerPlaysHisGame {

    private final GamePostmanPort gamePostmanPort;

    public PlayerPlaysHisGame(GamePostmanPort gamePostmanPort) {
        this.gamePostmanPort = gamePostmanPort;
    }

    public void invoke(Game opponentGame) {

        Game nextGame = opponentGame.calculateNextGame();

        if(nextGame.checkIfPlayable())
            gamePostmanPort.notifyGameToTheOpponent(nextGame);
    }
}
