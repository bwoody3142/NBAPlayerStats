package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.*;

public class NBAPlayerStatsApp extends Application{

    private Label teamLabel;
    private Label nameLabel;
    private Label yearLabel;
    private Scene outputScene;
    private ComboBox<String> teams = new ComboBox<>();
    private ComboBox<String> player = new ComboBox<>();
    private ComboBox<Integer> year = new ComboBox<>();
    private final TextArea statsArea = new TextArea();
    private final ListOfPlayers playerList = ListOfPlayers.createEmptyListOfPlayers();
    private Map<String, String> fullPlayerList = new HashMap<>();
    private final URLCreator url = URLCreator.createEmptyUrl();
    private InputStream playerStream;
    private InputStream headshotStream;
    private Image headshot;
    private ImageView headshotView = new ImageView();
    private Button rosterButton;
    private Button seasonButton;
    private Button statsButton;


    public NBAPlayerStatsApp() {
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("NBA Player Stats");
        createLabels();
        setAllPromptText();
        createButtons();
        setUneditable();
        teams.setItems(FXCollections.observableList(getValidTeams()));
        rosterButton.setOnAction(event -> {
            try {
                player.setItems(FXCollections.observableList(getValidRoster()));
                seasonButton.setDisable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        seasonButton.setOnAction(event -> {
            try {
                fullPlayerList = playerList.createFullListOfPlayers();
                year.setItems(FXCollections.observableArrayList(getVaildSeasons()));
                statsButton.setDisable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        statsButton.setOnAction(event -> {
            stage.setScene(outputScene);
            try {
                setStats();
                getHeadshot();
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

        HBox teamBox = new HBox(teamLabel, teams, rosterButton);
        HBox nameBox = new HBox(nameLabel, player, seasonButton);
        HBox yearBox = new HBox(yearLabel, year);
        VBox inputBox = new VBox(teamBox, nameBox, yearBox, statsButton);

        HBox resultBox = new HBox(headshotView, statsArea);
        Scene inputScene = new Scene(inputBox);
        outputScene = new Scene(resultBox);
        stage.setScene(inputScene);
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

    private List<Integer> getVaildSeasons() throws Exception {
        playerStream = url.createPlayerProfileStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        return SeasonGenerator.create().getYearsAsList(playerStream);
    }

    private void setStats() throws Exception {
        playerStream = url.createPlayerProfileStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        PlayerStats playerStats = PlayerParser.withStream(playerStream).andYear(year.getValue()).parse();
        statsArea.setText(playerStats.toString());
    }

    private void getHeadshot() throws Exception {
        headshotStream = url.createHeadshotStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        headshot = new Image(headshotStream);
        headshotView.setImage(headshot);
    }

    private void setUneditable() {
        player.setEditable(false);
        year.setEditable(false);
        statsArea.setEditable(false);
        seasonButton.setDisable(true);
        statsButton.setDisable(true);
    }

    private void createButtons() {
        rosterButton = new Button("Get Roster");
        seasonButton = new Button("Get Seasons");
        statsButton = new Button("Get Stats");
    }

    private void setAllPromptText() {
        teams.setPromptText("Select a Team");
        player.setPromptText("Select a Player");
        year.setPromptText("Select a season");
    }

    private void createLabels() {
        teamLabel = new Label("Team ");
        nameLabel = new Label("Player");
        yearLabel = new Label("Active Seasons ");
    }
}
