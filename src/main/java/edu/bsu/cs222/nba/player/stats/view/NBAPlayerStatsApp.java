package edu.bsu.cs222.nba.player.stats.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NBAPlayerStatsApp extends Application {

    private final Button differenceButton = new Button("See Difference");
    private final boolean PLAYER_ONE = false;
    private final boolean PLAYER_TWO = true;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("NBA Player Stats");
        UIController leftContainer = new UIController(PLAYER_ONE);
        UIController rightContainer = new UIController(PLAYER_TWO);
        HBox containers = new HBox(leftContainer, rightContainer);
        VBox ui = new VBox(containers, differenceButton);
        ui.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        stage.setScene(new Scene(ui, 1000, 700));
        stage.show();
    }


/*
    private Button makeSeeDifferenceButton(){
        differenceButton.setOnAction(event -> {
            StatView firstStatView = grabVisibleStatView();
            StatView secondStatView = grabVisibleStatView();
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

    private StatView grabVisibleStatView() {
        if (seasonStatView.isVisible()){
            return seasonStatView;
        } else {
            return careerStatView;
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
    }*/
}