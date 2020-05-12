package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.IndividualStatistic;
import edu.bsu.cs222.nba.player.stats.model.PlayerStats;
import edu.bsu.cs222.nba.player.stats.model.Statistic;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class StatView extends VBox {

    private final List<Label> statList = new ArrayList<>();
    private final PlayerStats playerStats;
    private final boolean isThisSecondPlayer;

    public StatView(PlayerStats playerStats, boolean isThisSecondPlayer) {
        this.playerStats = playerStats;
        this.isThisSecondPlayer = isThisSecondPlayer;
        makeStatLabels();
        statList.forEach(label -> getChildren().add(label));
    }

    public void makeStatLabels() {
        Statistic[] statistics = Statistic.values();
        List<IndividualStatistic> individualStatistics = playerStats.getStatsList();
        for (int i = 0; i < Statistic.values().length - 1; i++){
            float statValue = individualStatistics.get(i).getStatisticValue();
            Label label = new Label(formatLabelText(statistics[i], statValue));
            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
            statList.add(label);
        }
    }

    private String formatLabelText(Statistic statistic, float value) {
        if (isThisSecondPlayer) {
            return value + " " + statistic;
        } else {
            return statistic + " " + value;
        }
    }

    public Label getLabelFromListOfLabels(int index){
        ObservableList<Node> list = getChildren();
        return (Label) list.get(index);
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }
}
