package me.mircoporetti.gameofthree.domain.turn;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class GameTest {

    @Test
    void calculateNextGame_moveAlreadyDivisibleByThree() {

        Game underTest = new Game(9);

        Game result = underTest.calculateNextGame();

        assertThat(result, is(new Game(3)));
    }

    @Test
    void calculateNextGame_movePlusOneDivisibleByThree() {

        Game underTest = new Game(8);

        Game result = underTest.calculateNextGame();

        assertThat(result, is(new Game(3)));
    }
    @Test
    void calculateNextGame_moveMinusOneDivisibleByThree() {

        Game underTest = new Game(10);

        Game result = underTest.calculateNextGame();

        assertThat(result, is(new Game(3)));
    }

}