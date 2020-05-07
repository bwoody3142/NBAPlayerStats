package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.IndividualStatistic;
import edu.bsu.cs222.nba.player.stats.model.PlayerStats;
import edu.bsu.cs222.nba.player.stats.model.Statistic;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class DifferenceButton extends Button {

    private StatView leftStatView;
    private StatView rightStatView;
    private PlayerStats rightPlayerStats;
    private PlayerStats leftPlayerStats;
    private IndividualStatistic leftStat;
    private IndividualStatistic rightStat;
    private Label leftLabel;
    private Label rightLabel;

    public DifferenceButton(PlayerContainer leftContainer, PlayerContainer rightContainer){
        setupButton();
        setOnAction(event -> {
            setDisable(true);
            assignStats(leftContainer, rightContainer);
            highlightVisibleStats();
        });
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
            leftLabel.setTextFill(Color.LIMEGREEN);
            rightLabel.setTextFill(Color.RED);
            showDifferenceForLeftContainer(leftLabel);
        } else if (flag == -1) {
            rightLabel.setTextFill(Color.LIMEGREEN);
            leftLabel.setTextFill(Color.RED);
            showDifferenceForRightContainer(rightLabel);
        } else resetLabelHighlights();
    }

    private void highlightLabelsForTurnovers() {
        int flag = leftStat.compareTo(rightStat);
        if (flag == 1) {
            rightLabel.setTextFill(Color.LIMEGREEN);
            leftLabel.setTextFill(Color.RED);
            showDifferenceForRightContainer(rightLabel);
        } else if (flag == -1) {
            leftLabel.setTextFill(Color.LIMEGREEN);
            rightLabel.setTextFill(Color.RED);
            showDifferenceForLeftContainer(leftLabel);
        } else resetLabelHighlights();
    }

    private void resetLabelHighlights(){
        rightLabel.setTextFill(Color.BLACK);
        leftLabel.setTextFill(Color.BLACK);
    }

    private void makeLabels(Statistic statistic) {
        leftStat = IndividualStatistic.withPlayerStats(leftPlayerStats).andStatisticType(statistic);
        rightStat = IndividualStatistic.withPlayerStats(rightPlayerStats).andStatisticType(statistic);
        leftLabel = leftStatView.getLabelFromListOfLabels(statistic.getIndex());
        rightLabel = rightStatView.getLabelFromListOfLabels(statistic.getIndex());
    }

    private void assignStats(PlayerContainer leftContainer, PlayerContainer rightContainer) {
        leftStatView = leftContainer.grabVisibleStatView();
        rightStatView = rightContainer.grabVisibleStatView();
        leftPlayerStats = leftStatView.getPlayerStats();
        rightPlayerStats = rightStatView.getPlayerStats();
    }

    private void setupButton(){
        setText("See Difference!");
        setTextAlignment(TextAlignment.CENTER);
        setPrefSize(220, 30);
        setVisible(false);
    }

    public void showDifferenceForLeftContainer(Label label){
        float difference = leftStat.generateDifference(rightStat);
        String oldLabelText = label.getText();
        label.setText("+ " + difference + "  " + oldLabelText);
    }

    public void showDifferenceForRightContainer(Label label){
        float difference = leftStat.generateDifference(rightStat);
        String oldLabelText = label.getText();
        label.setText(oldLabelText + "  + " + difference);
    }
}