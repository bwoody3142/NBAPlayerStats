package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ControlPanel extends VBox {

    public interface PlayerStatsProductionListener {
        void onPlayerStatsProduced(ResultGenerationEvent generationEvent);
    }

    private final ComboBox<String> teams = new ComboBox<>();
    private final ComboBox<String> player = new ComboBox<>();
    private final ComboBox<String> season = new ComboBox<>();
    private final PlayerMap emptyPlayerMap = PlayerMap.createEmptyPlayerMap();
    private Map<String, Integer> fullPlayerMap = new HashMap<>();
    private ResultGenerationEvent resultGenerationEvent;
    private final URLCreator url = URLCreator.createEmptyUrl();
    private InputStream playerStream;
    private final List<PlayerStatsProductionListener> listeners = new ArrayList<>();
    private ResultGenerationEvent generationEvent;
    private final Executor executor = Executors.newCachedThreadPool();
    private final HBox teamBox;
    private final HBox playerBox;
    private final HBox seasonBox;
    private final Label loadingTeamsLabel = new Label(" Loading teams...");
    private final Label loadingRosterLabel = new Label(" Loading roster...");
    private final Label loadingInfoLabel = new Label(" Loading player's information...");
    private final CurrentSeasonGenerator generator = new CurrentSeasonGenerator();
    private final int currentSeason = generator.generateCurrentSeason();
    private Button getSeasonsButton;
    private Button getStatsButton;

    public ControlPanel() {
        setup();
        generateTeams();
        teamBox = createTeamBox();
        playerBox = createPlayerBox();
        seasonBox = createSeasonBox();
        getChildren().add(teamBox);
    }

    private void setup(){
        teams.setDisable(true);
        player.setDisable(true);
        season.setDisable(true);
    }

    public void addListeners(PlayerStatsProductionListener listener){
        listeners.add(listener);
    }

    public void fireEvent() {
        executor.execute(() -> {
            loadingInfoLabel.setVisible(true);
            generationEvent = generatePlayerStats();
            for (PlayerStatsProductionListener listener : listeners) {
                listener.onPlayerStatsProduced(generationEvent);
            }
            loadingInfoLabel.setVisible(false);
        });
    }

    public void generateTeams() {
        executor.execute(() -> {
            try {
                teams.setItems(FXCollections.observableList(getValidTeams()));
                teams.setDisable(false);
                loadingTeamsLabel.setVisible(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void generateRoster() {
        loadingRosterLabel.setVisible(true);
        player.setItems(null);
        getChildren().removeAll(playerBox, seasonBox);
        executor.execute(() -> {
            player.setDisable(true);
            try {
                player.setItems(FXCollections.observableList(getValidRoster()));
                player.setDisable(false);
                loadingRosterLabel.setVisible(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        getChildren().add(playerBox);
    }

    private void generateSeasons() {
        getChildren().remove(seasonBox);
        season.setDisable(true);
        try {
            fullPlayerMap = emptyPlayerMap.createMapOfPlayersWithID();
            season.setItems(FXCollections.observableArrayList(getValidSeasons()));
            season.setDisable(false);
            getChildren().add(seasonBox);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Please select a player!");
            alert.showAndWait();
        }
    }

    private ResultGenerationEvent generatePlayerStats() {
        try {
            resultGenerationEvent =
                ResultGenerationEvent.withCareerStats(parseStats(true))
                                     .seasonStats(parseStats(false))
                                     .andPlayerInfo(parsePlayerInfo());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultGenerationEvent;
    }

    private HBox createTeamBox(){
        Label teamLabel = new Label("Current Team ");
        teams.setPromptText("Select a Team");
        teams.setOnAction(event -> generateRoster());
        return new HBox(teamLabel, teams, loadingTeamsLabel);
    }

    private HBox createPlayerBox(){
        getSeasonsButton = new Button("Get Seasons!");
        Label playerLabel = new Label("Player ");
        player.setPromptText("Select a Player");
        playerComboBoxOnMouseClick();
        return new HBox(playerLabel, player, loadingRosterLabel);
    }

    private void playerComboBoxOnMouseClick() {
        player.setOnMouseClicked(event -> {
            teamBox.setDisable(true);
            seasonBox.setDisable(true);
            if (!(playerBox.getChildren().contains(getSeasonsButton))) {
                playerBox.getChildren().add(2, getSeasonsButton);
            }
            getSeasonsButtonOnAction();
        });
    }

    private void getSeasonsButtonOnAction() {
        getSeasonsButton.setOnAction(event -> {
            generateSeasons();
            playerBox.getChildren().remove(getSeasonsButton);
            teamBox.setDisable(false);
            seasonBox.setDisable(false);
        });
    }

    private HBox createSeasonBox(){
        loadingInfoLabel.setVisible(false);
        getStatsButton = new Button("Get Stats!");
        Label seasonLabel = new Label("Active Seasons ");
        season.setPromptText("Select a season");
        seasonComboBoxOnMouseClick();
        return new HBox(seasonLabel, season, loadingInfoLabel);
    }

    private void seasonComboBoxOnMouseClick() {
        season.setOnMouseClicked(event -> {
            teamBox.setDisable(true);
            playerBox.setDisable(true);
            if (!(seasonBox.getChildren().contains(getStatsButton))) seasonBox.getChildren().add(2, getStatsButton);
            getStatsButtonOnAction();
        });
    }

    private void getStatsButtonOnAction() {
        getStatsButton.setOnAction(e -> {
            fireEvent();
            seasonBox.getChildren().remove(getStatsButton);
            teamBox.setDisable(false);
            playerBox.setDisable(false);
        });
    }


    private List<String> getValidTeams() throws IOException {
        ListOfTeams teamList = ListOfTeams.getNewListOfTeams();
        return teamList.createFullListOfTeams(currentSeason);
    }

    private ObservableList<String> getValidRoster() throws IOException {
        TeamParser parser = TeamParser.withStream(url.createTeamListStream(currentSeason)).andFullTeamName(teams.getValue());
        Team team = parser.parse();
        TeamRoster roster = TeamRoster.createTeamRoster(team.getUrlName());
        Collection<String> collection = Collections.checkedCollection(roster.createRoster().values(), String.class);
        return FXCollections.observableArrayList(collection);
    }

    private List<String> getValidSeasons() {
        try {
            int personID = fullPlayerMap.get(player.getValue());
            playerStream = url.createPlayerProfileStream(personID);
        } catch (NullPointerException | IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please make sure you have selected a player!");
            alert.showAndWait();
        }
        return ActiveSeasonsMap.create().getSeasonsAsList(playerStream);
    }

    private PlayerStats parseStats(boolean trueFalse) throws IOException{
        playerStream = url.createPlayerProfileStream(fullPlayerMap.get(player.getValue()));
        Map<String, Integer> map = ActiveSeasonsMap.create().createActiveSeasonsMap(playerStream);
        playerStream = url.createPlayerProfileStream(fullPlayerMap.get(player.getValue()));
        return PlayerParser.isCareer(trueFalse).withStream(playerStream).andYear(map.get(season.getValue())).parseForStats();
    }

    private PlayerInfo parsePlayerInfo() throws IOException{
        InputStream playerListStream = url.createPlayerListStream(currentSeason);
        int personID = fullPlayerMap.get(player.getValue());
        return PlayerInfoParser.withStream(playerListStream).andPersonID(personID).parseForPlayerInfo();
    }

    public String getTeam(){
        return teams.getValue();
    }

    public String getPlayer(){
        return player.getValue();
    }

    public String getSeason(){
        return season.getValue();
    }
}
