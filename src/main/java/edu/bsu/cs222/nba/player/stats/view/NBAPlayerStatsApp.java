package edu.bsu.cs222.nba.player.stats.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class NBAPlayerStatsApp extends Application {

    private HeadshotLogoView firstHeadshotLogoView;
    private HeadshotLogoView secondHeadshotLogoView;
    private ControlPanel firstControlPanel;
    private ControlPanel secondControlPanel;
    private VBox firstPlayerResultArea;
    private VBox secondPlayerResultArea;


    public NBAPlayerStatsApp() {
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("NBA Player Stats");
        Parent ui = createUI();
        stage.setScene(new Scene(ui, 1000, 500));
        stage.show();
    }

    private HBox createUI() {
        VBox firstContainer = buildFirstContainer();
        VBox secondContainer = buildSecondContainer();
        HBox ui = new HBox(50, firstContainer, secondContainer);
        ui.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        firstContainer.setPadding(new Insets(20, 0, 0, 10));
        secondContainer.setPadding(new Insets(20, 0, 0, 0));
        secondContainer.setAlignment(Pos.TOP_RIGHT);
        listenForFirstPlayerStats();
        listenForSecondPlayerStats();
        return ui;
    }

    private VBox buildFirstContainer(){
        firstControlPanel = new ControlPanel();
        firstPlayerResultArea = new VBox();
        return new VBox(firstControlPanel, firstPlayerResultArea);
    }

    private VBox buildSecondContainer(){
        secondControlPanel = new ControlPanel();
        secondPlayerResultArea = new VBox();
        return new VBox(secondControlPanel, secondPlayerResultArea);
    }

    private void listenForFirstPlayerStats() {
        firstControlPanel.addListeners(resultGenerationEvent -> Platform.runLater(() -> {
            firstPlayerResultArea.getChildren().clear();
            StatView seasonStats = new StatView(resultGenerationEvent.seasonPlayerStats);
            StatView careerStats = new StatView(resultGenerationEvent.careerPlayerStats);
            Label label = new Label(firstControlPanel.getSeason());
            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
            StackPane statsPane = new StackPane(seasonStats, careerStats);
            Button button = makeButton(seasonStats, careerStats, firstControlPanel, label);
            VBox vbox = new VBox(label, statsPane);
            HBox buttonStatsBox = new HBox(125, button, vbox);
            careerStats.setVisible(false);
            PlayerInfoView playerInfoView = makePlayerInfoView(resultGenerationEvent);
            getFirstHeadshotLogoView();
            HBox hbox = new HBox(firstHeadshotLogoView, playerInfoView);
            firstPlayerResultArea.getChildren().addAll(hbox, buttonStatsBox);
            seasonStats.setAlignment(Pos.CENTER_RIGHT);
            careerStats.setAlignment(Pos.CENTER_RIGHT);
            buttonStatsBox.setAlignment(Pos.CENTER_RIGHT);
            hbox.setAlignment(Pos.CENTER_RIGHT);
        }));
    }

    private PlayerInfoView makePlayerInfoView(ResultGenerationEvent resultGenerationEvent){
        PlayerInfoView playerInfoView = PlayerInfoView.create(resultGenerationEvent.playerInfo);
        playerInfoView.setAlignment(Pos.CENTER_RIGHT);
        playerInfoView.getNameJerseyPositionBox().setAlignment(Pos.CENTER_RIGHT);
        return playerInfoView;
    }

    private void listenForSecondPlayerStats() {
        secondControlPanel.addListeners(resultGenerationEvent -> Platform.runLater(() -> {
            secondPlayerResultArea.getChildren().clear();
            StatView seasonStats = new StatView(resultGenerationEvent.seasonPlayerStats);
            StatView careerStats = new StatView(resultGenerationEvent.careerPlayerStats);
            Label label = new Label(secondControlPanel.getSeason());
            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
            StackPane statsPane = new StackPane(seasonStats, careerStats);
            Button button = makeButton(seasonStats, careerStats, secondControlPanel, label);
            VBox vbox = new VBox(label, statsPane);
            HBox buttonStatsBox = new HBox(125, vbox, button);
            careerStats.setVisible(false);
            PlayerInfoView playerInfoView = PlayerInfoView.create(resultGenerationEvent.playerInfo);
            getSecondHeadshotLogoView();
            HBox hbox = new HBox(playerInfoView, secondHeadshotLogoView);
            secondPlayerResultArea.getChildren().addAll(hbox, buttonStatsBox);
            buttonStatsBox.setAlignment(Pos.CENTER_LEFT);
            playerInfoView.setAlignment(Pos.CENTER_LEFT);
            playerInfoView.getNameJerseyPositionBox().setAlignment(Pos.CENTER_LEFT);
        }));
    }

    private Button makeButton(StatView seasonStats, StatView careerStats, ControlPanel controlPanel, Label label) {
        Button button = new Button("See Career Stats!");
        button.setOnAction(event -> {
            if (!careerStats.isVisible()){
                careerStats.setVisible(true);
                seasonStats.setVisible(false);
                label.setText("Career Stats");
                button.setText("See Season Stats!");
            } else {
                careerStats.setVisible(false);
                seasonStats.setVisible(true);
                label.setText(controlPanel.getSeason());
                button.setText("See Career Stats!");
            }
        });
        button.setAlignment(Pos.CENTER);
        return button;
    }

    private void getFirstHeadshotLogoView() {
        try {
            firstHeadshotLogoView = HeadshotLogoView.withTeam(firstControlPanel.getTeam())
                    .andPlayerName(firstControlPanel.getPlayer());
            firstHeadshotLogoView.generateHeadshot();
            firstHeadshotLogoView.generateLogo();
            firstHeadshotLogoView.formatFirstPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSecondHeadshotLogoView() {
        try {
            secondHeadshotLogoView = HeadshotLogoView.withTeam(secondControlPanel.getTeam())
                    .andPlayerName(secondControlPanel.getPlayer());
            secondHeadshotLogoView.generateHeadshot();
            secondHeadshotLogoView.generateLogo();
            secondHeadshotLogoView.formatSecondPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
