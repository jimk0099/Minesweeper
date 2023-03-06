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
        Menu applicationMenu = new Menu("Application");
        Menu detailsMenu = new Menu("Details");

        // create menu items
        MenuItem m1 = new MenuItem("Create");
        MenuItem m2 = new MenuItem("Load");
        MenuItem m3 = new MenuItem("Start");
        MenuItem m4 = new MenuItem("Exit");

        MenuItem d1 = new MenuItem("Rounds");
        MenuItem d2 = new MenuItem("Solution");

        // add menu items to menu
        applicationMenu.getItems().add(m1);
        applicationMenu.getItems().add(m2);
        applicationMenu.getItems().add(m3);
        applicationMenu.getItems().add(m4);

        detailsMenu.getItems().add(d1);
        detailsMenu.getItems().add(d2);

        // create a menu bar
        MenuBar mb = new MenuBar();

        // add menu to menu bar
        mb.getMenus().add(applicationMenu);
        mb.getMenus().add(detailsMenu);

        return mb;
    }
}
