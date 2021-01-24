package me.mircoporetti.gameofthree.domain.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static me.mircoporetti.gameofthree.domain.game.GameBuilder.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerPlaysHisGameTest {

    /*
    I previously separated the Game domain model's tests from the use case's tests.
    Then I found it not very useful and in my opinion, for this specific case, it had no a real value.
    For this reason I decided to test the entire behaviours here.
    */

    @Mock
    private GameNotificationPort gameNotificationPort;

    private PlayerPlaysHisGame underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new PlayerPlaysHisGame(gameNotificationPort);
    }

    @Test
    void playANotFinalTurn_moveAlreadyDivisibleByThree() {

        Game opponentGame = aGame().withMove(9).build();
        Game expectedGameToBeNotified = aGame().withMove(3).build();

        underTest.invoke(opponentGame);

        verify(gameNotificationPort).notifyGameToTheOpponent(expectedGameToBeNotified);
    }

    @Test
    void playANotFinalTurn_movePlusOneDivisibleByThree() {

        Game opponentGame = aGame().withMove(8).build();
        Game expectedGameToBeNotified = aGame().withMove(3).build();

        underTest.invoke(opponentGame);

        verify(gameNotificationPort).notifyGameToTheOpponent(expectedGameToBeNotified);
    }

    @Test
    void playANotFinalTurn_moveMinusOneDivisibleByThree() {

        Game opponentGame = aGame().withMove(10).build();
        Game expectedGameToBeNotified = aGame().withMove(3).build();

        underTest.invoke(opponentGame);

        verify(gameNotificationPort).notifyGameToTheOpponent(expectedGameToBeNotified);
    }

    @Test
    void playTheFinalTurn() {

        Game opponentGame = aGame().withMove(3).build();

        underTest.invoke(opponentGame);

        verify(gameNotificationPort, never()).notifyGameToTheOpponent(any());
    }
}