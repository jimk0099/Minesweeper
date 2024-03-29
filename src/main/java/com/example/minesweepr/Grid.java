package com.example.minesweepr;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;

public class Grid extends Pane {

    private final IntegerProperty flaggedCells;
    protected static int mines;
    private boolean maxFlags;
    private boolean endFlag = false;
    private Integer hyperMine;
    private int correctMarks;
    private int size;
    private Cell [][] cell;
    protected static int clicks;

    protected static Pane p;

    public Grid(int mines) {
        this.mines = mines;
        this.maxFlags = false;
        this.flaggedCells = new SimpleIntegerProperty();
        this.correctMarks = 0;
    }

    public Grid(int mines, Integer hyperMine) {
        this.mines = mines;
        this.hyperMine = hyperMine;
        this.maxFlags = false;
        this.flaggedCells = new SimpleIntegerProperty();
        this.correctMarks = 0;
    }

    public IntegerProperty getFlCells() {
        return flaggedCells;
    }

    double width;
    public Pane makeGrid(int n) {
        clicks = 0;
        size = n;
        if(n == 9) {
            width = 64;
        } else {
            width = 56;
        }
        p = new Pane();

        cell = new Cell [n][n];

        Random random = new Random();

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
        MinesFile minesFile = new MinesFile();
        BufferedWriter bufferedWriter = minesFile.createWriter();
        int k = 0;
        while(k < this.getMines()) {
            int randI = random.nextInt(n);
            int randJ = random.nextInt(n);
            if (cell[randI][randJ].getStatus() == 0) {          // if this is not a bomb
                cell[randI][randJ].setStatus(-1);
                if (hyperMine == 1 && k == 0) {                  // Set the first mine to be the hyperMine if the scenario has hyperMine
                    cell[randI][randJ].setHyperBomb(true);
                    minesFile.writeLine(randI, randJ, 1);
                }
                minesFile.writeLine(randI, randJ, 0);
                k++;
            }
        }
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Set status depending on the adjacent bombs
        int val;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(cell[i][j].getStatus() == -1) {          // if it is a bomb
                    //TESTING: Show Mines
                    //cell[i][j].setFill(Color.BLACK);
                    if(cell[i][j].isHyperBomb()) {
                        // TESTING: Show hyperMine
                        //cell[i][j].setFill(Color.GREEN);
                    }
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
                }
            }
        }

        p.setOnMouseClicked(me -> {
            double posX = me.getX();
            double posY = me.getY();

            int colX = (int) (posX / width);
            int colY = (int) (posY / width);

            if(!cell[colX][colY].isDisabled()) {

                if (me.getButton() == MouseButton.PRIMARY) {
                    //Count successful left clicks
                    if(!cell[colX][colY].isOpened()) {
                        clicks++;
                    }
                    openCell(colX, colY, cell, width, p, n, flaggedCells);
                } else if (me.getButton() == MouseButton.SECONDARY) {
                    flagCell(colX, colY, cell, flaggedCells);

                }
            }
        });

        return p;
    }

    private void openCell(int colX, int colY, Cell[][] c, double width, Pane p, int n, IntegerProperty flaggedCells) {
        int openedCells;
        // If the cell is closed open it
        if (colX < 0 || colY < 0 || colX > n-1 || colY > n-1 ) {        // if out of bounds

        } else {
            if (!c[colX][colY].isOpened()) {
                if (c[colX][colY].isFlag()) {
                    c[colX][colY].setFlag(false);
                    c[colX][colY].setFill(Color.WHITE);
                    updateFlaggedCells(flaggedCells, -1, this.getMines());
                }
                c[colX][colY].setOpened(true);
                if (c[colX][colY].getStatus() == -1) {                  // if we open a bomb
                    p.setDisable(true);
                    for(int i=0; i<n; i++) {
                        for (int j = 0; j < n; j++) {
                            if (c[i][j].getStatus() == -1 && !c[i][j].isFlag()) {
                                c[i][j].setFill(Color.BLACK);
                            }
                        }
                    }
                    c[colX][colY].setFill(Color.RED);
                    GameStatus.timeline.stop();

                    //TESTING: Lose Game (mine) - Write to file
                    RoundsFile roundsFile = new RoundsFile();
                    BufferedWriter bufferedWriter = roundsFile.createWriter();
                    roundsFile.writeGame(mines, clicks, GameStatus.startingTime, "Computer");
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // Endgame popup
                    EndPopup endPopup = new EndPopup(0);
                    if (!endPopup.isShowing()) {
                        endPopup.show();
                    } else {
                        endPopup.hide();
                    }

                } else if (c[colX][colY].getStatus() == 0 && c[colX][colY].getAdj() == 0) {  // if it is a non-bomb cell with no adj bombs
                    c[colX][colY].setFill(Color.valueOf("#E2E2E2"));
                    openCell(colX, colY - 1, c, width, p, n, flaggedCells);
                    openCell(colX, colY + 1, c, width, p, n, flaggedCells);
                    openCell(colX - 1, colY, c, width, p, n, flaggedCells);
                    openCell(colX + 1, colY, c, width, p, n, flaggedCells);
                    openCell(colX - 1, colY - 1, c, width, p, n, flaggedCells);
                    openCell(colX + 1, colY - 1, c, width, p, n, flaggedCells);
                    openCell(colX - 1, colY + 1, c, width, p, n, flaggedCells);
                    openCell(colX + 1, colY + 1, c, width, p, n, flaggedCells);
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

                //Check for win condition
                openedCells = 0;
                for(int i=0; i<n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (c[i][j].isOpened() && c[i][j].getStatus() == 0) {
                            openedCells++;
                        }
                    }
                }
                if(openedCells == (n * n) - mines && !this.isEndFlag()) {
                    this.setEndFlag(true);
                    p.setDisable(true);
                    GameStatus.timeline.stop();

                    //TESTING: Win Game - write to file
                    RoundsFile roundsFile = new RoundsFile();
                    BufferedWriter bufferedWriter = roundsFile.createWriter();
                    roundsFile.writeGame(mines, clicks, GameStatus.startingTime, "Player");
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // Endgame popup
                    EndPopup endPopup = new EndPopup(1);
                    if (!endPopup.isShowing()) {
                        endPopup.show();
                    } else {
                        endPopup.hide();
                    }
                }
            }
        }
    }

    private void flagCell(int colX, int colY, Cell[][] c, IntegerProperty flaggedCells) {
        if (c[colX][colY].isOpened()) {

        } else {
            if (!c[colX][colY].isFlag()) {              // if it is not flagged
                if(!this.isMaxFlags()) {
                    c[colX][colY].setFlag(true);
                    c[colX][colY].setFill(Color.YELLOW);
                    updateFlaggedCells(flaggedCells, 1, this.getMines());
                    if (c[colX][colY].getStatus() == -1) {
                        correctMarks++;
                        if (c[colX][colY].isHyperBomb() && correctMarks <= 4) {
                            System.out.println("hyper activated");
                            activateHyper(colX, colY, c, colX, colY);
                        }
                    }
                }
            } else {                                    // if it is already flagged
                c[colX][colY].setFlag(false);
                c[colX][colY].setFill(Color.WHITE);
                updateFlaggedCells(flaggedCells, -1, this.getMines());
            }
        }
    }

    private void updateFlaggedCells(IntegerProperty flaggedCells, int amount, int mines) {
        flaggedCells.set(flaggedCells.get() + amount);

        // Reached max flags?
        this.setMaxFlags(flaggedCells.get() == mines);
    }

    private void activateHyper(int colX, int colY, Cell[][] c, int hyperColX, int hyperColY) {
        if(colX < 0 || colY < 0 || colX > size-1 || colY > size-1 ) {

        } else if (!c[colX][colY].isDisabled()) {
            c[colX][colY].setDisable(true);
            if(c[colX][colY].getStatus() == -1) {                   // if it is a bomb
                c[colX][colY].setFill(Color.BLACK);
            } else {
                c[colX][colY].setOpened(true);
                if (c[colX][colY].getAdj() == 0) {
                    c[colX][colY].setFill(Color.valueOf("#E2E2E2"));
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
            if (colX == hyperColX && colY == hyperColY) {                   // we are at the hyperMine
                activateHyper(colX-1, colY, c, hyperColX, hyperColY);
                activateHyper(colX+1, colY, c, hyperColX, hyperColY);
                activateHyper(colX, colY-1, c, hyperColX, hyperColY);
                activateHyper(colX, colY+1, c, hyperColX, hyperColY);
            } else if (colX < hyperColX) {
                activateHyper(colX-1, colY, c, hyperColX, hyperColY);
            } else if (colX > hyperColX) {
                activateHyper(colX+1, colY, c, hyperColX, hyperColY);
            } else if (colY < hyperColY) {
                activateHyper(colX, colY-1, c, hyperColX, hyperColY);
            } else if (colY > hyperColY) {
                activateHyper(colX, colY+1, c, hyperColX, hyperColY);
            }

        }
    }

    public void solve() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cell[i][j].getStatus() == -1) {
                    cell[i][j].setFill(Color.BLACK);
                } else if (cell[i][j].getStatus() == 0) {
                    if (cell[i][j].getAdj() == 0) {
                        cell[i][j].setFill(Color.valueOf("#E2E2E2"));
                    } else {
                        String str = cell[i][j].getAdj().toString();
                        Text text = new Text(i * width + width / 2, j * width + width / 2, str);
                        text.fontProperty().set(Font.font(20.0));
                        switch (cell[i][j].getAdj()) {
                            case 1 -> text.fillProperty().set(Color.BLUE);
                            case 2 -> text.fillProperty().set(Color.GREEN);
                            default -> text.fillProperty().set(Color.RED);
                        }
                        cell[i][j].setAdjacent(text);
                        p.getChildren().add(cell[i][j].getAdjacent());
                    }
                }
            }
        }
        GameStatus.timeline.stop();
        EndPopup endPopup = new EndPopup(3);
        if (!endPopup.isShowing()) {
            endPopup.show();
        } else {
            endPopup.hide();
        }
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public boolean isMaxFlags() {
        return maxFlags;
    }

    public void setMaxFlags(boolean maxFlags) {
        this.maxFlags = maxFlags;
    }

    public boolean isEndFlag() {
        return endFlag;
    }

    public void setEndFlag(boolean endFlag) {
        this.endFlag = endFlag;
    }
}
