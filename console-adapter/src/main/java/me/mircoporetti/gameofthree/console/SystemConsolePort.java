package me.mircoporetti.gameofthree.console;

import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsolePort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemConsolePort implements GameOfThreeConsolePort {

    @Override
    public void print(String line) {
        System.out.println(line);
    }

    @Override
    public Integer readGameOperand() {
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(line);
    }
}
