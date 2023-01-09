package com.example.minesweepr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Scenario {
    
    private int difficulty;
    private int numberOfMines;
    private int timeInSeconds;
    private boolean hyperMine;

    public Scenario () {
        this.difficulty = 1;
        this.numberOfMines = 9;
        this.timeInSeconds = 180;
        this.hyperMine = false;
    }

    public Scenario(int difficulty, int numberOfMines, int timeInSeconds, boolean hyperMine) {
        this.difficulty = difficulty;
        this.numberOfMines = numberOfMines;
        this.timeInSeconds = timeInSeconds;
        this.hyperMine = hyperMine;
    }

    public Scenario(String filename) {

        ArrayList<String> settings = new ArrayList<String>();

        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                settings.add(data);
            }
            scanner.close();

            this.difficulty = Integer.parseInt(settings.get(0));
            this.numberOfMines = Integer.parseInt(settings.get(1));
            this.timeInSeconds = Integer.parseInt(settings.get(2));
            this.hyperMine = Boolean.parseBoolean(settings.get(3));


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public int getNumberOfMines() {
        return this.numberOfMines;
    }

    public int getTimeInSeconds() {
        return this.timeInSeconds;
    }

    public boolean getHyperMine() {
        return this.hyperMine;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public void setTimeInSeconds(int timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public void setHyperMine(boolean hyperMine) {
        this.hyperMine = hyperMine;
    }

    public void test() {
        System.out.println(this.getDifficulty());
        System.out.println(this.getNumberOfMines());
        System.out.println(this.getTimeInSeconds());
        System.out.println(this.getHyperMine());
    }

}