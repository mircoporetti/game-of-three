package me.mircoporetti.gameofthree.domain.game;

import java.util.Objects;

public class Game {

    private final Integer move;

    public Game(Integer move) {
        this.move = move;
    }

    public Game calculateNextGame() {
        int nextMove;
        if(isDivisibleByThree(move)) {
            nextMove = move / 3;
            printGameSteps(nextMove, 0, move);
        } else if (isDivisibleByThree(move + 1)) {
            nextMove = (move + 1) / 3;
            printGameSteps(nextMove, 1, move);
        } else {
            nextMove = (move - 1) / 3;
            printGameSteps(nextMove, -1, move);
        }

        return new Game(nextMove);
    }

    public Game calculateManualNextGame(Integer operand) {
        return new Game((move + operand) / 3);
    }

    private void printGameSteps(int nextMove, int i, Integer move) {
        System.out.println("Added " + i + " to " + move + " ==> " + (move + i) + " / " + 3 + " = " + nextMove);
    }

    public boolean validateOperandForNextGame(Integer operand) {
        return (operand >= -1 && operand <= 1) && isDivisibleByThree(move + operand);
    }

    public boolean checkIfPlayable() {
        return move != 1;
    }

    private boolean isDivisibleByThree(Integer move) {
        return move % 3 == 0;
    }

    public Integer getMove() {
        return move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(move, game.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(move);
    }

    @Override
    public String toString() {
        return "Game{" +
                "move=" + move +
                '}';
    }
}
