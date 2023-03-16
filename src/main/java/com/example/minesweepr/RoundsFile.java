package com.example.minesweepr;

import javafx.animation.Timeline;
import javafx.scene.control.Label;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RoundsFile {

    private String fileName = "rounds.txt";
    BufferedWriter bufferedWriter;

    public BufferedWriter createWriter() {
        FileWriter writer;
        try {
            String path = "/home/jimk/Documents/NTUA/semester9/multimedia/minesweepr/src/main/resources/com/example/minesweepr";
            File file = new File(path, fileName);
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bufferedWriter = new BufferedWriter(writer);

        return bufferedWriter;
    }

    public void writeGame(int mines, int clicks, Integer startingTime, String winner) {
        try {
            System.out.println(mines);
            bufferedWriter.write(mines+", "+clicks+", "+startingTime+", "+winner);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
