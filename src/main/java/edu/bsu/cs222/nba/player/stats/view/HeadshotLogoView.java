package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.ListOfPlayers;
import edu.bsu.cs222.nba.player.stats.model.Team;
import edu.bsu.cs222.nba.player.stats.model.TeamParser;
import edu.bsu.cs222.nba.player.stats.model.URLCreator;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HeadshotLogoView extends GridPane {

    public static HeadshotLogoBuilder withTeam(String team){
        return new HeadshotLogoBuilder(team);
    }

    public static final class HeadshotLogoBuilder {

        private String team;
        private String player;

        public HeadshotLogoBuilder(String team){
            this.team = team;
        }

        public HeadshotLogoView andPlayerName(String player) {
            this.player = player;
            return new HeadshotLogoView(this);
        }
    }

    private URLCreator url = URLCreator.createEmptyUrl();
    private ImageView headshotView = new ImageView();
    private ImageView logoView = new ImageView();
    private String team;
    private String player;

    public HeadshotLogoView(HeadshotLogoBuilder builder) {
        this.team = builder.team;
        this.player = builder.player;
        formatPane();
        getChildren().addAll(logoView, headshotView);
    }

    private void formatPane() {
        setPadding(new Insets(20,20,20,20));
        setVgap(10);
        setHgap(10);
        setConstraints(headshotView,0,0);
        setConstraints(logoView,1,0);
    }

    public ImageView generateLogo() throws IOException {
        TeamParser parser = TeamParser.withStream(url.createTeamListStream(2019)).andFullTeamName(team);
        Team teamObject = parser.parse();
        InputStream logoStream = url.createLogoStream(teamObject.getAbbreviation());
        Image image = new Image(logoStream);
        logoView.setFitHeight(200);
        logoView.setFitWidth(200);
        logoView.setImage(image);
        return logoView;
    }

    public void generateHeadshot() throws IOException {
        Map <String, String> fullPlayerList = ListOfPlayers.createEmptyListOfPlayers().createFullListOfPlayers();
        InputStream headshotStream = url.createHeadshotStream(Integer.parseInt(fullPlayerList.get(player)));
        Image headshot = new Image(headshotStream);
        headshotView.setImage(headshot);
    }
}
