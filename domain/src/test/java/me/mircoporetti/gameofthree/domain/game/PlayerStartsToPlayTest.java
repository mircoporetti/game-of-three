package me.mircoporetti.gameofthree.domain.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerStartsToPlayTest {

    @Mock
    private QueueRepositoryPort queueRepositoryPort;
    @Mock
    private GameNotificationPort gameNotificationPort;

    private PlayerStartsToPlay underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new PlayerStartsToPlay(queueRepositoryPort, gameNotificationPort);
    }

    @Test
    void playerIsTheFirstToPlay_playHisFirstRandomMove() {
        doReturn(0).when(queueRepositoryPort).getNumberOfMessagesIn(any());

        underTest.invoke("aPlayerName", "anOpponentName");

        verify(queueRepositoryPort).getNumberOfMessagesIn("aPlayerName");
        verify(gameNotificationPort).notifyGameToTheOpponent(any(), eq("anOpponentName"));
    }

    @Test
    void gameAlreadyStarted_doNothing() {
        doReturn(1).when(queueRepositoryPort).getNumberOfMessagesIn(any());

        underTest.invoke("aPlayerName", "anOpponentName");

        verify(queueRepositoryPort).getNumberOfMessagesIn("aPlayerName");
        verify(gameNotificationPort, never()).notifyGameToTheOpponent(any(Game.class), any());
    }
}