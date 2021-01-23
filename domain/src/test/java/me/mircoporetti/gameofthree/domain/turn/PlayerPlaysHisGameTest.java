package me.mircoporetti.gameofthree.domain.turn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerPlaysHisGameTest {

    @Mock
    private Game opponentGame;
    @Mock
    private GamePostmanPort gamePostmanPort;

    private PlayerPlaysHisGame underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new PlayerPlaysHisGame(gamePostmanPort);
    }

    @Test
    void playANotFinalTurn() {

        Game gameToBePlayed = new Game(20);
        doReturn(gameToBePlayed).when(opponentGame).calculateNextGame();

        underTest.invoke(opponentGame);

        verify(gamePostmanPort).send(gameToBePlayed);
    }

    @Test
    void playTheFinalTurn() {

        Game lastGame = new Game(1);
        doReturn(lastGame).when(opponentGame).calculateNextGame();

        underTest.invoke(opponentGame);

        verify(gamePostmanPort, never()).send(lastGame);
    }
}