package edu.bsu.cs222.nba.player.stats.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class NBAPlayerStatsApp extends Application {

    private HeadshotLogoView headshotLogoView;
    private ControlPanel controlPanel;
    private HBox playerInfoArea;

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

    private VBox createUI() {
        controlPanel = new ControlPanel();
        playerInfoArea = new HBox();
        VBox container = new VBox(controlPanel, playerInfoArea);
        listenForPlayerStats();
        return container;
    }

    private void listenForPlayerStats() {
        controlPanel.addListeners(playerStatsGenerationEvent -> Platform.runLater(() -> {
            playerInfoArea.getChildren().clear();
            StatView seasonStats = new StatView(playerStatsGenerationEvent.seasonPlayerStats);
            StatView careerStats = new StatView(playerStatsGenerationEvent.careerPlayerStats);
            getHeadshotLogoView();
            playerInfoArea.getChildren().addAll(headshotLogoView, seasonStats, careerStats);
        }));
    }

    private void getHeadshotLogoView() {
        try {
            headshotLogoView = HeadshotLogoView.withTeam(controlPanel.getTeam())
                    .andPlayerName(controlPanel.getPlayer());
            headshotLogoView.generateHeadshot();
            headshotLogoView.generateLogo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
