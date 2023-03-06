package com.example.minesweepr;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicInteger;

public class GameStatus {

    public static Text getGameMines(int mines) {
        Text text = new Text();
        text.setText("Mines: " + mines);
        return text;
    }

    public static Text getGameFlags() {
        AtomicInteger flags = new AtomicInteger();
        Text text = new Text();
        text.setText("Flags: " + flags);
        return text;
    }
}
