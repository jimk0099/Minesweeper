package com.example.minesweepr;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Cell extends Rectangle {
    private boolean flag;
    private boolean opened;
    private Text adjacent;
    private Integer status;         // 2: Hyperbomb, 1: bomb, 0: no bomb

    public Cell() {
        this.adjacent = new Text();
        this.flag = false;
        this.opened = false;
        this.status = 0;
    }

    public boolean isFlag() {
        return flag;
    }

    public boolean isOpened() {
        return opened;
    }

    public Integer getStatus() {
        return status;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Text getAdjacent() {
        return adjacent;
    }

    public void setAdjacent(Text adjacent) {
        this.adjacent = adjacent;
    }
}