package com.example.minesweepr;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;


public class HelloApplication extends Application {

    public static Pane makeGrid(int n) {
        double width = 64;
        Pane p = new Pane();
    
        Rectangle [][] rec = new Rectangle [n][n];

        Random random = new Random();
        int numberBombs = random.nextInt(9,12);

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                rec[i][j] = new Rectangle();
                rec[i][j].setX(i * width);
                rec[i][j].setY(j * width);
                rec[i][j].setWidth(width);
                rec[i][j].setHeight(width);
                rec[i][j].setFill(null);
                rec[i][j].setStroke(Color.BLACK);
                p.getChildren().add(rec[i][j]);
            }
        }

        int k = 0;
        while(k < numberBombs) {
            int randI = random.nextInt(n);
            int randJ = random.nextInt(n);
            if (rec[randI][randJ].getFill() != Color.BLACK) {
                rec[randI][randJ].setFill(Color.BLACK);
                k++;
            }
        }

        p.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler <MouseEvent> (){
            @Override
            public void handle(MouseEvent me){
                double posX = me.getX();
                double posY = me.getY();
    
                int colX = (int)(posX / width);
                int colY = (int) (posY / width);
                

                if (me.getButton() == MouseButton.PRIMARY) {
                    rec[colX][colY].setFill(Color.RED);
                }
                else if (me.getButton() == MouseButton.SECONDARY) {
                    if (rec[colX][colY].getFill() == Color.GREEN)
                        rec[colX][colY].setFill(Color.BLUE);
                    else if (rec[colX][colY].getFill() == Color.BLUE)
                        rec[colX][colY].setFill(Color.WHITE);
                    else rec[colX][colY].setFill(Color.GREEN);
                    
                }
            }
        });

        return p;
    }

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Scene scene = new Scene(makeGrid(10), 640, 640);
        stage.setTitle("Minesweeper!");
        stage.setScene(scene);
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