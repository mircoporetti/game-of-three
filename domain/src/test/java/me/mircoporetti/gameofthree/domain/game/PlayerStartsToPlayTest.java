package me.mircoporetti.gameofthree.domain.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerStartsToPlayTest {

    @Mock
    private GameRepositoryPort gameRepositoryPort;
    @Mock
    private GameNotificationPort gameNotificationPort;

    private PlayerStartsToPlay underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new PlayerStartsToPlay(gameRepositoryPort, gameNotificationPort);
    }

    @Test
    void playerIsTheFirstToPlay_playHisFirstRandomMove() {
        doReturn(0).when(gameRepositoryPort).getNumberOfOpponentGamesFromMyQueue();

        underTest.invoke();

        verify(gameNotificationPort).notifyGameToTheOpponent(any(Game.class));
    }

    @Test
    void gameAlreadyStarted_doNothing() {
        doReturn(1).when(gameRepositoryPort).getNumberOfOpponentGamesFromMyQueue();

        underTest.invoke();

        verify(gameNotificationPort, never()).notifyGameToTheOpponent(any(Game.class));
    }
}