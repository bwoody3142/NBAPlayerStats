package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.*;

public class NBAPlayerStatsApp extends Application{

    private Label teamLabel;
    private Label nameLabel;
    private Label yearLabel;
    private Label ppgLabel;
    private Label apgLabel;
    private Label rpgLabel;
    private Label topgLabel;
    private Label spgLabel;
    private Label bpgLabel;
    private Label fgpLabel;
    private Label ftpLabel;
    private Label tpmLabel;
    private ComboBox<String> teams = new ComboBox<>();
    private ComboBox<String> player = new ComboBox<>();
    private ComboBox<Integer> year = new ComboBox<>();
    private GridPane pane = new GridPane();
    private GridPane statsPane = new GridPane();
    private final ListOfPlayers playerList = ListOfPlayers.createEmptyListOfPlayers();
    private Map<String, String> fullPlayerList = new HashMap<>();
    private final URLCreator url = URLCreator.createEmptyUrl();
    private InputStream playerStream;
    private ImageView headshotView = new ImageView();
    private ImageView logoView = new ImageView();
    private Button rosterButton;
    private Button seasonButton;
    private Button statsButton;
    private PlayerStats playerStats;


    public NBAPlayerStatsApp() {
    }

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("NBA Player Stats");
        createInputLabels();
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
                year.setItems(FXCollections.observableArrayList(getValidSeasons()));
                statsButton.setDisable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        statsButton.setOnAction(event -> {
            statsPane.getChildren().clear();
            try {
                setStats();
                createStatsLabels();
                setStatsPaneConstraints();
                statsPane.getChildren().addAll(ppgLabel, apgLabel, rpgLabel, topgLabel,
                        spgLabel, bpgLabel, fgpLabel, ftpLabel, tpmLabel);
                getHeadshot();
                getLogo();

                // Dr. G. slush space here!

                // First, clear the results area
                //statsPane.getChildren().clear();

               // statsPane.getChildren().add(new PlayerStatsView(playerStats));
                // end

            } catch (Exception e) {
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
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createInputBox() {
        HBox teamBox = new HBox(teamLabel, teams, rosterButton);
        HBox nameBox = new HBox(nameLabel, player, seasonButton);
        HBox yearBox = new HBox(yearLabel, year);
        return new VBox(teamBox, nameBox, yearBox, statsButton);
    }

    private void getLogo() throws Exception {
        TeamParser parser = TeamParser.withStream(url.createTeamListStream(2019)).andFullName(teams.getValue());
        Team team = parser.parse();
        InputStream logoStream = url.createLogoStream(team.getAbbreviation());
        Image logo = new Image(logoStream);
        logoView.setFitHeight(200);
        logoView.setFitWidth(200);
        logoView.setImage(logo);
    }

    private List<String> getValidTeams() throws Exception {
        ListOfTeams teamList = ListOfTeams.getNewListOfTeams();
        return teamList.createFullListOfTeams(2019);
    }

    private ObservableList<String> getValidRoster() throws Exception {
        TeamParser parser = TeamParser.withStream(url.createTeamListStream(2019)).andFullName(teams.getValue());
        Team team = parser.parse();
        TeamRoster roster = TeamRoster.createTeamRoster(team.getUrlName());
        Collection<String> collection = Collections.checkedCollection(roster.createRoster().values(), String.class);
        return FXCollections.observableArrayList(collection);
    }

    private List<Integer> getValidSeasons() throws Exception {
        playerStream = url.createPlayerProfileStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        return SeasonGenerator.create().getYearsAsList(playerStream);
    }

    private void setStats() throws Exception {
        playerStream = url.createPlayerProfileStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        playerStats = PlayerParser.withStream(playerStream).andYear(year.getValue()).parse();
    }

    private void getHeadshot() throws Exception {
        InputStream headshotStream = url.createHeadshotStream(Integer.parseInt(fullPlayerList.get(player.getValue())));
        Image headshot = new Image(headshotStream);
        headshotView.setImage(headshot);
    }

    private void setUneditable() {
        player.setEditable(false);
        year.setEditable(false);
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

    private void createInputLabels() {
        teamLabel = new Label("Team ");
        nameLabel = new Label("Player");
        yearLabel = new Label("Active Seasons ");
    }

    private void createStatsLabels(){
        ppgLabel = new Label("PPG:  " + playerStats.getPointsPerGame());
        apgLabel = new Label("APG:  " + playerStats.getAssistsPerGame());
        rpgLabel = new Label("RPG:  " + playerStats.getReboundsPerGame());
        topgLabel = new Label("TOPG:  " + playerStats.getTurnOversPerGame());
        spgLabel = new Label("SPG:  " + playerStats.getStealsPerGame());
        bpgLabel = new Label("BPG:  " + playerStats.getBlocksPerGame());
        fgpLabel = new Label("FGP:  " + playerStats.getFieldGoalPercentage());
        ftpLabel = new Label("FTP:  " + playerStats.getFreeThrowPercentage());
        tpmLabel = new Label("TPM:  " + playerStats.getThreePointers());
    }

    private void setStatsPaneConstraints(){
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
}
