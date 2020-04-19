package edu.bsu.cs222.nba.player.stats.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private VBox firstPlayerInfoArea;
    private VBox secondPlayerInfoArea;


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
        firstPlayerInfoArea = new VBox();
        return new VBox(firstControlPanel, firstPlayerInfoArea);
    }

    private VBox buildSecondContainer(){
        secondControlPanel = new ControlPanel();
        secondPlayerInfoArea = new VBox();
        return new VBox(secondControlPanel, secondPlayerInfoArea);
    }

    private void listenForFirstPlayerStats() {
        firstControlPanel.addListeners(resultGenerationEvent -> Platform.runLater(() -> {
            firstPlayerInfoArea.getChildren().clear();
            StatView seasonStats = new StatView(resultGenerationEvent.seasonPlayerStats);
            StatView careerStats = new StatView(resultGenerationEvent.careerPlayerStats);
            PlayerInfoView playerInfoView = PlayerInfoView.create(resultGenerationEvent.playerInfo);
            getFirstHeadshotLogoView();
            HBox hbox = new HBox(firstHeadshotLogoView, playerInfoView);
            firstPlayerInfoArea.getChildren().addAll(hbox, seasonStats);
            seasonStats.setAlignment(Pos.CENTER_RIGHT);
            hbox.setAlignment(Pos.CENTER_RIGHT);
            playerInfoView.setAlignment(Pos.CENTER_RIGHT);
            playerInfoView.getNameJerseyPositionBox().setAlignment(Pos.CENTER_RIGHT);
        }));
    }

    private void listenForSecondPlayerStats() {
        secondControlPanel.addListeners(resultGenerationEvent -> Platform.runLater(() -> {
            secondPlayerInfoArea.getChildren().clear();
            StatView seasonStats = new StatView(resultGenerationEvent.seasonPlayerStats);
            StatView careerStats = new StatView(resultGenerationEvent.careerPlayerStats);
            PlayerInfoView playerInfoView = PlayerInfoView.create(resultGenerationEvent.playerInfo);
            getSecondHeadshotLogoView();
            HBox hbox = new HBox(playerInfoView, secondHeadshotLogoView);
            secondPlayerInfoArea.getChildren().addAll(hbox, seasonStats);
            playerInfoView.setAlignment(Pos.CENTER_LEFT);
            playerInfoView.getNameJerseyPositionBox().setAlignment(Pos.CENTER_LEFT);
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
