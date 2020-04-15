package edu.bsu.cs222.nba.player.stats.view;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class NBAPlayerStatsApp extends Application {

    private StatView seasonStats;
    private StatView careerStats;
    private HeadshotLogoView headshotLogoView;

    public NBAPlayerStatsApp() {
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("NBA Player Stats");
        Parent ui = createUI();
        stage.setScene(new Scene(ui, 900, 650));
        stage.show();
    }

    private VBox createUI() throws IOException {
        ControlPanel controlPanel = new ControlPanel();
        controlPanel.addListeners(playerStatsGenerationEvent -> {
            seasonStats = new StatView(playerStatsGenerationEvent.seasonPlayerStats);
            careerStats = new StatView(playerStatsGenerationEvent.careerPlayerStats);
            headshotLogoView = HeadshotLogoView.withTeam(controlPanel.teams.getValue())
                                               .andPlayerName(controlPanel.player.getValue());
        });
        return new VBox(controlPanel, headshotLogoView, seasonStats, careerStats);
    }
}
