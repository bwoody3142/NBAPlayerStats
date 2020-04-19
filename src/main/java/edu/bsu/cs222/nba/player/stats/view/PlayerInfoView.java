package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.PlayerInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PlayerInfoView extends VBox {

    HBox nameJerseyPositionBox;
    Label name;
    Label jerseyNumber;
    Label position;
    Label yearsPro;
    Label height;
    Label weight;
    PlayerInfo playerInfo;

    private PlayerInfoView(PlayerInfo playerInfo){
        this.playerInfo = playerInfo;
        createInfoDisplay();
        changeFont();
        getChildren().addAll(getInfoList());
    }

    public static PlayerInfoView create(PlayerInfo playerInfo){
        return new PlayerInfoView(playerInfo);
    }

    private void createInfoDisplay(){
        name = new Label(playerInfo.getName());
        jerseyNumber = new Label("   #" + playerInfo.getJerseyNumber() + "   ");
        position = new Label(playerInfo.getPosition());
        yearsPro = new Label("Seasons in the NBA: " + playerInfo.getYearsPro());
        height = new Label("Height: " + playerInfo.getHeightFeet() + "' "
                + playerInfo.getHeightInches() + "\"");
        weight = new Label("Weight: " + playerInfo.getWeight() + " lbs");
        nameJerseyPositionBox = new HBox(name, jerseyNumber, position);
    }

    private ObservableList<Region> getInfoList(){
        return FXCollections.observableArrayList(nameJerseyPositionBox, yearsPro, height, weight);
    }

    private ObservableList<Label> getAllLabels(){
        return FXCollections.observableArrayList(name, jerseyNumber, position, yearsPro, height, weight);
    }

    private void changeFont(){
        for (int i = 0; i < getAllLabels().size(); i++){
            getAllLabels().get(i).setFont(Font.font("Times New Roman", FontWeight.NORMAL, 14));
        }
    }

    public HBox getNameJerseyPositionBox() {
        return nameJerseyPositionBox;
    }
}
