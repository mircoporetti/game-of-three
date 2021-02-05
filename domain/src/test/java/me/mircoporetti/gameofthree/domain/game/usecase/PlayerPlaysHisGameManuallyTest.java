package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static me.mircoporetti.gameofthree.domain.game.GameBuilder.aGame;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerPlaysHisGameManuallyTest {

    @Mock
    private GameOfThreeConsole console;
    @Mock
    private GameNotificationPort gameNotificationPort;

    private PlayTurnManuallyUseCase underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new PlayerPlaysHisTurnManually(gameNotificationPort, console);
    }

    @Test
    void playANotFinalTurn_rightInput() {

        Game opponentGame = aGame().withMove(9).build();
        Game expectedGameToBeNotified = aGame().withMove(3).build();

        doReturn(0).when(console).readGameOperand();

        underTest.invoke(opponentGame, "opponentName");

        verify(console).print("Come on! :) Pick an operand {-1,0,1} to make 9 divisible by 3...");
        verify(console).readGameOperand();
        verify(gameNotificationPort).notifyGameToTheOpponent(expectedGameToBeNotified, "opponentName");
        verify(console).print("Great!");
    }

    @Test
    void playANotFinalTurn_wrongInput() {

        Game opponentGame = aGame().withMove(9).build();
        Game expectedGameToBeNotified = aGame().withMove(3).build();

        doReturn(1).doReturn(0).when(console).readGameOperand();

        underTest.invoke(opponentGame, "opponentName");

        verify(console).print("Come on! :) Pick an operand {-1,0,1} to make 9 divisible by 3...");
        verify(console).print("Ops, you were wrong! Please retry...");
        verify(console, times(2)).readGameOperand();
        verify(gameNotificationPort).notifyGameToTheOpponent(expectedGameToBeNotified, "opponentName");
        verify(console).print("Great!");

    }

    @Test
    void playAFinalTurn_rightInput() {

        Game opponentGame = aGame().withMove(4).build();
        Game expectedGameToBeNotified = aGame().withMove(1).build();

        doReturn(-1).when(console).readGameOperand();

        underTest.invoke(opponentGame, "opponentName");

        verify(console).print("Come on! :) Pick an operand {-1,0,1} to make 4 divisible by 3...");
        verify(console).readGameOperand();
        verify(gameNotificationPort, never()).notifyGameToTheOpponent(expectedGameToBeNotified, "opponentName");
        verify(console).print("WIN!!!");
    }
}
