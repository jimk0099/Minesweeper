package com.example.minesweepr;

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

import java.io.BufferedWriter;
import java.io.IOException;


/**
 * The class Game status extends Hbox.
 * GameStatus Class returns all the components we need to show
 * next to the menu bar. More specifically, we get for the current game
 * the total mines, the total flags placed and the remaining time.
 *
 */
public class GameStatus extends HBox {

    protected static Timeline timeline = new Timeline();
    protected static Label timeLabel = new Label();
    protected static Integer startingTime;


    /**
     *
     * This is the constructor of the class.
     * It resets the static variable timeline each time
     * a new object of the class is created
     *
     * @return public
     */
    public GameStatus() {

        timeline = new Timeline();
    }


    /**
     *
     * Returns a Hbox that contains the number of total
     * mines in the game that the player is currently playing.
     *
     * @param numOfMines  the num of mines
     * @return the mines status
     */
    public HBox getMinesStatus(int numOfMines) {

        HBox hBox = new HBox(new Text("Total Mines: " + numOfMines));
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(0, 15, 0, 15));
        return hBox;
    }


    /**
     *
     * Returns a Hbox that contains the number of total
     * flags that the user has placed. This value is dynamically
     * updated as the user continues to place flags at runtime.
     *
     * @param grid  the grid
     * @return the flag status
     */
    public HBox getFlagStatus(Grid grid) {

        Text text = new Text();
        text.textProperty().bind(grid.getFlCells().asString());
        HBox hBox = new HBox(new Text("Flags Placed: "), text);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(0, 15, 0, 15));
        return hBox;
    }


    /**
     *
     * Returns a Hbox that contains the time remaining for the
     * current game. The countdown timer updates every second and
     * when it reaches zero, it ends the game sending the according
     * message to the EndPopup.
     *
     * @param seconds  the seconds
     * @return the timer status
     */
    public HBox getTimerStatus(int seconds) {


        startingTime = seconds;
        SimpleIntegerProperty remainingTime = new SimpleIntegerProperty(startingTime);

        timeLabel = new Label();
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

            //TESTING: Write to file
            RoundsFile roundsFile = new RoundsFile();
            BufferedWriter bufferedWriter = roundsFile.createWriter();
            roundsFile.writeGame(Grid.mines, Grid.clicks, GameStatus.startingTime, "Computer");
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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
