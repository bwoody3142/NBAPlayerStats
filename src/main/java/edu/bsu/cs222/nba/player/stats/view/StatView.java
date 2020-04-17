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
    private boolean isThisSecondPlayer = false;

    public StatView(PlayerStats playerStats) {
        checkForFirstOrSecondPlayer(playerStats);
        changeFont();
        getChildren().addAll(getStatsList());
        setStatsPaneConstraints();
    }

    private void checkForFirstOrSecondPlayer(PlayerStats playerStats){
        if (!isThisSecondPlayer){
            checkForFirstSeasonOrCareerStats(playerStats);
            isThisSecondPlayer = true;
        } else {
            checkForSecondSeasonOrCareerStats(playerStats);
        }
    }

    private void checkForFirstSeasonOrCareerStats(PlayerStats playerStats){
        if(playerStats.getSeasonTurnOversPerGame() == 0){
            createFirstCareerStatLabels(playerStats);
        } else {
            createFirstSeasonStatLabels(playerStats);
        }
    }

    private void checkForSecondSeasonOrCareerStats(PlayerStats playerStats){
        if(playerStats.getSeasonTurnOversPerGame() == 0){
            createSecondCareerStatLabels(playerStats);
        } else {
            createSecondSeasonStatLabels(playerStats);
        }
    }

    private void createFirstSeasonStatLabels(PlayerStats playerStats) {
        ppgLabel = new Label("PPG " + playerStats.getPointsPerGame());
        apgLabel = new Label("APG  " + playerStats.getAssistsPerGame());
        rpgLabel = new Label("RPG  " + playerStats.getReboundsPerGame());
        topgLabel = new Label("TOPG  " + playerStats.getSeasonTurnOversPerGame());
        spgLabel = new Label("SPG  " + playerStats.getStealsPerGame());
        bpgLabel = new Label("BPG  " + playerStats.getBlocksPerGame());
        fgpLabel = new Label("FGP  " + playerStats.getFieldGoalPercentage());
        ftpLabel = new Label("FTP  " + playerStats.getFreeThrowPercentage());
        tpmLabel = new Label("TPM  " + playerStats.getThreePointers());
    }

    private void createFirstCareerStatLabels(PlayerStats playerStats) {
        ppgLabel = new Label("PPG " + playerStats.getPointsPerGame());
        apgLabel = new Label("APG  " + playerStats.getAssistsPerGame());
        rpgLabel = new Label("RPG  " + playerStats.getReboundsPerGame());
        topgLabel = new Label("TOPG  " + playerStats.getCareerTurnOversPerGame());
        spgLabel = new Label("SPG  " + playerStats.getStealsPerGame());
        bpgLabel = new Label("BPG  " + playerStats.getBlocksPerGame());
        fgpLabel = new Label("FGP  " + playerStats.getFieldGoalPercentage());
        ftpLabel = new Label("FTP  " + playerStats.getFreeThrowPercentage());
        tpmLabel = new Label("TPM  " + playerStats.getThreePointers());
    }

    private void createSecondSeasonStatLabels(PlayerStats playerStats) {
        ppgLabel = new Label(playerStats.getPointsPerGame() + "  PPG");
        apgLabel = new Label(playerStats.getAssistsPerGame() + "  APG");
        rpgLabel = new Label(playerStats.getReboundsPerGame() + "  RPG");
        topgLabel = new Label(playerStats.getSeasonTurnOversPerGame() + "  TOPG");
        spgLabel = new Label(playerStats.getStealsPerGame() + "  SPG");
        bpgLabel = new Label(playerStats.getBlocksPerGame() + "  BPG");
        fgpLabel = new Label(playerStats.getFieldGoalPercentage() + "  FGP");
        ftpLabel = new Label(playerStats.getFreeThrowPercentage() + "  FTP");
        tpmLabel = new Label(playerStats.getThreePointers() + "  TPM");
    }

    private void createSecondCareerStatLabels(PlayerStats playerStats) {
        ppgLabel = new Label(playerStats.getPointsPerGame() + "  PPG");
        apgLabel = new Label(playerStats.getAssistsPerGame() + "  APG");
        rpgLabel = new Label(playerStats.getReboundsPerGame() + "  RPG");
        topgLabel = new Label(playerStats.getCareerTurnOversPerGame() + "  TOPG");
        spgLabel = new Label(playerStats.getStealsPerGame() + "  SPG");
        bpgLabel = new Label(playerStats.getBlocksPerGame() + "  BPG");
        fgpLabel = new Label(playerStats.getFieldGoalPercentage() + "  FGP");
        ftpLabel = new Label(playerStats.getFreeThrowPercentage() + "  FTP");
        tpmLabel = new Label(playerStats.getThreePointers() + "  TPM");
    }

    private ObservableList<Label> getStatsList(){
        return FXCollections.observableArrayList(ppgLabel, apgLabel, rpgLabel, topgLabel,
                spgLabel, bpgLabel, fgpLabel, ftpLabel, tpmLabel);
    }

    private void setStatsPaneConstraints(){
        for (int i = 0; i < getStatsList().size(); i++){
            setConstraints(getStatsList().get(i), 0, i);
        }
    }

    private void changeFont(){
        for (int i = 0; i < getStatsList().size(); i++){
            getStatsList().get(i).setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        }
    }
}
