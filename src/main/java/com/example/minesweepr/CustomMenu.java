package com.example.minesweepr;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomMenu extends MenuBar {

    public static MenuBar makeMenu ()
    {
        // create a menu
        Menu m = new Menu("Menu");

        // create menu items
        MenuItem m1 = new MenuItem("menu item 1");
        MenuItem m2 = new MenuItem("menu item 2");
        MenuItem m3 = new MenuItem("menu item 3");

        // add menu items to menu
        m.getItems().add(m1);
        m.getItems().add(m2);
        m.getItems().add(m3);

        // create a menu bar
        MenuBar mb = new MenuBar();

        // add menu to menu bar
        mb.getMenus().add(m);

        return mb;
    }
}
