package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.PlayerStats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Arrays;
import java.util.List;

public class StatView extends VBox {

    private Label ppgLabel;
    private Label apgLabel;
    private Label rpgLabel;
    private Label topgLabel;
    private Label spgLabel;
    private Label bpgLabel;
    private Label fgpLabel;
    private Label ftpLabel;
    private Label tpmLabel;
    private final PlayerStats playerStats;
    private final boolean isThisSecondPlayer;

    public StatView(PlayerStats playerStats, boolean isThisSecondPlayer) {
        this.playerStats = playerStats;
        this.isThisSecondPlayer = isThisSecondPlayer;
        makeStatLabels();
        setupLabels();
        getChildren().addAll(getStatsList());
    }

    public void makeStatLabels() {
        ppgLabel = new Label(formatLabelText("PPG", playerStats.getPointsPerGame()));
        apgLabel = new Label(formatLabelText("APG", playerStats.getAssistsPerGame()));
        rpgLabel = new Label(formatLabelText("RPG", playerStats.getReboundsPerGame()));
        topgLabel = new Label(formatLabelText("TOPG", playerStats.getTurnOversPerGame()));
        spgLabel = new Label(formatLabelText("SPG", playerStats.getStealsPerGame()));
        bpgLabel = new Label(formatLabelText("BPG", playerStats.getBlocksPerGame()));
        fgpLabel = new Label(formatLabelText("FGP", playerStats.getFieldGoalPercentage()));
        ftpLabel = new Label(formatLabelText("FTP", playerStats.getFreeThrowPercentage()));
        tpmLabel = new Label(formatLabelText("TPM", playerStats.getThreePointers()));
    }

    private String formatLabelText(String stat, float value) {
        if (isThisSecondPlayer) {
            return value + " " + stat;
        } else {
            return stat + " " + value;
        }
    }

    private ObservableList<Label> getStatsList(){
        return FXCollections.observableArrayList(ppgLabel, apgLabel, rpgLabel, topgLabel,
                spgLabel, bpgLabel, fgpLabel, ftpLabel, tpmLabel);
    }

    private void setupLabels(){
        for (int i = 0; i < getStatsList().size(); i++){
            Label label = getStatsList().get(i);
            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
            label.setTextFill(Color.BLACK);
        }
    }

    public List<Label> getListOfLabels(){
        return Arrays.asList(ppgLabel,
                             apgLabel,
                             rpgLabel,
                             topgLabel,
                             spgLabel,
                             bpgLabel,
                             fgpLabel,
                             ftpLabel,
                             tpmLabel);
    }

    public Label getLabelFromListOfLabels(int index){
        List<Label> list = getListOfLabels();
        return list.get(index);
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }
}
