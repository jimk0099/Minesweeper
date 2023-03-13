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

public class EndPopup extends Dialog {
    private int status;             // 0: defeat, 1: win
    private GridPane gridPane = new GridPane();

    public EndPopup(int status){
        this.status = status;

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
        if(this.status == 1) {
            text.setText("You Won!");
        } else {
            text.setText("You Lost :(");
        }
        gridPane.getChildren().add(text);

        dialogPane.setContent(gridPane);
    }
}
