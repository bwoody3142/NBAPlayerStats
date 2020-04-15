package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        void onPlayerStatsProduced(PlayerStatsGenerationEvent generationEvent);
    }

    public final ComboBox<String> teams = new ComboBox<>();
    public final ComboBox<String> player = new ComboBox<>();
    private final ComboBox<Integer> season = new ComboBox<>();
    private final ListOfPlayers emptyListOfPlayers = ListOfPlayers.createEmptyListOfPlayers();
    private Map<String, String> fullPlayerList = new HashMap<>();
    private PlayerStatsGenerationEvent playerStatsGenerationEvent;
    private URLCreator url = URLCreator.createEmptyUrl();
    private InputStream playerStream;
    private List<PlayerStatsProductionListener> listeners = new ArrayList<>();
    private PlayerStatsGenerationEvent generationEvent;
    private Executor executor = Executors.newCachedThreadPool();

    public ControlPanel() throws IOException {
        player.setDisable(true);
        season.setDisable(true);
        generateTeams();
        VBox parent = createParentBox();
        getChildren().add(parent);
    }

    public void addListeners(PlayerStatsProductionListener listener){
        listeners.add(listener);
    }

    public void fireEvent() {
        executor.execute(() -> {
            generationEvent = generatePlayerStats();
            for (PlayerStatsProductionListener listener : listeners) {
                listener.onPlayerStatsProduced(generationEvent);
            }
        });
    }

    public void generateTeams() throws IOException {
        teams.setItems(FXCollections.observableList(getValidTeams()));
    }

    private void generateRoster() {
        try {
            player.setDisable(false);
            player.setItems(FXCollections.observableList(getValidRoster()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateSeasons() {
        try {
            season.setDisable(false);
            fullPlayerList = emptyListOfPlayers.createFullListOfPlayers();
            season.setItems(FXCollections.observableArrayList(getValidSeasons()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PlayerStatsGenerationEvent generatePlayerStats() {
        try {
            playerStatsGenerationEvent =
                    PlayerStatsGenerationEvent.withCareer(parseCareerStats()).andSeasonStats(parseSeasonStats());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return playerStatsGenerationEvent;
    }

    public VBox createParentBox() {
        HBox teamBox = createTeamBox();
        HBox playerBox = createPlayerBox();
        HBox seasonBox = createSeasonBox();
        return new VBox(teamBox, playerBox, seasonBox);
    }

    private HBox createTeamBox(){
        Label teamLabel = new Label("Team ");
        teams.setPromptText("Select a Team");
        teams.setOnAction(event -> generateRoster());
        return new HBox(teamLabel, teams);
    }

    private HBox createPlayerBox(){
        Label playerLabel = new Label("Player ");
        player.setPromptText("Select a Player");
        player.setOnAction(event -> generateSeasons());
        return new HBox(playerLabel, player);
    }

    private HBox createSeasonBox(){
        Button getStatsButton = new Button("Get Stats!");
        Label seasonLabel = new Label("Active Seasons ");
        season.setPromptText("Select a season");
        getStatsButton.setOnAction(event -> fireEvent());
        return new HBox(seasonLabel, season, getStatsButton);
    }


    private List<String> getValidTeams() throws IOException {
        ListOfTeams teamList = ListOfTeams.getNewListOfTeams();
        return teamList.createFullListOfTeams(2019);
    }

    private ObservableList<String> getValidRoster() throws IOException {
        TeamParser parser = TeamParser.withStream(url.createTeamListStream(2019)).andFullTeamName(teams.getValue());
        Team team = parser.parse();
        TeamRoster roster = TeamRoster.createTeamRoster(team.getUrlName());
        Collection<String> collection = Collections.checkedCollection(roster.createRoster().values(), String.class);
        return FXCollections.observableArrayList(collection);
    }

    private List<Integer> getValidSeasons() throws IOException {
        playerStream = url.createPlayerProfileStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        return ListOfActiveSeasons.create().getYearsAsList(playerStream);
    }

    private PlayerStats parseSeasonStats() throws IOException {
        playerStream = url.createPlayerProfileStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        return PlayerParser.withStream(playerStream).andYear(season.getValue()).parseForSeasonStats();
    }

    private PlayerStats parseCareerStats() throws IOException {
        playerStream = url.createPlayerProfileStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        return PlayerParser.withStream(playerStream).andYear(season.getValue()).parseForCareerStats();
    }
}
