package com.example.minesweepr;

import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;

public class RoundsPopup extends Dialog {

    private GridPane gridPane = new GridPane();
    protected ArrayList<String> roundsList = new ArrayList<>();

    BufferedReader bufferedReader;

    public BufferedReader createReader() {
        FileReader reader;
        try {
            String path = "src/main/resources/com/example/minesweepr";
            String fileName = "rounds.txt";
            File file = new File(path, fileName);
            System.out.println(file);
            reader = new FileReader(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bufferedReader = new BufferedReader(reader);

        return bufferedReader;
    }

    public void readLines() {
        //Label label = new Label();
        roundsList = new ArrayList<>();
        String path = "src/main/resources/com/example/minesweepr/";
        String fileName = "rounds.txt";

        try (RandomAccessFile file = new RandomAccessFile(path+fileName, "r")) {
            long length = file.length();
            int linesToRead = 5;
            long position = length - 2;//TODO: fix
            int linesRead = 0;
            StringBuilder builder = new StringBuilder();
            while (position >= 0 && linesRead < linesToRead) {
                file.seek(position);
                char c = (char) file.read();
                if (c == '\n' || position == 0) {
                    if(position == 0) {
                        builder.append(c);
                    }
                    linesRead++;
                    roundsList.add(builder.reverse().toString());
                    builder.delete(0, builder.length());
                } else {
                    builder.append(c);
                }
                position--;
            }

            for (String s : roundsList) {
                System.out.println(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRoundsPopup() {

        Window window = this.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        setTitle("Recent Games");

        DialogPane dialogPane = getDialogPane();

        // Grid Settings
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));


        if (roundsList.size() == 0) {
            HBox hBox = new HBox();
            Text text = new Text();
            text.setStyle("-fx-font: 24 arial;");
            text.setText("No recent Games...");
            hBox.getChildren().add(text);
            gridPane.add(hBox, 0, 0);
        } else {
            int i = 0;
            for (String s : roundsList) {
                HBox hBox = new HBox();
                if (i % 2 == 0) {
                    hBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#E5E0FF"), CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    hBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                String[] values = s.split(",");
                for (int j = 0; j < values.length; j++) {
                    values[j] = values[j].replace(" ", "");
                }

                Text text = new Text();
                text.setStyle("-fx-font: 24 arial;");
                text.setText("-- Game " + (i+1) + " --" +"\n"
                        + "Mines: " + values[0] + "\n"
                        + "Successful tries: " + values[1] + "\n"
                        + "Time: " + values[2] + "\n"
                        + "Winner: " + values[3]);
                hBox.getChildren().add(text);
                gridPane.add(hBox, 0, i);
                i++;
            }
        }
        dialogPane.setContent(gridPane);
    }
}
