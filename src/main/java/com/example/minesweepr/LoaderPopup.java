package com.example.minesweepr;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.io.File;

public class LoaderPopup extends Dialog<String> {

    private GridPane gridPane = new GridPane();

    public LoaderPopup() {

        initModality(Modality.APPLICATION_MODAL);

        setTitle("Select Scenario");
        setHeaderText("Select a file from the dropdown:");
        // set dialog style, if needed
        initStyle(StageStyle.UTILITY);

        DialogPane dialogPane = getDialogPane();

        // Grid Settings
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        dialogPane.setContent(gridPane);


        File dir = new File("src/main/resources/com/example/minesweepr/Scenarios");
        File[] files = dir.listFiles();

        ComboBox<String> fileDropdown = new ComboBox<>();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                fileDropdown.getItems().add(file.getName());
            }
        }

        gridPane.add(fileDropdown, 0, 0);

        // Submit button
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setBackground(new Background(new BackgroundFill(Color.valueOf("#58a6ff"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.add(submitButton, 1, 1);             // 1

    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void test1() {
        gridPane.getChildren().remove(0, 2);
        File dir = new File("src/main/resources/com/example/minesweepr/Scenarios");
        File[] files = dir.listFiles();

        ComboBox<String> fileDropdown = new ComboBox<>();
        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                fileDropdown.getItems().add(file.getName());
            }
        }

        gridPane.add(fileDropdown, 0, 0);

        // Submit button
        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setBackground(new Background(new BackgroundFill(Color.valueOf("58a6ff"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.add(submitButton, 1, 1);             // 1

        DialogPane dialogPane = getDialogPane();
        dialogPane.setContent(gridPane);
    }
}
