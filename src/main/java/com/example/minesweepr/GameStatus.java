package com.example.minesweepr;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

public class GameStatus extends HBox {

    public GameStatus() {
    }

    public HBox getMinesStatus(int numOfMines) {
        HBox hBox = new HBox(new Text("Total Mines: " + numOfMines));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(0, 15, 0, 15));
        return hBox;
    }

    public HBox getFlagStatus(Grid grid) {
        Text text = new Text();
        text.textProperty().bind(grid.getFlCells().asString());
        HBox hBox = new HBox(new Text("Flags Placed: "), text);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(0, 15, 0, 15));
        return hBox;
    }

//    public HBox getTimerStatus() {
//
//    }
}
