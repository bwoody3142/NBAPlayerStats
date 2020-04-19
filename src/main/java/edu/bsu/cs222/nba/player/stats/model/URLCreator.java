package edu.bsu.cs222.nba.player.stats.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLCreator {
    public URLCreator(){
    }

    public static URLCreator createEmptyUrl() {
        return new URLCreator();
    }

    public URL getPlayerListUrl(Integer year) throws IOException {
        String encodedYear = URLEncoder.encode(year.toString(), StandardCharsets.UTF_8);
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/players.json";
        return new URL(search);
    }

    public InputStream createPlayerListStream(Integer year) throws IOException {
        return getPlayerListUrl(year).openConnection().getInputStream();
    }

    public URL getPlayerProfileUrl(Integer personID) throws IOException {
        URLFactory factory = URLFactory.withYear(2019).andPersonID(personID);
        String encodedYear = URLEncoder.encode(factory.getYear().toString(), StandardCharsets.UTF_8);
        String encodedPersonID = URLEncoder.encode(factory.getPersonID().toString(), StandardCharsets.UTF_8);
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/players/" + encodedPersonID + "_profile.json";
        return new URL(search);
    }

    public InputStream createPlayerProfileStream(Integer personID) throws IOException {
        return getPlayerProfileUrl(personID).openConnection().getInputStream();
    }

    public URL getTeamListUrl(Integer year) throws IOException {
        String encodedYear = URLEncoder.encode(year.toString(), StandardCharsets.UTF_8);
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/teams.json";
        return new URL(search);
    }

    public InputStream createTeamListStream(Integer year) throws IOException {
        return getTeamListUrl(year).openConnection().getInputStream();
    }

    public URL getTeamRosterUrl(String urlName) throws IOException {
        String encodedUrlName = URLEncoder.encode(urlName, StandardCharsets.UTF_8);
        String search = "http://data.nba.net/data/10s/prod/v1/2019/teams/" + encodedUrlName +  "/roster.json";
        return new URL(search);
    }

    public InputStream createTeamRosterStream(String team) throws IOException {
        return getTeamRosterUrl(team).openConnection().getInputStream();
    }

    public URL getHeadshotUrl(Integer personID) throws IOException {
        String encodedPersonID = URLEncoder.encode(personID.toString(), StandardCharsets.UTF_8);
        String search = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/" + encodedPersonID + ".png";
        return new URL(search);
    }

    public InputStream createHeadshotStream(Integer personID) throws IOException {
        return getHeadshotUrl(personID).openConnection().getInputStream();
    }

    public URL getLogoUrl(String abbreviation) throws IOException {
        String encodedAbbreviation = URLEncoder.encode(abbreviation, StandardCharsets.UTF_8);
        String search = "https://www.nba.com/.element/img/1.0/teamsites/logos/teamlogos_500x500/"+encodedAbbreviation+".png";
        return new URL(search);
    }

    public InputStream createLogoStream(String abbreviation) throws IOException {
        return getLogoUrl(abbreviation).openConnection().getInputStream();
    }
}
