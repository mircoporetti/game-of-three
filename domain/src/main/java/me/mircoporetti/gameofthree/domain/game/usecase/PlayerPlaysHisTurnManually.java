package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsolePort;

public class PlayerPlaysHisTurnManually implements PlayTurnManuallyUseCase {

    private final GameNotificationPort gameNotificationPort;
    private final GameOfThreeConsolePort gameOfThreeConsolePort;

    public PlayerPlaysHisTurnManually(GameNotificationPort gameNotificationPort, GameOfThreeConsolePort gameOfThreeConsolePort) {
        this.gameNotificationPort = gameNotificationPort;
        this.gameOfThreeConsolePort = gameOfThreeConsolePort;
    }

    public void invoke(Game opponentGame, String opponentName) {

        gameOfThreeConsolePort.print("Come on! :) Pick an operand {-1,0,1} to make " + opponentGame.getMove() + " divisible by 3...");

        Game nextGame;
        boolean validOperand;
        Integer operand;
        do{
            operand = gameOfThreeConsolePort.readGameOperand();
            validOperand = opponentGame.validateOperandForNextGame(operand);
            if(!validOperand) gameOfThreeConsolePort.print("Ops, you were wrong! Please retry...");
        } while(!validOperand);

        nextGame = opponentGame.calculateManualNextGame(operand);
        if(nextGame.checkIfPlayable()) {
            gameNotificationPort.notifyGameToTheOpponent(nextGame, opponentName);
            gameOfThreeConsolePort.print("Great!");
        }
        else
            gameOfThreeConsolePort.print("WIN!!!");
    }
}
