package me.mircoporetti.gameofthree.rabbitmq.events;

import me.mircoporetti.gameofthree.domain.game.usecase.JoinTheGameUseCase;
import me.mircoporetti.gameofthree.rabbitmq.events.player.Player;
import me.mircoporetti.gameofthree.rabbitmq.events.player.PlayerMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerTest {

    @Mock
    private JoinTheGameUseCase playerStartsToPlay;

    private Player underTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        underTest = new Player("aName", "anOpponentName", PlayerMode.AUTO, playerStartsToPlay);
    }

    @Test
    void playerStartToPlay() {

        underTest.startToPlay();

        verify(playerStartsToPlay).invoke("aName", "anOpponentName");
    }
}
