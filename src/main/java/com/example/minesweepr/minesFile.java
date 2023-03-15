package com.example.minesweepr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class minesFile {

    private String fileName = "mines.txt";
    BufferedWriter bufferedWriter;


    public BufferedWriter createWriter() {
        FileWriter writer;
        try {
            String path = "/home/jimk/Documents/NTUA/semester9/multimedia/minesweepr/src/main/resources/com/example/minesweepr";
            File file = new File(path, fileName);
            writer = new FileWriter(file, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bufferedWriter = new BufferedWriter(writer);

        return bufferedWriter;
    }

    public void writeLine(int randI, int randJ, int k) {
        try {
            bufferedWriter.write(randJ+", "+randI+", "+k);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
