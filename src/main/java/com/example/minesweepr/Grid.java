package com.example.minesweepr;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

public class Grid extends Pane {

    protected static IntegerProperty flaggedCells = new SimpleIntegerProperty();

    public static Pane makeGrid(int n) {
        double width = 64;
        Pane p = new Pane();

        Cell [][] cell = new Cell [n][n];

        Random random = new Random();
        int numberBombs = random.nextInt(9,12);

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                cell[i][j] = new Cell();            // status: 0, adjacent: 0
                cell[i][j].setX(i * width);
                cell[i][j].setY(j * width);
                cell[i][j].setWidth(width);
                cell[i][j].setHeight(width);
                cell[i][j].setFill(Color.WHITE);
                cell[i][j].setStroke(Color.BLACK);
                p.getChildren().add(cell[i][j]);
            }
        }

        // Insert bombs
        int k = 0;
        while(k < numberBombs) {
            int randI = random.nextInt(n);
            int randJ = random.nextInt(n);
            if (cell[randI][randJ].getStatus() != -1) {     // if this is not a bomb
                cell[randI][randJ].setStatus(-1);           // set it as bomb
                k++;
            }
        }

        // Set status depending on the adjacent bombs
        Integer val = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(cell[i][j].getStatus() == -1) {          // if it is a bomb
                    //TESTING ONLY
                    //cell[i][j].setFill(Color.BLACK);
                } else {
                    if (i-1 < 0 && j-1 < 0) {                // Top Left corner
                        val = -(cell[i+1][j].getStatus() + cell[i][j+1].getStatus() + cell[i+1][j+1].getStatus());
                    } else if (i+1 == n && j-1 < 0){         // Top Right corner
                        val = -(cell[i][j+1].getStatus() + cell[i-1][j].getStatus() + cell[i-1][j+1].getStatus());
                    } else if (i-1 < 0 && j+1 == n) {        // Bottom Left corner
                        val = -(cell[i][j-1].getStatus() + cell[i+1][j].getStatus() + cell[i+1][j-1].getStatus());
                    } else if (i+1 == n && j+1 == n) {        // Bottom Right corner
                        val = -(cell[i][j-1].getStatus() + cell[i-1][j].getStatus() + cell[i-1][j-1].getStatus());
                    } else if (j-1 < 0) {                   // Top Row
                        val = -(cell[i-1][j].getStatus() + cell[i+1][j].getStatus() + cell[i][j+1].getStatus() +
                                cell[i-1][j+1].getStatus() + cell[i+1][j+1].getStatus());
                    } else if (i+1 == n) {                   // Right Column
                        val = -(cell[i][j-1].getStatus() + cell[i][j+1].getStatus() + cell[i-1][j].getStatus() +
                                cell[i-1][j-1].getStatus() + cell[i-1][j+1].getStatus());
                    } else if (j+1 == n) {                   // Bottom Row
                        val = -(cell[i-1][j].getStatus() + cell[i+1][j].getStatus() + cell[i][j-1].getStatus() +
                                cell[i-1][j-1].getStatus() + cell[i+1][j-1].getStatus());
                    } else if (i-1 < 0) {                   // Left Column
                        val = -(cell[i][j-1].getStatus() + cell[i][j+1].getStatus() + cell[i+1][j].getStatus() +
                                cell[i+1][j+1].getStatus() + cell[i+1][j-1].getStatus());
                    } else {                                // Middle Cell
                        val = -(cell[i-1][j-1].getStatus() + cell[i-1][j].getStatus() + cell[i-1][j+1].getStatus() +
                                cell[i+1][j-1].getStatus() + cell[i+1][j].getStatus() + cell[i+1][j+1].getStatus() +
                                cell[i][j+1].getStatus() + cell[i][j-1].getStatus());
                    }
                    cell[i][j].setAdj(val);
//                    String str = val.toString();
//                    Text text = new Text(i * width + width/2, j * width + width/2, str);
//                    text.fontProperty().set(Font.font(20.0));
//                    text.fillProperty().set(Color.BLACK);
//                    cell[i][j].setAdjacent(text);
//                    p.getChildren().add(cell[i][j].getAdjacent());
                }
            }
        }

        p.setOnMouseClicked((EventHandler<? super MouseEvent>) new EventHandler <MouseEvent> (){
            @Override
            public void handle(MouseEvent me){
                //System.out.println(me.getX());
                double posX = me.getX();
                double posY = me.getY();

                int colX = (int)(posX / width);
                int colY = (int) (posY / width);

                //System.out.println();

                if (me.getButton() == MouseButton.PRIMARY) {
                    openCell(colX, colY, cell, width, p, n);
                }
                else if (me.getButton() == MouseButton.SECONDARY) {
                    flagCell(colX, colY, cell, p, flaggedCells);
                }
            }
        });

        return p;
    }

    private static void openCell(int colX, int colY, Cell [][] c, double width, Pane p, int n) {
        // If the cell is closed open it
        if (colX < 0 || colY < 0 || colX > n-1 || colY > n-1 ) {        // if out of bounds
            return;
        } else {
            if (!c[colX][colY].isOpened()) {
                if (c[colX][colY].isFlag()) {
                    c[colX][colY].setFlag(false);
                    c[colX][colY].setFill(Color.WHITE);
                    updateFlaggedCells(flaggedCells, -1);
                }
                c[colX][colY].setOpened(true);
                if (c[colX][colY].getStatus() == -1) {                  // if we open a bomb

                } else if (c[colX][colY].getStatus() == 0 && c[colX][colY].getAdj() == 0) {  // if it is a non-bomb cell with no adj bombs
                    c[colX][colY].setFill(Color.valueOf("#E2E2E2"));
                    openCell(colX, colY - 1, c, width, p, n);
                    openCell(colX, colY + 1, c, width, p, n);
                    openCell(colX - 1, colY, c, width, p, n);
                    openCell(colX + 1, colY, c, width, p, n);
                    openCell(colX - 1, colY - 1, c, width, p, n);
                    openCell(colX + 1, colY - 1, c, width, p, n);
                    openCell(colX - 1, colY + 1, c, width, p, n);
                    openCell(colX + 1, colY + 1, c, width, p, n);
                } else {
                    String str = c[colX][colY].getAdj().toString();
                    Text text = new Text(colX * width + width / 2, colY * width + width / 2, str);
                    text.fontProperty().set(Font.font(20.0));
                    switch (c[colX][colY].getAdj()) {
                        case 1 -> text.fillProperty().set(Color.BLUE);
                        case 2 -> text.fillProperty().set(Color.GREEN);
                        default -> text.fillProperty().set(Color.RED);
                    }
                    c[colX][colY].setAdjacent(text);
                    p.getChildren().add(c[colX][colY].getAdjacent());
                }
            }
        }
    }

    private static void flagCell(int colX, int colY, Cell [][] c, Pane p, IntegerProperty flaggedCells) {
        if (c[colX][colY].isOpened()) {

        } else {
            if (!c[colX][colY].isFlag()) {              // if it is not flagged
                c[colX][colY].setFlag(true);
                c[colX][colY].setFill(Color.YELLOW);
                updateFlaggedCells(flaggedCells, 1);
            } else {                                    // if it is already flagged
                c[colX][colY].setFlag(false);
                c[colX][colY].setFill(Color.WHITE);
                updateFlaggedCells(flaggedCells, -1);
            }
        }
    }

    private static void updateFlaggedCells(IntegerProperty flaggedCells, int amount) {
        flaggedCells.set(flaggedCells.get() + amount);
    }
}
