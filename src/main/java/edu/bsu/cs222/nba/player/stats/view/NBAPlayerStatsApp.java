package edu.bsu.cs222.nba.player.stats.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class NBAPlayerStatsApp extends Application {

    Button differenceButton;


    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("NBA Player Stats");
        boolean PLAYER_ONE = false;
        boolean PLAYER_TWO = true;
        UIController leftContainer = new UIController(PLAYER_ONE);
        UIController rightContainer = new UIController(PLAYER_TWO);
        leftContainer.setPadding(new Insets(0,0,0,28));
        HBox containers = new HBox(20,leftContainer, rightContainer);
        differenceButton = new DifferenceButton(leftContainer, rightContainer).makeSeeDifferenceButton();
        VBox ui = new VBox(10,containers, differenceButton);
        ui.setPadding(new Insets(20,0,0,0));
        formatButton();
        ui.setAlignment(Pos.TOP_CENTER);
        ui.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        stage.setScene(new Scene(ui, 925, 700));
        stage.show();
    }

    private void formatButton(){
        differenceButton.setPrefSize(220,30);
        differenceButton.setTextAlignment(TextAlignment.CENTER);
    }
}