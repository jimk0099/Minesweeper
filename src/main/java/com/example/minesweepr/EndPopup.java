package com.example.minesweepr;

import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class EndPopup extends Dialog {
    private int status;             // 0: defeat, 1: win
    private GridPane gridPane = new GridPane();

    public EndPopup(int status){
        this.status = status;

        Window window = this.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        initModality(Modality.APPLICATION_MODAL);

        setTitle("Game ended");

        // set dialog style, if needed
        initStyle(StageStyle.UTILITY);

        DialogPane dialogPane = getDialogPane();

        //GridPane gridPane = new GridPane();
        // Grid Settings
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        Text text = new Text();
        text.setStyle("-fx-font: 24 arial;");
        switch (this.status) {
            case 0 -> text.setText("You Hit a Bomb :(");
            case 1 -> text.setText("You Won!");
            case 2 -> text.setText("Out of Time :(");
            case 3 -> text.setText("This is the Solution!");
            default -> text.setText("Ooops...");
        }

        gridPane.getChildren().add(text);

        dialogPane.setContent(gridPane);
    }
}
