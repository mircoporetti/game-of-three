package me.mircoporetti.gameofthree.console;

import me.mircoporetti.gameofthree.domain.game.port.GameOfThreeConsole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemConsole implements GameOfThreeConsole {

    @Override
    public void print(String line) {
        System.out.println(line);
    }

    @Override
    public String read() {
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}
