package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.PlayerStats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
    private PlayerStats playerStats;
    private boolean isThisSecondPlayer = false;

    public StatView(PlayerStats playerStats) {
        this.playerStats = playerStats;
        checkForFirstOrSecondPlayer();
        changeFont();
        getChildren().addAll(getStatsList());
    }

    private void checkForFirstOrSecondPlayer(){
        if (!isThisSecondPlayer){
            checkForFirstSeasonOrCareerStats();
            isThisSecondPlayer = true;
        } else {
            checkForSecondSeasonOrCareerStats();
        }
    }

    private void checkForFirstSeasonOrCareerStats(){
        if(playerStats.getSeasonTurnOversPerGame() == 0){
            createFirstCareerStatLabels();
        } else {
            createFirstSeasonStatLabels();
        }
    }

    private void checkForSecondSeasonOrCareerStats(){
        if(playerStats.getSeasonTurnOversPerGame() == 0){
            createSecondCareerStatLabels();
        } else {
            createSecondSeasonStatLabels();
        }
    }

    private void createFirstSeasonStatLabels() {

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

    private void createFirstCareerStatLabels() {
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

    private void createSecondSeasonStatLabels() {
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

    private void createSecondCareerStatLabels() {
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

    private void changeFont(){
        for (int i = 0; i < getStatsList().size(); i++){
            getStatsList().get(i).setFont(Font.font("Times New Roman", FontWeight.NORMAL, 18));
        }
    }

    public Label getPpgLabel() {
        return ppgLabel;
    }

    public Label getApgLabel() {
        return apgLabel;
    }

    public Label getRpgLabel() {
        return rpgLabel;
    }

    public Label getTopgLabel() {
        return topgLabel;
    }

    public Label getSpgLabel() {
        return spgLabel;
    }

    public Label getBpgLabel() {
        return bpgLabel;
    }

    public Label getFgpLabel() {
        return fgpLabel;
    }

    public Label getFtpLabel() {
        return ftpLabel;
    }

    public Label getTpmLabel() {
        return tpmLabel;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    public List<Label> getListOfLabels(){
        return Arrays.asList(getPpgLabel(),
                getApgLabel(),
                getRpgLabel(),
                getTopgLabel(),
                getSpgLabel(),
                getBpgLabel(),
                getFgpLabel(),
                getFtpLabel(),
                getTpmLabel());
    }
}
