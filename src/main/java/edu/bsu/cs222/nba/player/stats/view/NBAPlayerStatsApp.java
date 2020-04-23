package edu.bsu.cs222.nba.player.stats.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NBAPlayerStatsApp extends Application {


    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("NBA Player Stats");
        boolean PLAYER_ONE = false;
        boolean PLAYER_TWO = true;
        UIController leftContainer = new UIController(PLAYER_ONE);
        UIController rightContainer = new UIController(PLAYER_TWO);
        HBox containers = new HBox(leftContainer, rightContainer);
        Button differenceButton = new DifferenceButton(leftContainer, rightContainer).makeSeeDifferenceButton();
        VBox ui = new VBox(containers, differenceButton);
        ui.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        stage.setScene(new Scene(ui, 1000, 700));
        stage.show();
    }
}