package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.IndividualStatistic;
import edu.bsu.cs222.nba.player.stats.model.PlayerStatComparison;
import edu.bsu.cs222.nba.player.stats.model.Statistic;
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
import java.util.List;

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
            removeHighlightFromAllLabels();
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
            removeHighlightFromAllLabels();
        }));
    }

    private Button makeSeasonOrCareerButton(StatView seasonStats, StatView careerStats, ControlPanel controlPanel, Label label) {
        Button button = new Button("See Career Stats!");
        button.setOnAction(event -> {
            removeHighlightFromAllLabels();
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
            StatView firstStatView = grabFirstVisibleStatView();
            StatView secondStatView = grabSecondVisibleStatView();
            int index = 0;
            while(index < firstStatView.getListOfLabels().size()){
                for (Statistic statistic : Statistic.values()){
                    if (!(statistic.index == 9)) {
                        IndividualStatistic firstStat = IndividualStatistic.withPlayerStats(firstStatView.getPlayerStats()).andStatisticType(statistic);
                        IndividualStatistic secondStat = IndividualStatistic.withPlayerStats(secondStatView.getPlayerStats()).andStatisticType(statistic);
                        PlayerStatComparison playerStatComparison = PlayerStatComparison.withFirstIndividualStats(firstStat).andSecondPlayerStats(secondStat);
                        Label firstLabel = firstStatView.getLabelFromListOfLabels(index);
                        Label secondLabel = secondStatView.getLabelFromListOfLabels(index);
                        if (playerStatComparison.getFlag() == 1) {
                            highlightLabelGreen(firstLabel);
                            highlightLabelRed(secondLabel);
                        } else if (playerStatComparison.getFlag() == 2) {
                            highlightLabelRed(firstLabel);
                            highlightLabelGreen(secondLabel);
                        }
                        index++;
                    }
                }
            }
        });
        return differenceButton;
    }

    private StatView grabFirstVisibleStatView() {
        if (firstSeasonStatView.isVisible()){
            return firstSeasonStatView;
        } else {
            return firstCareerStatView;
        }
    }

    private StatView grabSecondVisibleStatView() {
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

    private void highlightLabelBlack(List<Label> list){
        for (Label label : list){
            label.setTextFill(Color.BLACK);
        }
    }

    private void removeHighlightFromAllLabels(){
        highlightLabelBlack(firstSeasonStatView.getListOfLabels());
        highlightLabelBlack(firstCareerStatView.getListOfLabels());
        highlightLabelBlack(secondSeasonStatView.getListOfLabels());
        highlightLabelBlack(secondCareerStatView.getListOfLabels());
    }
}