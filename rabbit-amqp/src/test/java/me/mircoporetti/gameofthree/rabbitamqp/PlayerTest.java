package me.mircoporetti.gameofthree.rabbitamqp;

import me.mircoporetti.gameofthree.domain.game.usecase.StartToPlayUseCase;
import me.mircoporetti.gameofthree.rabbitamqp.player.Player;
import me.mircoporetti.gameofthree.rabbitamqp.player.PlayerMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerTest {

    @Mock
    private StartToPlayUseCase playerStartsToPlay;

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