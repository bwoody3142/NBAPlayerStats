package edu.bsu.cs222.nba.player.stats.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

@SuppressWarnings("FieldCanBeLocal")
public class NBAPlayerStatsApp extends Application {

    Button differenceButton;
    private final boolean FIRST_PLAYER = false;
    private final boolean SECOND_PLAYER = true;
    private UIController leftContainer;
    private UIController rightContainer;



    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("NBA Player Stats");
        leftContainer = new UIController(FIRST_PLAYER);
        rightContainer = new UIController(SECOND_PLAYER);
        rightContainer.setVisible(false);
        leftContainer.setPadding(new Insets(0,0,0,28));
        HBox containers = new HBox(20, leftContainer, rightContainer);
        differenceButton = new DifferenceButton(leftContainer, rightContainer).makeSeeDifferenceButton();
        formatButton();
        listenForFirstStatView();
        listenForSecondStatView();
        VBox ui = new VBox(10, containers, differenceButton);
        ui.setPadding(new Insets(20,0,0,0));
        ui.setAlignment(Pos.TOP_CENTER);
        ui.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        stage.setScene(new Scene(ui, 925, 700));
        stage.show();
    }

    private void listenForFirstStatView(){
        leftContainer.addListeners(statView -> Platform.runLater(() -> rightContainer.setVisible(true)));
    }

    private void listenForSecondStatView(){
        rightContainer.addListeners(statView -> Platform.runLater(() -> differenceButton.setVisible(true)));
    }

    private void formatButton(){
        differenceButton.setPrefSize(220,30);
        differenceButton.setTextAlignment(TextAlignment.CENTER);
        differenceButton.setVisible(false);
    }
}