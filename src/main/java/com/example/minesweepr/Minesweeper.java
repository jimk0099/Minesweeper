package com.example.minesweepr;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        // Create handler
        EventHandler<ActionEvent> createEvent =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        if (cPopup.isShowing()) {

                            // Get name of scenario
                            TextField id = (TextField) cPopup.getGridPane().getChildren().get(2);
                            String id1 = id.getText();

                            // Get difficulty level
                            HBox difficulty = (HBox) cPopup.getGridPane().getChildren().get(4);
                            RadioButton diffButton1 = (RadioButton) difficulty.getChildren().get(0);
                            String difficulty1;
                            if(diffButton1.isSelected()) {
                                difficulty1 = "1";
                            } else {
                                difficulty1 = "2";
                            }

                            // Get number of mines
                            TextField mines = (TextField) cPopup.getGridPane().getChildren().get(6);
                            String mines1 = mines.getText();

                            // Get HyperMine field
                            HBox hypermine = (HBox) cPopup.getGridPane().getChildren().get(8);
                            CheckBox hypermineBox = (CheckBox) hypermine.getChildren().get(0);
                            String hypermine1;
                            if(hypermineBox.isSelected()) {
                                hypermine1 = "1";
                            } else {
                                hypermine1 = "0";
                            }

                            // Get time
                            TextField time = (TextField) cPopup.getGridPane().getChildren().get(10);
                            String time1 = time.getText();

                            // Set scenario
                            try {
                                cPopup.scenario.setDifficulty(Integer.parseInt(difficulty1));
                                cPopup.scenario.setNumberOfMines(Integer.parseInt(mines1));
                                cPopup.scenario.setHyperMine(Integer.parseInt(hypermine1));
                                cPopup.scenario.setTimeInSeconds(Integer.parseInt(time1));
                            } catch (InvalidValueException ex) {
                                throw new RuntimeException(ex);
                            }

                            cPopup.scenario.test();

                            cPopup.hide();
                        }
                    }
        };


        System.out.println(cPopup.getGridPane().getChildren().get(2));
        System.out.println(cPopup.getGridPane().getChildren().get(11));
        Button button = (Button) cPopup.getGridPane().getChildren().get(11);
        button.setOnAction(createEvent);


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