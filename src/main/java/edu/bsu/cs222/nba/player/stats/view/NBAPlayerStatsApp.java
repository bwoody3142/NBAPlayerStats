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

    private StatView seasonStats;
    private StatView careerStats;
    private HeadshotLogoView headshotLogoView;
    private ControlPanel controlPanel;

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
        HBox playerInfoArea = new HBox();
        VBox container = new VBox(controlPanel, playerInfoArea);
        controlPanel.addListeners(playerStatsGenerationEvent -> Platform.runLater(() -> {
            playerInfoArea.getChildren().clear();
            seasonStats = new StatView(playerStatsGenerationEvent.seasonPlayerStats);
            careerStats = new StatView(playerStatsGenerationEvent.careerPlayerStats);
            getHeadshotLogoView();
            playerInfoArea.getChildren().addAll(headshotLogoView, seasonStats, careerStats);
        }));
        return container;
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
