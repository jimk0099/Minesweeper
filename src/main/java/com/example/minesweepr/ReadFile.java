package com.example.minesweepr;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemException;
import java.util.Scanner;
import com.example.minesweepr.Scenario;

public class ReadFile {

    Scenario scenario = new Scenario();
    public void test() {
        System.out.println(scenario.getDifficulty());
        System.out.println(scenario.getNumberOfMines());
        System.out.println(scenario.getTimeInSeconds());
        System.out.println(scenario.getHyperMine());
    }

    public Scenario read(String filename) {

        Scenario scenario = new Scenario();
        
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return new Scenario();
    }
}