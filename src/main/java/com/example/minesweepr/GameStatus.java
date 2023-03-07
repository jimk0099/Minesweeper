package com.example.minesweepr;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

public class GameStatus extends HBox {

    public static HBox getGameStatus() {
        Text text = new Text();
        text.textProperty().bind(Grid.flaggedCells.asString());
        return new HBox(new Text("Flags Placed: "), text);
    }
}
