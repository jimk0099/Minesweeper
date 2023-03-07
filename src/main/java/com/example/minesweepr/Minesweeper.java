package com.example.minesweepr;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Minesweeper extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Minesweeper.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //Scene scene = new Scene(Grid.makeGrid(10), 640, 640);
        Grid grid = new Grid();
        HBox hBox = new HBox(CustomMenu.makeMenu(), GameStatus.getGameStatus(grid));
        VBox vbox = new VBox(hBox, grid.makeGrid(10));
        stage.setTitle("Minesweeper!");
        stage.setScene(new Scene(vbox));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws InvalidValueException {
        String path = "/home/jimk/Documents/NTUA/semester9/multimedia/minesweepr/src/main/resources/com/example/minesweepr/";
        String filename = path + "invalid_range_example.txt";
        Scenario scenario = new Scenario(filename);
        scenario.test();
        launch();
    }
}