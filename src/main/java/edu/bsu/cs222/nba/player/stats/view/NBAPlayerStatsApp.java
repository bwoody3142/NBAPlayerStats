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

    private ChoiceBox<String> teams;
    private ChoiceBox<String> player = new ChoiceBox<>();
    private final ChoiceBox<String> year = new ChoiceBox<>(FXCollections.observableArrayList("2019"));
    private final TextArea statsArea = new TextArea();
    private final ListOfPlayers playerList = ListOfPlayers.createEmptyListOfPlayers();
    private final URLCreator url = URLCreator.createEmptyUrl();
    private URLFactory.URLFactoryBuilder builder;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("NBA Player Stats");
        Label teamLabel = new Label("Team ");
        Label nameLabel = new Label("Player");
        Label yearLabel = new Label("Year ");
        teams = new ChoiceBox<>(FXCollections.observableList(getValidTeams()));
        Button rosterButton = new Button("Get roster");
        Button statsButton = new Button("Get Stats");
        statsArea.setEditable(false);

        rosterButton.setOnAction(event -> {
            try {
                player.setItems(FXCollections.observableList(getValidRoster()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        statsButton.setOnAction(event -> {
            try {
                builder = buildUrlFactory(new URLFactory.URLFactoryBuilder());
                InputStream stream = url.createPlayerProfileStream(builder);
                PlayerStats playerStats = PlayerParser.withStream(stream).andYear(getAsInt(year)).parse();
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
        HBox nameBox = new HBox(nameLabel,player);
        HBox yearBox = new HBox(yearLabel, year);
        HBox output = new HBox(statsArea);
        VBox parent = new VBox(teamBox, nameBox, yearBox, output, statsButton);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    private URLFactory.URLFactoryBuilder buildUrlFactory(URLFactory.URLFactoryBuilder builder) throws Exception {
        Map<String, String> listOfPlayers = playerList.createFullListOfPlayers();
        builder.setYear(getAsInt(year));
        builder.setPersonID(Integer.parseInt(listOfPlayers.get(player.getValue())));
        return builder;
    }

    public List<String> getValidTeams() throws Exception {
        ListOfTeams teamList = ListOfTeams.getNewListOfTeams();
        return teamList.createFullListOfTeams(2019);
    }

    public ObservableList<String> getValidRoster() throws Exception {
        TeamRoster roster = TeamRoster.createTeamRoster(teams.getValue());
        Collection<String> collection = Collections.checkedCollection(roster.createRoster().values(), String.class);
        return FXCollections.observableArrayList(collection);
    }


    private Integer getAsInt(ChoiceBox<String> box){
        return Integer.parseInt(box.getValue());
    }
}
