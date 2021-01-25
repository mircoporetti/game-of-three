package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsole;

public class PlayerPlaysHisGameManually implements PlayGameManuallyUseCase {

    private final GameNotificationPort gameNotificationPort;
    private final GameOfThreeConsole gameOfThreeConsole;

    public PlayerPlaysHisGameManually(GameNotificationPort gameNotificationPort, GameOfThreeConsole gameOfThreeConsole) {
        this.gameNotificationPort = gameNotificationPort;
        this.gameOfThreeConsole = gameOfThreeConsole;
    }

    public void invoke(Game opponentGame, String opponentName) {

        gameOfThreeConsole.print("Come on! :) Pick an operand {-1,0,1} to make " + opponentGame.getMove() + " divisible by 3...");

        Game nextGame;
        boolean validOperand;
        Integer operand;
        do{
            operand = gameOfThreeConsole.readGameOperand();
            validOperand = opponentGame.validateOperandForNextGame(operand);
            if(!validOperand) gameOfThreeConsole.print("Ops, the input you were wrong! Please retry...");
        } while(!validOperand);

        nextGame = opponentGame.calculateManualNextGame(operand);
        if(nextGame.checkIfPlayable())
            gameNotificationPort.notifyGameToTheOpponent(nextGame, opponentName);
        else
            gameOfThreeConsole.print("WIN!!!");
    }
}
