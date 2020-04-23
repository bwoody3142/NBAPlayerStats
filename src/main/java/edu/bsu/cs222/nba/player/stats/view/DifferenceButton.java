package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.IndividualStatistic;
import edu.bsu.cs222.nba.player.stats.model.PlayerStats;
import edu.bsu.cs222.nba.player.stats.model.Statistic;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class DifferenceButton extends Button {

    private final Button differenceButton = new Button("See Difference");
    private StatView leftStatView;
    private StatView rightStatView;
    private final UIController leftContainer;
    private final UIController rightContainer;
    private PlayerStats rightPlayerStats;
    private PlayerStats leftPlayerStats;
    private IndividualStatistic leftStat;
    private IndividualStatistic rightStat;
    private Label leftLabel;
    private Label rightLabel;

    public DifferenceButton(UIController leftContainer, UIController rightContainer){
        this.leftContainer = leftContainer;
        this.rightContainer = rightContainer;
    }

    public Button makeSeeDifferenceButton(){
        differenceButton.setOnAction(event -> {
            setupButton();
            Arrays.asList(Statistic.values()).forEach(statistic -> {
                if(statistic != Statistic.GAMES_PLAYED) {
                    makeLabels(statistic);
                    if (statistic != Statistic.TURNOVERS) highlightLabels();
                    else highlightLabelsForTurnovers();
                }
            });
        });
        return differenceButton;
    }

    private void highlightLabels() {
        int flag = leftStat.compareTo(rightStat);
        if (flag > 0) {
            highlightLabelGreen(leftLabel);
            highlightLabelRed(rightLabel);
        } else if (flag < 0) {
            highlightLabelGreen(rightLabel);
            highlightLabelRed(leftLabel);
        }
    }

    private void highlightLabelsForTurnovers() {
        int flag = leftStat.compareTo(rightStat);
        if (flag > 0) {
            highlightLabelGreen(rightLabel);
            highlightLabelRed(leftLabel);
        } else if (flag < 0) {
            highlightLabelGreen(leftLabel);
            highlightLabelRed(rightLabel);
        }
    }

    private void makeLabels(Statistic statistic) {
        leftStat = IndividualStatistic.withPlayerStats(leftPlayerStats).andStatisticType(statistic);
        rightStat = IndividualStatistic.withPlayerStats(rightPlayerStats).andStatisticType(statistic);
        leftLabel = leftStatView.getLabelFromListOfLabels(statistic.index);
        rightLabel = rightStatView.getLabelFromListOfLabels(statistic.index);
    }

    private void setupButton() {
        leftStatView = leftContainer.grabVisibleStatView();
        rightStatView = rightContainer.grabVisibleStatView();
        leftPlayerStats = leftStatView.getPlayerStats();
        rightPlayerStats = rightStatView.getPlayerStats();
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
}
