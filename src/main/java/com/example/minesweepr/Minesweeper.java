package com.example.minesweepr;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Minesweeper extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Grid grid = new Grid(0);
        Pane pane = new Pane(grid.makeGrid(9));
        pane.setDisable(true);

        CustomMenu customMenu = new CustomMenu();
        customMenu.setMenuBar(customMenu.makeMenuBar());

        HBox hBox = new HBox(customMenu.getMenuBar());
        VBox vBox = new VBox(hBox, pane);
        stage.setTitle("Minesweeper!");
        stage.setScene(new Scene(vBox));
        stage.setResizable(false);

        // Loader initialize
        LoaderPopup loaderPopup = new LoaderPopup();
        Window window = loaderPopup.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        // Rounds initialize
        RoundsPopup roundsPopup = new RoundsPopup();


        //===========
        CPopup cPopup = new CPopup();
        VBox vbox2 = new VBox(cPopup.getGridPane());
        cPopup.getContent().add(vbox2);

        // Menu Create Option
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

        // Create Submit handler
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
                            CFile cFile = new CFile();

                            try {
                                cFile.scenario.setDifficulty(Integer.parseInt(difficulty1));
                                cFile.scenario.setNumberOfMines(Integer.parseInt(mines1));
                                cFile.scenario.setHyperMine(Integer.parseInt(hypermine1));
                                cFile.scenario.setTimeInSeconds(Integer.parseInt(time1));

                                if (cFile.scenario.isValid()) {
                                    cFile.setScenarioName(id1 + ".txt");
                                    System.out.println(cFile.getScenarioName());
                                    cFile.scenario.test();

                                    cFile.createFile();
                                }

                            } catch (InvalidValueException ex) {
                                throw new RuntimeException(ex);
                            }

                            cPopup.hide();
                        }
                    }
        };
        Button button = (Button) cPopup.getGridPane().getChildren().get(11);
        button.setOnAction(createEvent);

        // Create Close handler
        EventHandler<ActionEvent> createCloseEvent =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        cPopup.hide();
                    }
                };
        Button button1 = (Button) cPopup.getGridPane().getChildren().get(12);
        button1.setOnAction(createCloseEvent);


        // Loader handler show
        EventHandler<ActionEvent> eventLoader =
                new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent e) {
                        loaderPopup.test1();
                        if (!loaderPopup.isShowing()) {
                            loaderPopup.show();
                        }
                        else {
                            loaderPopup.hide();
                        }

                        // Load handler submit
                        EventHandler<ActionEvent> eventLoaderSubmit =
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {

                                        ComboBox<String> stringComboBox = (ComboBox<String>) loaderPopup.getGridPane().getChildren().get(0);
                                        String selectedFile = stringComboBox.getValue();
                                        String path = "src/main/resources/com/example/minesweepr/Scenarios/";
                                        selectedFile = path + selectedFile;
                                        System.out.println(selectedFile);


                                        try {
                                            Scenario scenario = new Scenario(selectedFile);
                                            scenario.test();

                                            window.hide();

                                            int gridSize;
                                            if (scenario.getDifficulty() == 1) {
                                                gridSize = 9;
                                            } else {
                                                gridSize = 16;
                                            }

                                            // Start button handler
                                            EventHandler<ActionEvent> eventStart =
                                                    new EventHandler<ActionEvent>() {
                                                        @Override
                                                        public void handle(ActionEvent event) {
                                                            GameStatus.timeline.stop();
                                                            GameStatus gameStatus = new GameStatus();
                                                            Grid grid = new Grid(scenario.getNumberOfMines(), scenario.getHyperMine());
                                                            Pane pane = new Pane(grid.makeGrid(gridSize));
                                                            pane.setDisable(false);
                                                            HBox hBox = new HBox(customMenu.getMenuBar(), gameStatus.getMinesStatus(scenario.getNumberOfMines()),
                                                                                gameStatus.getFlagStatus(grid), gameStatus.getTimerStatus(scenario.getTimeInSeconds()));
                                                            VBox vBox = new VBox(hBox, pane);
                                                            stage.setScene(new Scene(vBox));


                                                            // Solution handler
                                                            EventHandler<ActionEvent> eventSolution =
                                                                    new EventHandler<ActionEvent>() {
                                                                        @Override
                                                                        public void handle(ActionEvent event) {
                                                                            pane.setDisable(true);
                                                                            grid.solve();

                                                                        }
                                                                    };
                                                            customMenu.getMenuBar().getMenus().get(1).getItems().get(1).setOnAction(eventSolution);
                                                        }
                                                    };
                                            customMenu.getMenuBar().getMenus().get(0).getItems().get(2).setOnAction(eventStart);


                                        } catch (InvalidValueException e) {
                                            throw new RuntimeException(e);
                                        }


                                    }
                                };
                        Button button2 = (Button) loaderPopup.getGridPane().getChildren().get(1);
                        button2.setOnAction(eventLoaderSubmit);

                    }
                };
        customMenu.getMenuBar().getMenus().get(0).getItems().get(1).setOnAction(eventLoader);


        // Rounds handler
        EventHandler<ActionEvent> eventRounds =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        //System.out.println("pressed Rounds button");
                        //BufferedReader bufferedReader = roundsPopup.createReader();
                        roundsPopup.readLines();
                        roundsPopup.showRoundsPopup();

                        if (!roundsPopup.isShowing()) {
                            roundsPopup.show();
                        }
                        else {
                            roundsPopup.hide();
                        }
                    }
                };
        customMenu.getMenuBar().getMenus().get(1).getItems().get(0).setOnAction(eventRounds);


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
        launch();
    }
}