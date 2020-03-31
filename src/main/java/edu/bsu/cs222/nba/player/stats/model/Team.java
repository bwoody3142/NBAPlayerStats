package edu.bsu.cs222.nba.player.stats.model;

public class Team {

    private String urlName;
    private String abbreviation;

    private Team(String urlName, String abbreviation){
        this.urlName = urlName;
        this.abbreviation = abbreviation;
    }

    public static Team create(String urlName, String abbreviation){
        return new Team(urlName, abbreviation);
    }

    public String getUrlName() {
        return urlName;
    }

    public String getAbbreviation() {
        return abbreviation.toLowerCase();
    }
}
