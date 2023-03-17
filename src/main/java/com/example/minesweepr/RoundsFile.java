package com.example.minesweepr;

import javafx.animation.Timeline;
import javafx.scene.control.Label;

import java.io.*;

public class RoundsFile {

    private BufferedWriter bufferedWriter;
    protected int lineCount;

    public BufferedWriter createWriter() {
        FileWriter writer;
        try {
            String path = "src/main/resources/com/example/minesweepr";
            String fileName = "rounds.txt";
            File file = new File(path, fileName);
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bufferedWriter = new BufferedWriter(writer);

        return bufferedWriter;
    }

    public void writeGame(int mines, int clicks, Integer startingTime, String winner) {

        lineCount = 0;
        FileReader reader;
        try {
            String path = "src/main/resources/com/example/minesweepr";
            String fileName = "rounds.txt";
            File file = new File(path, fileName);
            reader = new FileReader(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader bufferedReader = new BufferedReader(reader);
        while (true) {
            try {
                if (bufferedReader.readLine() == null) break;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            lineCount++;
        }

        try {
            bufferedWriter.write(mines+", "+clicks+", "+startingTime+", "+winner);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
