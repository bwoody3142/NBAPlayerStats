package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.Map;

public class NBAPlayerStatsApp extends Application{

    private final TextField nameField = new TextField();
    private final TextField yearField = new TextField();
    private final TextArea statsArea = new TextArea();
    private final ListOfPlayers list = ListOfPlayers.createEmptyListOfPlayers();
    private final URLCreator url = URLCreator.createEmptyUrl();
    private URLFactory.URLFactoryBuilder builder;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("NBA Player Stats");
        Label nameLabel = new Label("Name of Player  ");
        Label yearLabel = new Label("Year  ");
        Button button = new Button("Get Stats");
        statsArea.setEditable(false);
        button.setOnAction(event -> {
            try {
                builder = buildUrlFactory(new URLFactory.URLFactoryBuilder());
                InputStream stream = url.createPlayerProfileStream(builder);
                PlayerStats playerStats = PlayerParser.withStream(stream).andYear(Integer.parseInt(yearField.getText())).parse();
                statsArea.setText(playerStats.toString());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alert");
                alert.setHeaderText("You have entered an invalid name or year \n" +
                        "Examples for a name: 'LeBron James', 'Dennis Smith Jr.', 'Glenn Robinson III'\n" +
                        "Example for year: '2019' (for the whole 2019-2020 regular season)");
                alert.setContentText(nameField.getText());
                alert.setResizable(true);
                alert.showAndWait();
            }
        });
        HBox nameBox = new HBox(nameLabel,nameField);
        HBox yearBox = new HBox(yearLabel, yearField);
        HBox output = new HBox(statsArea);
        VBox vbox = new VBox(nameBox, yearBox, output, button);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private URLFactory.URLFactoryBuilder buildUrlFactory(URLFactory.URLFactoryBuilder builder) throws Exception {
        Map<String, String> listOfPlayers = list.createFullListOfPlayers(Integer.parseInt(yearField.getText()));
        builder.setYear(Integer.parseInt(yearField.getText()));
        builder.setPersonID(Integer.parseInt(listOfPlayers.get(nameField.getText())));
        return builder;
    }
}
