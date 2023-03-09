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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.List;

public class Minesweeper extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(Minesweeper.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //Scene scene = new Scene(Grid.makeGrid(10), 640, 640);

//        Parameters parameters = getParameters();
//        List<String> unnamedParams = parameters.getUnnamed();
//
//        String difficulty = unnamedParams.get(0);
//        String numberOfMines = unnamedParams.get(1);
//        String timeInSeconds = unnamedParams.get(2);
//        String hyperMine = unnamedParams.get(3);
//
//        int diff = Integer.parseInt(difficulty);
//        int mines = Integer.parseInt(numberOfMines);
//        int time = Integer.parseInt(timeInSeconds);
//        int hyper = Integer.parseInt(hyperMine);

//        int gridSize;
//        if (diff == 1) {
//            gridSize = 9;
//        } else {
//            gridSize = 16;
//        }
//
        Grid grid = new Grid(0);
        Pane pane = new Pane(grid.makeGrid(9));
        pane.setDisable(true);
//        GameStatus gameStatus = new GameStatus();

        CustomMenu customMenu = new CustomMenu();
        customMenu.setMenuBar(customMenu.makeMenuBar());

        HBox hBox = new HBox(customMenu.getMenuBar()); //, gameStatus.getMinesStatus(mines), gameStatus.getFlagStatus(grid));
        //VBox vbox = new VBox(hBox, grid.makeGrid(gridSize));
        VBox vBox = new VBox(hBox, pane);
        stage.setTitle("Minesweeper!");
        stage.setScene(new Scene(vBox));
        stage.setResizable(false);

        // Loader initialize
        LoaderPopup loaderPopup = new LoaderPopup();
        Window window = loaderPopup.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());


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
                            } catch (InvalidValueException ex) {
                                throw new RuntimeException(ex);
                            }

                            cFile.setScenarioName(id1+".txt");
                            System.out.println(cFile.getScenarioName());
                            cFile.scenario.test();

                            cFile.createFile();

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
                        if (!loaderPopup.isShowing()) {
                            loaderPopup.show();
                        }
                        else
                            loaderPopup.hide();
                    }
                };
        customMenu.getMenuBar().getMenus().get(0).getItems().get(1).setOnAction(eventLoader);

        // Load handler submit
        EventHandler<ActionEvent> eventLoaderSubmit =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ComboBox<String> stringComboBox = (ComboBox<String>) loaderPopup.getGridPane().getChildren().get(0);
                        String selectedFile = stringComboBox.getValue();
                        String path = "/home/jimk/Documents/NTUA/semester9/multimedia/minesweepr/src/main/resources/com/example/minesweepr/Scenarios/";
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
                            Grid grid = new Grid(scenario.getNumberOfMines());
                            Pane pane = new Pane(grid.makeGrid(gridSize));

                            // SET THIS TO FALSE WHEN START BUTTON WORKS
                            pane.setDisable(false);

                            GameStatus gameStatus = new GameStatus();

                            HBox hBox = new HBox(customMenu.getMenuBar(), gameStatus.getMinesStatus(scenario.getNumberOfMines()), gameStatus.getFlagStatus(grid));
                            VBox vBox = new VBox(hBox, pane);
                            stage.setScene(new Scene(vBox));



                        } catch (InvalidValueException e) {
                            throw new RuntimeException(e);
                        }


                    }
                };
        Button button2 = (Button) loaderPopup.getGridPane().getChildren().get(1);
        button2.setOnAction(eventLoaderSubmit);




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
//        String path = "/home/jimk/Documents/NTUA/semester9/multimedia/minesweepr/src/main/resources/com/example/minesweepr/";
//        String filename = path + "level_1_example.txt";
//        Scenario scenario = new Scenario(filename);
//        scenario.test();
//        launch(scenario.getDifficulty().toString(), scenario.getNumberOfMines().toString(),
//                scenario.getTimeInSeconds().toString(), scenario.getHyperMine().toString());
        launch();
    }
}