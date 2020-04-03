package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.PlayerStats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StatView extends GridPane {

    private Label ppgLabel;
    private Label apgLabel;
    private Label rpgLabel;
    private Label topgLabel;
    private Label spgLabel;
    private Label bpgLabel;
    private Label fgpLabel;
    private Label ftpLabel;
    private Label tpmLabel;

    public StatView(PlayerStats playerStats) {
        ppgLabel = new Label("PPG: " + playerStats.getPointsPerGame());
        apgLabel = new Label("APG:  " + playerStats.getAssistsPerGame());
        rpgLabel = new Label("RPG:  " + playerStats.getReboundsPerGame());
        topgLabel = new Label("TOPG:  " + playerStats.getTurnOversPerGame());
        spgLabel = new Label("SPG:  " + playerStats.getStealsPerGame());
        bpgLabel = new Label("BPG:  " + playerStats.getBlocksPerGame());
        fgpLabel = new Label("FGP:  " + playerStats.getFieldGoalPercentage());
        ftpLabel = new Label("FTP:  " + playerStats.getFreeThrowPercentage());
        tpmLabel = new Label("TPM:  " + playerStats.getThreePointers());
        changeFont();
        getChildren().addAll(getListOfLabels());
        setStatsPaneConstraints();
    }

    private ObservableList<Label> getListOfLabels(){
        return FXCollections.observableArrayList(ppgLabel, apgLabel, rpgLabel, topgLabel,
                spgLabel, bpgLabel, fgpLabel, ftpLabel, tpmLabel);
    }

    public void setStatsPaneConstraints(){
        for (int i = 0; i < getListOfLabels().size(); i++){
            GridPane.setConstraints(getListOfLabels().get(i),0,i);
        }
    }

    private void changeFont(){
        for (int i = 0; i < getListOfLabels().size(); i++){
            getListOfLabels().get(i).setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        }
    }

}