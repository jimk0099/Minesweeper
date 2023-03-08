package com.example.minesweepr;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Minesweeper extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Minesweeper.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //Scene scene = new Scene(Grid.makeGrid(10), 640, 640);

        Parameters parameters = getParameters();
        List<String> unnamedParams = parameters.getUnnamed();

        String difficulty = unnamedParams.get(0);
        String numberOfMines = unnamedParams.get(1);
        String timeInSeconds = unnamedParams.get(2);
        String hyperMine = unnamedParams.get(3);

        int diff = Integer.parseInt(difficulty);
        int mines = Integer.parseInt(numberOfMines);
        int time = Integer.parseInt(timeInSeconds);
        int hyper = Integer.parseInt(hyperMine);

        int gridSize;
        if (diff == 1) {
            gridSize = 9;
        } else {
            gridSize = 16;
        }

        Grid grid = new Grid(mines);
        GameStatus gameStatus = new GameStatus();

        CustomMenu customMenu = new CustomMenu();
        customMenu.setMenuBar(customMenu.makeMenuBar());

        HBox hBox = new HBox(customMenu.getMenuBar(), gameStatus.getMinesStatus(mines), gameStatus.getFlagStatus(grid));
        VBox vbox = new VBox(hBox, grid.makeGrid(gridSize));
        stage.setTitle("Minesweeper!");
        stage.setScene(new Scene(vbox));
        stage.setResizable(false);


        //===========
        CPopup cPopup = new CPopup();
        //createPopup.getContent().add(createPopup.getLabel());
        //createPopup.getContent().add(new VBox(new Text("Hello2"), new VBox(new Text("Hello1"))));
        VBox vbox2 = new VBox(cPopup.getGridPane());
        cPopup.getContent().add(vbox2);

        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e)
                    {
                        if (!cPopup.isShowing())
                            cPopup.show(stage);
                        else
                            cPopup.hide();
                    }
                };
        customMenu.getMenuBar().getMenus().get(0).getItems().get(0).setOnAction(event);
        // =================


        // Exit handler
        EventHandler<ActionEvent> eventExit =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Platform.exit();
                    }
                };
        customMenu.getMenuBar().getMenus().get(0).getItems().get(3).setOnAction(eventExit);

        stage.show();
    }

    public static void main(String[] args) throws InvalidValueException {
        String path = "/home/jimk/Documents/NTUA/semester9/multimedia/minesweepr/src/main/resources/com/example/minesweepr/";
        String filename = path + "level_1_example.txt";
        Scenario scenario = new Scenario(filename);
        scenario.test();
        launch(scenario.getDifficulty().toString(), scenario.getNumberOfMines().toString(),
                scenario.getTimeInSeconds().toString(), scenario.getHyperMine().toString());
    }
}