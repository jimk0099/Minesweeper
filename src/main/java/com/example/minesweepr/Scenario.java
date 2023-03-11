package com.example.minesweepr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.minesweepr.InvalidValueException;

public class Scenario {

    private boolean valid;
    private Integer difficulty;
    private Integer numberOfMines;
    private Integer timeInSeconds;
    private Integer hyperMine;          // 0 or 1

    public Scenario () {
        this.valid = true;
        this.difficulty = 1;
        this.numberOfMines = 9;
        this.timeInSeconds = 180;
        this.hyperMine = 0;
    }

    public Scenario(Integer difficulty, Integer numberOfMines, Integer timeInSeconds, Integer hyperMine) throws InvalidValueException {
        this.setDifficulty(difficulty);
        this.setNumberOfMines(numberOfMines);
        this.setTimeInSeconds(timeInSeconds);
        this.setHyperMine(hyperMine);
    }

    public Scenario(String filename) throws InvalidValueException {

        ArrayList<String> settings = new ArrayList<String>();

        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                settings.add(data);
            }
            scanner.close();

            this.setDifficulty(Integer.parseInt(settings.get(0)));
            this.setNumberOfMines(Integer.parseInt(settings.get(1)));
            this.setTimeInSeconds(Integer.parseInt(settings.get(2)));
            this.setHyperMine(Integer.parseInt(settings.get(3)));


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Integer getDifficulty() {
        return this.difficulty;
    }

    public Integer getNumberOfMines() {
        return this.numberOfMines;
    }

    public Integer getTimeInSeconds() {
        return this.timeInSeconds;
    }

    public Integer getHyperMine() {
        return this.hyperMine;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setDifficulty(int difficulty) throws InvalidValueException {
        try {
            if(difficulty != 1 && difficulty != 2) {
                throw new InvalidValueException();
            } else {
                this.difficulty = difficulty;
            }
        } catch (InvalidValueException e) {
            System.out.println("error while setting difficulty");
            e.printStackTrace();
            this.valid = false;
        }
    }

    public void setNumberOfMines(int numberOfMines) throws InvalidValueException {
        try {
            if(this.difficulty == 1) {
                if(numberOfMines < 9 || numberOfMines > 11) {
                    throw new InvalidValueException();
                }
            } else { //difficulty == 2 because of previous checking
                if(numberOfMines < 35 || numberOfMines > 45) {
                    throw new InvalidValueException();
                }        
            }
            this.numberOfMines = numberOfMines;
        } catch (InvalidValueException e) {
            System.out.println("error while setting difficulty number of mines");
            e.printStackTrace();
            this.valid = false;
        }
    }

    public void setTimeInSeconds(int timeInSeconds) throws InvalidValueException {
        try {
            if(this.difficulty == 1) {
                if(timeInSeconds < 120 || timeInSeconds > 180) {
                    throw new InvalidValueException();
                }
            } else {
                if(timeInSeconds < 240 || timeInSeconds > 360) {
                    throw new InvalidValueException();
                }            
            }
            this.timeInSeconds = timeInSeconds;
        } catch (InvalidValueException e) {
            System.out.println("error while setting time");
            e.printStackTrace();
            this.valid = false;
        }
    }

    public void setHyperMine(int hyperMine) throws InvalidValueException {
        try {
            if(this.difficulty == 1) {
                if(hyperMine != 0) {
                    throw new InvalidValueException();
                }
            } else {
                if(hyperMine != 0 && hyperMine != 1) {
                    throw new InvalidValueException();
                }            
            }
            this.hyperMine = hyperMine;
        } catch (InvalidValueException e) {
            System.out.println("error while setting hyper mine");
            e.printStackTrace();
            this.valid = false;
        }    
    }

    public void test() {
        System.out.println(this.valid);
        System.out.println(this.getDifficulty());
        System.out.println(this.getNumberOfMines());
        System.out.println(this.getTimeInSeconds());
        System.out.println(this.getHyperMine());
    }

}