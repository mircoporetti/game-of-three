package me.mircoporetti.gameofthree.domain.game.usecase;

import me.mircoporetti.gameofthree.domain.game.Game;
import me.mircoporetti.gameofthree.domain.game.exception.QueueNotExistsException;
import me.mircoporetti.gameofthree.domain.game.port.GameNotificationPort;
import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsolePort;
import me.mircoporetti.gameofthree.domain.game.port.QueueRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerJoinsTheGameTest {

    @Mock
    private GameOfThreeConsolePort console;
    @Mock
    private QueueRepositoryPort queueRepositoryPort;
    @Mock
    private GameNotificationPort gameNotificationPort;

    private PlayerJoinsTheGame underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new PlayerJoinsTheGame(queueRepositoryPort, gameNotificationPort, console);
    }

    @Test
    void playerIsTheFirstToPlay_playHisFirstRandomMove() throws Exception {
        doReturn(0).doReturn(0).when(queueRepositoryPort).getNumberOfMessagesIn(any());

        underTest.invoke("aPlayerName", "anOpponentName");

        verify(queueRepositoryPort).getNumberOfMessagesIn("aPlayerName");
        verify(queueRepositoryPort).getNumberOfMessagesIn("anOpponentName");
        verify(gameNotificationPort).notifyGameToTheOpponent(any(), eq("anOpponentName"));
    }

    @Test
    void gameAlreadyStarted_doNothing() throws Exception {
        doReturn(1).doReturn(0).when(queueRepositoryPort).getNumberOfMessagesIn(any());

        underTest.invoke("aPlayerName", "anOpponentName");

        verify(queueRepositoryPort).getNumberOfMessagesIn("aPlayerName");
        verify(queueRepositoryPort).getNumberOfMessagesIn("anOpponentName");
        verify(gameNotificationPort, never()).notifyGameToTheOpponent(any(Game.class), any());
    }

    @Test
    void queueDontExistsYet() throws Exception {
        doThrow(QueueNotExistsException.class).when(queueRepositoryPort).getNumberOfMessagesIn(any());

        underTest.invoke("aPlayerName", "anOpponentName");

        verify(queueRepositoryPort).getNumberOfMessagesIn("aPlayerName");
        verify(gameNotificationPort, never()).notifyGameToTheOpponent(any(Game.class), any());
        verify(console).print("Waiting for the opponent...");

    }
}
