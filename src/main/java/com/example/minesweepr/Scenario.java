package com.example.minesweepr;

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

}