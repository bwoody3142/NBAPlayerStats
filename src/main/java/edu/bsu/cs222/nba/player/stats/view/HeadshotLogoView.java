package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class HeadshotLogoView extends StackPane {

    public static HeadshotLogoBuilder withTeam(String team){
        return new HeadshotLogoBuilder(team);
    }

    public static final class HeadshotLogoBuilder {

        private final String team;
        private String player;

        public HeadshotLogoBuilder(String team){
            this.team = team;
        }

        public HeadshotLogoView andPlayerName(String player) {
            this.player = player;
            return new HeadshotLogoView(this);
        }
    }

    private final URLCreator url = URLCreator.createEmptyUrl();
    private final ImageView headshotView = new ImageView();
    private final ImageView logoView = new ImageView();
    private final String team;
    private final String player;
    private final CurrentSeasonGenerator generator = new CurrentSeasonGenerator();
    private final int currentSeason = generator.generateCurrentSeason();

    public HeadshotLogoView(HeadshotLogoBuilder builder) {
        this.team = requireNonNull(builder.team);
        this.player = requireNonNull(builder.player);
        getChildren().addAll(logoView, headshotView);
    }

    public void formatFirstPane() {
        setHeight(1000);
        setWidth(1000);
        logoView.setFitHeight(90);
        logoView.setFitWidth(90);
        setAlignment(logoView, Pos.TOP_LEFT);
        setAlignment(headshotView, Pos.BOTTOM_RIGHT);
    }

    public void formatSecondPane() {
        setHeight(1000);
        setWidth(1000);
        logoView.setFitHeight(90);
        logoView.setFitWidth(90);
        setAlignment(logoView, Pos.TOP_RIGHT);
        setAlignment(headshotView, Pos.BOTTOM_LEFT);
    }

    public void generateLogo() throws IOException {
        TeamParser parser = TeamParser.withStream(url.createTeamListStream(currentSeason)).andFullTeamName(team);
        Team teamObject = parser.parse();
        InputStream logoStream = url.createLogoStream(teamObject.getAbbreviation());
        Image image = new Image(logoStream);
        logoView.setImage(image);
    }

    public void generateHeadshot() throws IOException {
        Map <String, Integer> fullPlayerMap = PlayerMap.createEmptyPlayerMap().createMapOfPlayersWithID();
        int personID = fullPlayerMap.get(player);
        InputStream headshotStream = url.createHeadshotStream(personID);
        Image headshot = new Image(headshotStream);
        headshotView.setImage(headshot);
    }
}
