package me.mircoporetti.gameofthree.domain.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static me.mircoporetti.gameofthree.domain.game.GameBuilder.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerPlaysHisGameTest {

    @Mock
    private Game opponentGame;
    @Mock
    private GameNotificationPort gameNotificationPort;

    private PlayerPlaysHisGame underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new PlayerPlaysHisGame(gameNotificationPort);
    }

    @Test
    void playANotFinalTurn() {

        Game gameToBePlayed = aGame().withMove(10).build();
        doReturn(gameToBePlayed).when(opponentGame).calculateNextGame();

        underTest.invoke(opponentGame);

        verify(gameNotificationPort).notifyGameToTheOpponent(gameToBePlayed);
    }

    @Test
    void playTheFinalTurn() {

        Game lastGame = aGame().withMove(1).build();
        doReturn(lastGame).when(opponentGame).calculateNextGame();

        underTest.invoke(opponentGame);

        verify(gameNotificationPort, never()).notifyGameToTheOpponent(lastGame);
    }
}