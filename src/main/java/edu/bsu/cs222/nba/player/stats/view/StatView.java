package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.PlayerStats;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.*;

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
        getChildren().addAll(ppgLabel, apgLabel, rpgLabel, topgLabel,
                spgLabel, bpgLabel, fgpLabel, ftpLabel, tpmLabel);
        setStatsPaneConstraints();
    }

    public void setStatsPaneConstraints(){
        GridPane.setConstraints(ppgLabel,0,0);
        GridPane.setConstraints(apgLabel,0,1);
        GridPane.setConstraints(rpgLabel,0,2);
        GridPane.setConstraints(topgLabel,0,3);
        GridPane.setConstraints(spgLabel,0,4);
        GridPane.setConstraints(bpgLabel,0,5);
        GridPane.setConstraints(fgpLabel,0,6);
        GridPane.setConstraints(ftpLabel,0,7);
        GridPane.setConstraints(tpmLabel,0,8);
    }

    private void changeFont(){
        ppgLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        apgLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        rpgLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        topgLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        spgLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        bpgLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        fgpLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        ftpLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        tpmLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
    }

}
