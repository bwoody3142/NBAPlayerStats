package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.IndividualStatistic;
import edu.bsu.cs222.nba.player.stats.model.PlayerStats;
import edu.bsu.cs222.nba.player.stats.model.Statistic;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class NBAPlayerStatsApp extends Application {

    private UIController leftContainer;
    private UIController rightContainer;
    private Button differenceButton = new Button("See Difference");

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("NBA Player Stats");
        boolean PLAYER_ONE = false;
        boolean PLAYER_TWO = true;
        leftContainer = new UIController(PLAYER_ONE);
        rightContainer = new UIController(PLAYER_TWO);
        HBox containers = new HBox(leftContainer, rightContainer);
        differenceButton = makeSeeDifferenceButton();
        VBox ui = new VBox(containers, differenceButton);
        ui.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
        stage.setScene(new Scene(ui, 1000, 700));
        stage.show();
    }

    private Button makeSeeDifferenceButton(){
        differenceButton.setOnAction(event -> {
            StatView leftStatView = leftContainer.grabVisibleStatView();
            StatView rightStatView = rightContainer.grabVisibleStatView();
            PlayerStats leftPlayerStats = leftStatView.getPlayerStats();
            PlayerStats rightPlayerStats = rightStatView.getPlayerStats();
            Arrays.asList(Statistic.values()).forEach(statistic -> {
                if(statistic != Statistic.GAMES_PLAYED) {
                    IndividualStatistic leftStat = IndividualStatistic.withPlayerStats(leftPlayerStats).andStatisticType(statistic);
                    IndividualStatistic rightStat = IndividualStatistic.withPlayerStats(rightPlayerStats).andStatisticType(statistic);
                    Label leftLabel = leftStatView.getLabelFromListOfLabels(statistic.index);
                    Label rightLabel = rightStatView.getLabelFromListOfLabels(statistic.index);
                    int flag = leftStat.compareTo(rightStat);
                    if (flag > 0) {
                        highlightLabelGreen(leftLabel);
                        highlightLabelRed(rightLabel);
                    } else if (flag < 0) {
                        highlightLabelGreen(rightLabel);
                        highlightLabelRed(leftLabel);
                    }
                }
            });
        });
        return differenceButton;
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

//    private void removeHighlightFromAllLabels(){
//        highlightLabelBlack(firstSeasonStatView.getListOfLabels());
//        highlightLabelBlack(firstCareerStatView.getListOfLabels());
//        highlightLabelBlack(secondSeasonStatView.getListOfLabels());
//        highlightLabelBlack(secondCareerStatView.getListOfLabels());
//    }
}