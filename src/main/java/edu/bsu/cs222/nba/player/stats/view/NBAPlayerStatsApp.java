package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class NBAPlayerStatsApp extends Application {

    private Label teamLabel;
    private Label nameLabel;
    private Label yearLabel;
    private ComboBox<String> teams = new ComboBox<>();
    private ComboBox<String> player = new ComboBox<>();
    private ComboBox<Integer> year = new ComboBox<>();
    private GridPane pane = new GridPane();
    private GridPane statsPane = new GridPane();
    private final ListOfPlayers playerList = ListOfPlayers.createEmptyListOfPlayers();
    private Map<String, String> fullPlayerList = new HashMap<>();
    private URLCreator url = URLCreator.createEmptyUrl();
    private InputStream playerStream;
    private ImageView headshotView = new ImageView();
    private ImageView logoView = new ImageView();
    private PlayerStats playerStats;

    public NBAPlayerStatsApp() {
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("NBA Player Stats");
        createInputLabels();
        setAllPromptText();
        player.setDisable(true);
        year.setDisable(true);
        teams.setItems(FXCollections.observableList(getValidTeams()));
        teams.setOnAction(event -> {

            try {
                player.setDisable(false);
                player.setItems(FXCollections.observableList(getValidRoster()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        player.setOnAction(event -> {
            try {
                year.setDisable(false);
                fullPlayerList = playerList.createFullListOfPlayers();
                year.setItems(FXCollections.observableArrayList(getValidSeasons()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        year.setOnAction(event -> {
            statsPane.getChildren().clear();
            try {
                setStats();
                StatView statView = new StatView(playerStats);
                statsPane.getChildren().add(statView);
                getHeadshot();
                getLogo();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText("Error");
                alert.setResizable(true);
                alert.showAndWait();
            }
        });

        pane.setPadding(new Insets(20,20,20,20));
        pane.setVgap(10);
        pane.setHgap(10);

        GridPane.setConstraints(headshotView,0,0);
        GridPane.setConstraints(logoView,1,0);
        GridPane.setConstraints(statsPane,2,0);
        pane.getChildren().addAll(logoView, headshotView, statsPane);

        statsPane.setPadding(new Insets(20,20,20,20));
        statsPane.setVgap(10);
        statsPane.setHgap(10);

        VBox parent = new VBox(createInputBox(), pane);
        Scene scene = new Scene(parent, 700, 450);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createInputBox() {
        HBox teamBox = new HBox(teamLabel, teams);
        HBox nameBox = new HBox(nameLabel, player);
        HBox yearBox = new HBox(yearLabel, year);
        return new VBox(teamBox, nameBox, yearBox);
    }

    private void getLogo() throws IOException {
        TeamParser parser = TeamParser.withStream(url.createTeamListStream(2019)).andFullName(teams.getValue());
        Team team = parser.parse();
        InputStream logoStream = url.createLogoStream(team.getAbbreviation());
        Image logo = new Image(logoStream);
        logoView.setFitHeight(200);
        logoView.setFitWidth(200);
        logoView.setImage(logo);
    }

    private List<String> getValidTeams() throws IOException {
        ListOfTeams teamList = ListOfTeams.getNewListOfTeams();
        return teamList.createFullListOfTeams(2019);
    }

    private ObservableList<String> getValidRoster() throws IOException {
        TeamParser parser = TeamParser.withStream(url.createTeamListStream(2019)).andFullName(teams.getValue());
        Team team = parser.parse();
        TeamRoster roster = TeamRoster.createTeamRoster(team.getUrlName());
        Collection<String> collection = Collections.checkedCollection(roster.createRoster().values(), String.class);
        return FXCollections.observableArrayList(collection);
    }

    private List<Integer> getValidSeasons() throws IOException {
        playerStream = url.createPlayerProfileStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        return SeasonGenerator.create().getYearsAsList(playerStream);
    }

    private void setStats() throws IOException {
        playerStream = url.createPlayerProfileStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        playerStats = PlayerParser.withStream(playerStream).andYear(year.getValue()).parseForSeasonStats();
    }

    private void getHeadshot() throws IOException {
        InputStream headshotStream = url.createHeadshotStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        Image headshot = new Image(headshotStream);
        headshotView.setImage(headshot);
    }

    private void setAllPromptText() {
        teams.setPromptText("Select a Team");
        player.setPromptText("Select a Player");
        year.setPromptText("Select a season");
    }

    private void createInputLabels() {
        teamLabel = new Label("Team ");
        nameLabel = new Label("Player ");
        yearLabel = new Label("Active Seasons ");
    }
}
