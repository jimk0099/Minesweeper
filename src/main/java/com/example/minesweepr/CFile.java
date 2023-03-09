package com.example.minesweepr;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CFile {

    protected String scenarioName = null;
    protected Scenario scenario = new Scenario();

    public void createFile() {
        String fileName = this.scenarioName;
        String fileContent = this.scenario.getDifficulty().toString() + "\n" +
                             this.scenario.getNumberOfMines().toString() + "\n" +
                             this.scenario.getTimeInSeconds().toString() + "\n" +
                             this.scenario.getHyperMine().toString();

        try {
            String path = "/home/jimk/Documents/NTUA/semester9/multimedia/minesweepr/src/main/resources/com/example/minesweepr/Scenarios";
            File file = new File(path, fileName);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileContent);
            fileWriter.close();
            System.out.println("Text file created successfully.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }
}
