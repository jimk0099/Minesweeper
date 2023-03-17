package com.example.minesweepr;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MinesFile {

    BufferedWriter bufferedWriter;

    public BufferedWriter createWriter() {
        FileWriter writer;
        try {
            String path = "src/main/resources/com/example/minesweepr";
            String fileName = "mines.txt";
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
