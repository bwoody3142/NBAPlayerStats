package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.PlayerStatComparison;
import edu.bsu.cs222.nba.player.stats.model.PlayerStats;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class NBAPlayerStatsApp extends Application {

    private HeadshotLogoView firstHeadshotLogoView;
    private HeadshotLogoView secondHeadshotLogoView;
    private ControlPanel firstControlPanel;
    private ControlPanel secondControlPanel;
    private StatView firstSeasonStatView;
    private StatView firstCareerStatView;
    private StatView secondSeasonStatView;
    private StatView secondCareerStatView;
    private VBox firstPlayerResultArea;
    private VBox secondPlayerResultArea;
    private Button differenceButton = new Button("See Difference");


    public NBAPlayerStatsApp() {
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("NBA Player Stats");
        VBox ui = createUI();
        ui.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        stage.setScene(new Scene(ui, 1000, 700));
        stage.show();
    }

    private VBox createUI() {
        VBox firstContainer = buildFirstContainer();
        VBox secondContainer = buildSecondContainer();
        HBox ui = new HBox(50, firstContainer, secondContainer);
        firstContainer.setPadding(new Insets(20, 0, 0, 10));
        secondContainer.setPadding(new Insets(20, 0, 0, 0));
        secondContainer.setAlignment(Pos.TOP_RIGHT);
        listenForFirstPlayerStats();
        listenForSecondPlayerStats();
        VBox mainUI = new VBox(30, ui, differenceButton);
        mainUI.setAlignment(Pos.TOP_CENTER);
        differenceButton.setPrefSize(150, 30);
        differenceButton.setTextAlignment(TextAlignment.CENTER);
        differenceButton.setVisible(false);
        return mainUI;
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
            firstSeasonStatView = new StatView(resultGenerationEvent.seasonPlayerStats, false);
            firstCareerStatView = new StatView(resultGenerationEvent.careerPlayerStats, false);
            Label label = new Label(firstControlPanel.getSeason());
            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
            StackPane statsPane = new StackPane(firstSeasonStatView, firstCareerStatView);
            Button seasonOrCareerButton = makeSeasonOrCareerButton(firstSeasonStatView, firstCareerStatView, firstControlPanel, label);
            VBox vbox = new VBox(label, statsPane);
            HBox buttonStatsBox = new HBox(125, seasonOrCareerButton, vbox);
            firstCareerStatView.setVisible(false);
            PlayerInfoView playerInfoView = makePlayerInfoView(resultGenerationEvent);
            getFirstHeadshotLogoView();
            HBox hbox = new HBox(firstHeadshotLogoView, playerInfoView);
            firstPlayerResultArea.getChildren().addAll(hbox, buttonStatsBox);
            firstSeasonStatView.setAlignment(Pos.CENTER_RIGHT);
            firstCareerStatView.setAlignment(Pos.CENTER_RIGHT);
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
            secondSeasonStatView = new StatView(resultGenerationEvent.seasonPlayerStats, true);
            secondCareerStatView = new StatView(resultGenerationEvent.careerPlayerStats, true);
            Label label = new Label(secondControlPanel.getSeason());
            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
            StackPane statsPane = new StackPane(secondSeasonStatView, secondCareerStatView);
            Button button = makeSeasonOrCareerButton(secondSeasonStatView, secondCareerStatView, secondControlPanel, label);
            VBox vbox = new VBox(label, statsPane);
            HBox buttonStatsBox = new HBox(125, vbox, button);
            secondCareerStatView.setVisible(false);
            PlayerInfoView playerInfoView = PlayerInfoView.create(resultGenerationEvent.playerInfo);
            getSecondHeadshotLogoView();
            HBox hbox = new HBox(playerInfoView, secondHeadshotLogoView);
            secondPlayerResultArea.getChildren().addAll(hbox, buttonStatsBox);
            buttonStatsBox.setAlignment(Pos.CENTER_LEFT);
            playerInfoView.setAlignment(Pos.CENTER_LEFT);
            playerInfoView.getNameJerseyPositionBox().setAlignment(Pos.CENTER_LEFT);
            differenceButton.setVisible(true);
            differenceButton = makeSeeDifferenceButton();
        }));
    }

    private Button makeSeasonOrCareerButton(StatView seasonStats, StatView careerStats, ControlPanel controlPanel, Label label) {
        Button button = new Button("See Career Stats!");
        button.setOnAction(event -> {
            if (!careerStats.isVisible()){
                careerStats.setVisible(true);
                seasonStats.setVisible(false);
                label.setText("Career Stats");
                button.setText("See Season Stats!");
                differenceButton = makeSeeDifferenceButton();
            } else {
                careerStats.setVisible(false);
                seasonStats.setVisible(true);
                label.setText(controlPanel.getSeason());
                button.setText("See Career Stats!");
                differenceButton = makeSeeDifferenceButton();
            }
        });
        button.setAlignment(Pos.CENTER);
        return button;
    }

    private Button makeSeeDifferenceButton(){
        differenceButton.setOnAction(event -> {
            StatView firstStatView = checkForFirstSelectedStatView();
            StatView secondStatView = checkForSecondSelectedStatView();
            PlayerStats firstPlayerStats = firstStatView.getPlayerStats();
            PlayerStats secondPlayerStats = secondStatView.getPlayerStats();
            PlayerStatComparison playerStatComparison = PlayerStatComparison.withFirstPlayerStats(firstPlayerStats)
                    .andSecondPlayerStats(secondPlayerStats);
            float difference = playerStatComparison.comparePPG();
            int flag = playerStatComparison.getFlag();
            if (flag == playerStatComparison.FIRST_PLAYER_IS_GREATER){
                highlightLabelGreen(firstStatView.getPpgLabel());
                highlightLabelRed(secondStatView.getPpgLabel());
            } else if (flag == playerStatComparison.SECOND_PLAYER_IS_GREATER){
                highlightLabelRed(firstStatView.getPpgLabel());
                highlightLabelGreen(secondStatView.getPpgLabel());
            }

            float difference2 = playerStatComparison.compareAPG();
            int flag2 = playerStatComparison.getFlag();
            if (flag2 == playerStatComparison.FIRST_PLAYER_IS_GREATER){
                highlightLabelGreen(firstStatView.getApgLabel());
                highlightLabelRed(secondStatView.getApgLabel());
            } else if (flag2 == playerStatComparison.SECOND_PLAYER_IS_GREATER){
                highlightLabelRed(firstStatView.getApgLabel());
                highlightLabelGreen(secondStatView.getApgLabel());
            }

            /*for (int i = 0; i < firstPlayerStats.getSeasonStatsList().size(); i++){
                ComparePlayerStat comparePlayerStat = ComparePlayerStat
                        .withFirstPlayerStats(firstPlayerStats).andSecondPlayerStats(secondPlayerStats);
                float difference = comparePlayerStat.getDifferencesAsList().get(i);
                int flag = comparePlayerStat.getFlag();
                if (flag == comparePlayerStat.FIRST_PLAYER_IS_GREATER){
                    highlightLabelGreen(firstPlayerStatView.getListOfLabels().get(i));
                    highlightLabelRed(secondPlayerStatView.getListOfLabels().get(i));
                } else if (flag == comparePlayerStat.SECOND_PLAYER_IS_GREATER){
                    highlightLabelRed(firstPlayerStatView.getListOfLabels().get(i));
                    highlightLabelGreen(secondPlayerStatView.getListOfLabels().get(i));
                }
            }*/
        });
        return differenceButton;
    }

    private StatView checkForFirstSelectedStatView() {
        if (firstSeasonStatView.isVisible()){
            return firstSeasonStatView;
        } else {
            return firstCareerStatView;
        }
    }

    private StatView checkForSecondSelectedStatView() {
        if (secondSeasonStatView.isVisible()){
            return secondSeasonStatView;
        } else {
            return secondCareerStatView;
        }
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

    private void highlightLabelGreen(Label label){
        label.setTextFill(Color.LIMEGREEN);
    }

    private void highlightLabelRed(Label label){
        label.setTextFill(Color.RED);
    }

    private void highlightLabelBlack(Label label){
        label.setTextFill(Color.BLACK);
    }
}
