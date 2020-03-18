package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.*;

public class NBAPlayerStatsApp extends Application{

    private ComboBox<String> teams;
    private ComboBox<String> player = new ComboBox<>();
    private ComboBox<Integer> year = new ComboBox<>();
    private final TextArea statsArea = new TextArea();
    private final ListOfPlayers playerList = ListOfPlayers.createEmptyListOfPlayers();
    private Map<String, String> fullPlayerList = new HashMap<>();
    private final URLCreator url = URLCreator.createEmptyUrl();
    private InputStream stream;

    public NBAPlayerStatsApp() {
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("NBA Player Stats");
        Label teamLabel = new Label("Team ");
        Label nameLabel = new Label("Player");
        Label yearLabel = new Label("Year ");
        teams = new ComboBox<>(FXCollections.observableList(getValidTeams()));
        teams.setPromptText("Select a Team");
        Button rosterButton = new Button("Get Roster");
        Button seasonButton = new Button("Get Seasons");
        Button statsButton = new Button("Get Stats");
        statsArea.setEditable(false);

        rosterButton.setOnAction(event -> {
            player.setPromptText("Select a Player!");
            try {
                player.setItems(FXCollections.observableList(getValidRoster()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        seasonButton.setOnAction(event -> {
            year.setPromptText("Select a season");
            try {
                fullPlayerList = playerList.createFullListOfPlayers();
                stream = url.createPlayerProfileStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
                year.setItems(FXCollections.observableArrayList(getVaildSeasons()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        statsButton.setOnAction(event -> {
            try {
                PlayerStats playerStats = PlayerParser.withStream(stream).andYear(year.getValue()).parse();
                statsArea.setText(playerStats.toString());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText("You have entered an invalid name or year \n" +
                        "Examples for a name: 'LeBron James', 'Dennis Smith Jr.', 'Glenn Robinson III'\n" +
                        "Example for year: '2019' (for the whole 2019-2020 regular season)");
                alert.setContentText(player.getValue());
                alert.setResizable(true);
                alert.showAndWait();
            }
        });

        HBox teamBox = new HBox(teamLabel,teams,rosterButton);
        HBox nameBox = new HBox(nameLabel,player, seasonButton);
        HBox yearBox = new HBox(yearLabel, year);
        HBox output = new HBox(statsArea);
        VBox parent = new VBox(teamBox, nameBox, yearBox, output, statsButton);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    private List<String> getValidTeams() throws Exception {
        ListOfTeams teamList = ListOfTeams.getNewListOfTeams();
        return teamList.createFullListOfTeams(2019);
    }

    private ObservableList<String> getValidRoster() throws Exception {
        TeamRoster roster = TeamRoster.createTeamRoster(teams.getValue());
        Collection<String> collection = Collections.checkedCollection(roster.createRoster().values(), String.class);
        return FXCollections.observableArrayList(collection);
    }

    private List<Integer> getVaildSeasons(){
        return SeasonGenerator.create().getYearsAsList(stream);

    }
}
