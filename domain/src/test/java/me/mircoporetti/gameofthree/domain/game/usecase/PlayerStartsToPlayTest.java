package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.QueueRepositoryPort;
import me.mircoporetti.gameofthree.domain.game.usecase.PlayerStartsToPlay;
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
    void playerIsTheFirstToPlay_playHisFirstRandomMove() throws Exception {
        doReturn(0).when(queueRepositoryPort).getNumberOfMessagesIn(any());

        underTest.invoke("aPlayerName", "anOpponentName");

        verify(queueRepositoryPort).getNumberOfMessagesIn("aPlayerName");
        verify(gameNotificationPort).notifyGameToTheOpponent(any(), eq("anOpponentName"));
    }

    @Test
    void gameAlreadyStarted_doNothing() throws Exception {
        doReturn(1).when(queueRepositoryPort).getNumberOfMessagesIn(any());

        underTest.invoke("aPlayerName", "anOpponentName");

        verify(queueRepositoryPort).getNumberOfMessagesIn("aPlayerName");
        verify(gameNotificationPort, never()).notifyGameToTheOpponent(any(Game.class), any());
    }
}