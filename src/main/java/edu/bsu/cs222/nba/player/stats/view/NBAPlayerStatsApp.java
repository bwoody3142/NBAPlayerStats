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
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.util.List;

public class NBAPlayerStatsApp extends Application {

    private GridPane ui;
    private HeadshotLogoView firstHeadshotLogoView;
    private HeadshotLogoView secondHeadshotLogoView;
    private ControlPanel firstControlPanel;
    private ControlPanel secondControlPanel;
    private StatView firstSeasonStatView;
    private StatView firstCareerStatView;
    private StatView secondSeasonStatView;
    private StatView secondCareerStatView;
    private GridPane firstPlayerResultArea = new GridPane();
    private GridPane secondPlayerResultArea = new GridPane();
    private Button differenceButton = new Button("See Difference");
    private boolean isSecondStatViewCreated = false;
    private boolean isFirstStatViewCreated = false;
    private boolean isSecondPlayer = false;


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
        firstControlPanel = new ControlPanel();
        secondControlPanel = new ControlPanel();
        ui = new GridPane();
        listenForFirstPlayerStats();
        listenForSecondPlayerStats();
        setupContainers();
        VBox mainUI = new VBox(30, ui, differenceButton);
        mainUI.setAlignment(Pos.TOP_CENTER);
        differenceButton.setPrefSize(150, 30);
        differenceButton.setTextAlignment(TextAlignment.CENTER);
        differenceButton.setVisible(false);
        return mainUI;
    }

    private void setupContainers() {
        ui.getChildren().addAll(firstControlPanel, firstPlayerResultArea, secondControlPanel, secondPlayerResultArea);
        GridPane.setConstraints(firstControlPanel, 0, 0);
        GridPane.setConstraints(firstPlayerResultArea, 0, 1);
        GridPane.setConstraints(secondControlPanel, 1, 0);
        GridPane.setConstraints(secondPlayerResultArea, 1, 1);
    }

    private void listenForFirstPlayerStats() {
        firstControlPanel.addListeners(resultGenerationEvent -> Platform.runLater(() -> {
            isSecondPlayer = false;
            firstPlayerResultArea = makeResultArea(resultGenerationEvent);
            if (isSecondStatViewCreated){
               removeHighlightFromAllLabels();
            }
            isFirstStatViewCreated = true;
        }));
    }

    private void listenForSecondPlayerStats() {
        secondControlPanel.addListeners(resultGenerationEvent -> Platform.runLater(() -> {
            isSecondPlayer = true;
            secondPlayerResultArea = makeResultArea(resultGenerationEvent);
            differenceButton.setVisible(true);
            differenceButton = makeSeeDifferenceButton();
            if(isFirstStatViewCreated){
                removeHighlightFromAllLabels();
            }
            isSecondStatViewCreated = true;
            }));
    }

    private GridPane makeResultArea(ResultGenerationEvent resultGenerationEvent) {
        if (isSecondPlayer) {
            secondPlayerResultArea.getChildren().clear();
            Label label = new Label(secondControlPanel.getSeason());
            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
            Button seasonOrCareerButton = makeSeasonOrCareerButton(secondSeasonStatView, secondCareerStatView, secondControlPanel, label);
            makeStatView(resultGenerationEvent);
            PlayerInfoView playerInfoView = makePlayerInfoView(resultGenerationEvent);
            HeadshotLogoView headshotLogoView = makeHeadShotLogoView(secondControlPanel);
            secondPlayerResultArea.getChildren().addAll(headshotLogoView,
                    playerInfoView, label, seasonOrCareerButton, secondSeasonStatView);
            GridPane.setConstraints(headshotLogoView, 1, 0);
            GridPane.setConstraints(playerInfoView, 0, 0);
            GridPane.setConstraints(label, 0, 1);
            GridPane.setConstraints(seasonOrCareerButton, 1, 2);
            GridPane.setConstraints(secondSeasonStatView, 0, 2);
            seasonOrCareerButton.setAlignment(Pos.CENTER);
            return secondPlayerResultArea;
        } else {
            firstPlayerResultArea.getChildren().clear();
            Label label = new Label(firstControlPanel.getSeason());
            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
            Button seasonOrCareerButton = makeSeasonOrCareerButton(firstSeasonStatView, firstCareerStatView, firstControlPanel, label);
            makeStatView(resultGenerationEvent);
            PlayerInfoView playerInfoView = makePlayerInfoView(resultGenerationEvent);
            HeadshotLogoView headshotLogoView = makeHeadShotLogoView(firstControlPanel);
            firstPlayerResultArea.getChildren().addAll(headshotLogoView,
                    playerInfoView, label, seasonOrCareerButton, firstSeasonStatView);
            GridPane.setConstraints(headshotLogoView, 0, 0);
            GridPane.setConstraints(playerInfoView, 1, 0);
            GridPane.setConstraints(label, 1, 1);
            GridPane.setConstraints(seasonOrCareerButton, 0, 2);
            GridPane.setConstraints(firstSeasonStatView, 1, 2);
            seasonOrCareerButton.setAlignment(Pos.CENTER);
            return firstPlayerResultArea;
        }
    }

    private StackPane makeStatView(ResultGenerationEvent resultGenerationEvent){
        if (isSecondPlayer){
            secondSeasonStatView = new StatView(resultGenerationEvent.seasonPlayerStats, true);
            secondCareerStatView = new StatView(resultGenerationEvent.careerPlayerStats, true);
            alignStatView();
            secondCareerStatView.setVisible(false);
            return new StackPane(secondSeasonStatView, secondCareerStatView);
        } else {
            firstSeasonStatView = new StatView(resultGenerationEvent.seasonPlayerStats, false);
            firstCareerStatView = new StatView(resultGenerationEvent.careerPlayerStats, false);
            alignStatView();
            firstCareerStatView.setVisible(false);
            return new StackPane(firstSeasonStatView, firstCareerStatView);
        }
    }

    private void alignStatView() {
        if (isSecondPlayer){
            secondSeasonStatView.setAlignment(Pos.CENTER_LEFT);
            secondCareerStatView.setAlignment(Pos.CENTER_LEFT);
        } else {
            firstSeasonStatView.setAlignment(Pos.CENTER_RIGHT);
            firstCareerStatView.setAlignment(Pos.CENTER_RIGHT);
        }
    }

    private PlayerInfoView makePlayerInfoView(ResultGenerationEvent resultGenerationEvent){
        PlayerInfoView playerInfoView = PlayerInfoView.create(resultGenerationEvent.playerInfo);
        if (isSecondPlayer){
            playerInfoView.setAlignment(Pos.CENTER_LEFT);
            playerInfoView.getNameJerseyPositionBox().setAlignment(Pos.CENTER_LEFT);
        } else {
            playerInfoView.setAlignment(Pos.CENTER_RIGHT);
            playerInfoView.getNameJerseyPositionBox().setAlignment(Pos.CENTER_RIGHT);
        }
        return playerInfoView;
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
            } else {
                careerStats.setVisible(false);
                seasonStats.setVisible(true);
                label.setText(controlPanel.getSeason());
                button.setText("See Career Stats!");
            }
            differenceButton = makeSeeDifferenceButton();
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

    private HeadshotLogoView makeHeadShotLogoView(ControlPanel controlPanel) {
        HeadshotLogoView headshotLogoView = HeadshotLogoView.withTeam(controlPanel.getTeam())
                .andPlayerName(controlPanel.getPlayer());
            try {
                headshotLogoView.generateHeadshot();
                headshotLogoView.generateLogo();
                headshotLogoView.formatFirstPane();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return headshotLogoView;
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