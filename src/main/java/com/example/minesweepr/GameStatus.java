package com.example.minesweepr;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameStatus extends HBox {

    protected static Timeline timeline = new Timeline();

    public GameStatus() {
        timeline = new Timeline();
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

    public HBox getTimerStatus(int seconds) {

        Integer startingTime = seconds;
        SimpleIntegerProperty remainingTime = new SimpleIntegerProperty(startingTime);

        Label timeLabel = new Label();
        HBox hBox = new HBox(new Text("Timer: "));
        hBox.setPadding(new Insets(0, 15, 0, 15));
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(timeLabel);

        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(startingTime + 1), new KeyValue(remainingTime, 0)));
        timeline.playFromStart();

        timeLabel.setText(startingTime.toString());
        timeLabel.textProperty().bind(remainingTime.asString());

        timeline.setOnFinished(event -> {
            System.out.println("Out of time");

            Grid.p.setDisable(true);
            EndPopup endPopup = new EndPopup(2);
            if (!endPopup.isShowing()) {
                endPopup.show();
            } else {
                endPopup.hide();
            }
        });

        return hBox;

        }

}
