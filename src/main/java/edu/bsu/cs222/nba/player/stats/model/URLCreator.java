package edu.bsu.cs222.nba.player.stats.model;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class URLCreator {
    public URLCreator(){}

    public static URLCreator createEmptyUrl() {
        return new URLCreator();
    }

    public URL getPlayerListUrl(Integer year) throws Exception {
        String encodedYear = URLEncoder.encode(year.toString(),"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/players.json";
        return new URL(search);
    }

    public InputStream createPlayerListStream(Integer year) throws Exception {
        return getPlayerListUrl(year).openConnection().getInputStream();
    }

    public URL getPlayerProfileUrl(Integer personID) throws Exception {
        URLFactory factory = URLFactory.withYear(2019).andPersonID(personID);
        String encodedYear = URLEncoder.encode(factory.getYear().toString(),"UTF-8");
        String encodedPersonID = URLEncoder.encode(factory.getPersonID().toString(),"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/players/" + encodedPersonID + "_profile.json";
        return new URL(search);
    }

    public InputStream createPlayerProfileStream(Integer personID) throws Exception {
        return getPlayerProfileUrl(personID).openConnection().getInputStream();
    }

    public URL getTeamListUrl(Integer year) throws Exception {
        String encodedYear = URLEncoder.encode(year.toString(),"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/teams.json";
        return new URL(search);
    }

    public InputStream createTeamListStream(Integer year) throws Exception {
        return getTeamListUrl(year).openConnection().getInputStream();
    }

    public URL getTeamRosterUrl(String team) throws Exception {
        String encodedTeam = URLEncoder.encode(team,"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/2019/teams/" + encodedTeam +  "/roster.json";
        return new URL(search);
    }

    public InputStream createTeamRosterStream(String team) throws Exception {
        return getTeamRosterUrl(team).openConnection().getInputStream();
    }

    public URL getHeadshotUrl(Integer personID) throws Exception {
        String encodedTeam = URLEncoder.encode(personID.toString(),"UTF-8");
        String search = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/" + encodedTeam + ".png";
        return new URL(search);
    }

    public InputStream createHeadshotStream(Integer personID) throws Exception {
        return getHeadshotUrl(personID).openConnection().getInputStream();
    }
}
