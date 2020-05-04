package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.IndividualStatistic;
import edu.bsu.cs222.nba.player.stats.model.PlayerStats;
import edu.bsu.cs222.nba.player.stats.model.Statistic;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

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
        differenceButton.setTextAlignment(TextAlignment.CENTER);
    }

    public Button makeSeeDifferenceButton(){
        differenceButton.setOnAction(event -> {
            setupButton();
            highlightVisibleStats();
        });
        return differenceButton;
    }

    private void highlightVisibleStats() {
        for (Statistic statistic : Statistic.values()) {
            if (statistic != Statistic.GAMES_PLAYED) {
                makeLabels(statistic);
                if (statistic != Statistic.TURNOVERS) highlightLabels();
                else highlightLabelsForTurnovers();
            }
        }
    }

    private void highlightLabels() {
        int flag = leftStat.compareTo(rightStat);
        if (flag == 1) {
            highlightLabelGreen(leftLabel);
            highlightLabelRed(rightLabel);
        } else if (flag == -1) {
            highlightLabelGreen(rightLabel);
            highlightLabelRed(leftLabel);
        } else highlightBothLabelsBlack();
    }

    private void highlightLabelsForTurnovers() {
        int flag = leftStat.compareTo(rightStat);
        if (flag == 1) {
            highlightLabelGreen(rightLabel);
            highlightLabelRed(leftLabel);
        } else if (flag == -1) {
            highlightLabelGreen(leftLabel);
            highlightLabelRed(rightLabel);
        } else {
            highlightBothLabelsBlack();
        }
    }

    private void highlightBothLabelsBlack(){
        highlightLabelBlack(rightLabel);
        highlightLabelBlack(leftLabel);
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

    private void highlightLabelBlack(Label label){
        label.setTextFill(Color.BLACK);
    }
}
