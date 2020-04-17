package edu.bsu.cs222.nba.player.stats.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class NBAPlayerStatsApp extends Application {

    private HeadshotLogoView firstHeadshotLogoView;
    private HeadshotLogoView secondHeadshotLogoView;
    private ControlPanel firstControlPanel;
    private ControlPanel secondControlPanel;
    private HBox firstPlayerInfoArea;
    private HBox secondPlayerInfoArea;


    public NBAPlayerStatsApp() {
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("NBA Player Stats");
        Parent ui = createUI();
        stage.setScene(new Scene(ui, 900, 650));
        stage.show();
    }

    private HBox createUI() {
        VBox firstContainer = buildFirstContainer();
        VBox secondContainer = buildSecondContainer();
        HBox ui = new HBox(firstContainer, secondContainer);
        ui.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        listenForFirstPlayerStats();
        listenForSecondPlayerStats();
        return ui;
    }

    private VBox buildFirstContainer(){
        firstControlPanel = new ControlPanel();
        firstPlayerInfoArea = new HBox();
        return new VBox(firstControlPanel, firstPlayerInfoArea);
    }

    private VBox buildSecondContainer(){
        secondControlPanel = new ControlPanel();
        secondPlayerInfoArea = new HBox();
        return new VBox(secondControlPanel, secondPlayerInfoArea);
    }

    private void listenForFirstPlayerStats() {
        firstControlPanel.addListeners(playerStatsGenerationEvent -> Platform.runLater(() -> {
            firstPlayerInfoArea.getChildren().clear();
            StatView seasonStats = new StatView(playerStatsGenerationEvent.seasonPlayerStats);
            StatView careerStats = new StatView(playerStatsGenerationEvent.careerPlayerStats);
            getFirstHeadshotLogoView();
            firstPlayerInfoArea.getChildren().addAll(firstHeadshotLogoView, seasonStats, careerStats);
        }));
    }

    private void listenForSecondPlayerStats() {
        secondControlPanel.addListeners(playerStatsGenerationEvent -> Platform.runLater(() -> {
            secondPlayerInfoArea.getChildren().clear();
            StatView seasonStats = new StatView(playerStatsGenerationEvent.seasonPlayerStats);
            StatView careerStats = new StatView(playerStatsGenerationEvent.careerPlayerStats);
            getSecondHeadshotLogoView();
            secondPlayerInfoArea.getChildren().addAll(seasonStats, careerStats, secondHeadshotLogoView);
        }));
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
