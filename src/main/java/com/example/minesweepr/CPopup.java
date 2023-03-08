package com.example.minesweepr;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CPopup extends Popup {

    private GridPane gridPane = new GridPane();

    public CPopup() {

        // Description
        Label descriptionLabel = new Label("Make a Custom Game");
        descriptionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        GridPane.setConstraints(descriptionLabel, 0, 0, GridPane.REMAINING, 1);
        gridPane.getChildren().add(descriptionLabel);

        // Grid Settings
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Define scenario-id
        Label nameLabel = new Label("SCENARIO-ID: ");
        TextField nameTextField = new TextField();
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameTextField, 1, 1);

        // Define difficulty
        Label diffLabel = new Label("Difficulty: ");

        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        RadioButton radioButton1 = new RadioButton("1");
        radioButton1.setToggleGroup(difficultyToggleGroup);
        RadioButton radioButton2 = new RadioButton("2");
        radioButton2.setToggleGroup(difficultyToggleGroup);

        HBox hBox = new HBox(10, radioButton1, radioButton2);
        gridPane.add(diffLabel, 0, 2);
        gridPane.add(hBox, 1, 2);

        // Define Mines
        Label minesLabel = new Label("Number of Mines: ");
        TextField minesTextField = new TextField();
        gridPane.add(minesLabel, 0, 3);
        gridPane.add(minesTextField, 1, 3);

        // Define Hypermine
        Label hypermineLabel = new Label("Hypermine: ");
        CheckBox checkBox1 = new CheckBox("");
        HBox hBox1 = new HBox(10, checkBox1);

        gridPane.add(hypermineLabel, 0, 4);
        gridPane.add(hBox1, 1, 4);

        // Define Time
        Label timeLabel = new Label("Time: ");
        TextField timeTextField = new TextField();
        gridPane.add(timeLabel, 0, 5);
        gridPane.add(timeTextField, 1, 5);

        // Submit button
        Button button = new Button();
        button.setText("Create");
        gridPane.add(button, 1, 6);

    }

    public void createScenario() {
        String fileName = "example.txt";
        String fileContent = "This is some example text.";

        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileContent);
            fileWriter.close();
            System.out.println("Text file created successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the text file.");
            e.printStackTrace();
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }
}