package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.Map;

public class NBAPlayerStatsApp extends Application{

    private final TextField nameField = new TextField();
    private final TextField yearField = new TextField();
    private final TextArea stats = new TextArea();

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        stage.setTitle("NBA Player Stats");
        Label nameLabel = new Label("Name of Player ");
        Label yearLabel = new Label("Year    ");
        Button button = new Button("Get Stats");
        button.setOnAction(event -> {
            try {
                Integer year = Integer.parseInt(yearField.getText());
                ListOfPlayers list = ListOfPlayers.createEmptyListOfPlayers();
                final Map<String, String> listOfPlayers = list.createFullListOfPlayers(year);
                Integer personID = Integer.parseInt(listOfPlayers.get(nameField.getText()));
                URLCreator urlCreator = URLCreator.createEmptyUrl();
                URLFactory.URLFactoryBuilder builder = new URLFactory.URLFactoryBuilder();
                builder.setYear(year);
                builder.setPersonID(personID);
                InputStream stream = urlCreator.createPlayerProfileStream(builder);
                PlayerStats playerStats = PlayerParser.withStream(stream).andYear(year).parse();
                stats.setText(playerStats.toString());
            } catch (Exception e) {
                System.out.println("Error");
            }
        });

        HBox nameBox = new HBox(nameLabel,nameField);
        HBox yearBox = new HBox(yearLabel, yearField);
        HBox output = new HBox(stats);
        VBox vbox = new VBox(nameBox, yearBox, output, button);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }
}
