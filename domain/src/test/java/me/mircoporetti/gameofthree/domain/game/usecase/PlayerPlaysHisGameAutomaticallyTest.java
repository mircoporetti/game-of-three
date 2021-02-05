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

class PlayerPlaysHisGameAutomaticallyTest {

    /*
    I previously separated the Game domain model's tests from the use case's tests.
    Then I found it not very useful in this specific case and in my opinion it had no a real value.
    I decided to test the business logic here.
    */

    @Mock
    private GameOfThreeConsole console;
    @Mock
    private GameNotificationPort gameNotificationPort;

    private PlayerPlaysHisTurnAutomatically underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new PlayerPlaysHisTurnAutomatically(gameNotificationPort, console);
    }

    @Test
    void playANotFinalTurn_moveAlreadyDivisibleByThree() {

        Game opponentGame = aGame().withMove(9).build();
        Game expectedGameToBeNotified = aGame().withMove(3).build();

        underTest.invoke(opponentGame, "opponentName");

        verify(gameNotificationPort).notifyGameToTheOpponent(expectedGameToBeNotified, "opponentName");
    }

    @Test
    void playANotFinalTurn_movePlusOneDivisibleByThree() {

        Game opponentGame = aGame().withMove(8).build();
        Game expectedGameToBeNotified = aGame().withMove(3).build();

        underTest.invoke(opponentGame, "opponentName");

        verify(gameNotificationPort).notifyGameToTheOpponent(expectedGameToBeNotified, "opponentName");
    }

    @Test
    void playANotFinalTurn_moveMinusOneDivisibleByThree() {

        Game opponentGame = aGame().withMove(10).build();
        Game expectedGameToBeNotified = aGame().withMove(3).build();

        underTest.invoke(opponentGame, "opponentName");

        verify(gameNotificationPort).notifyGameToTheOpponent(expectedGameToBeNotified, "opponentName");
    }

    @Test
    void playTheFinalTurn() {

        Game opponentGame = aGame().withMove(3).build();

        underTest.invoke(opponentGame, "opponentName");

        verify(gameNotificationPort, never()).notifyGameToTheOpponent(any(), any());
        verify(console).print("WIN!!!");
    }
}
