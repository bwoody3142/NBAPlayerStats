package edu.bsu.cs222.nba.player.stats.model;

public class Team {

    public static TeamBuilder withUrlName(String urlName) {
        return new TeamBuilder(urlName);
    }

    public static final class TeamBuilder {
        private final String urlName;
        private String abbreviation;

        public TeamBuilder(String urlName) {
            this.urlName = urlName;
        }

        public Team andAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation.toLowerCase();
            return create(this);
        }
    }

    private final String urlName;
    private final String abbreviation;

    private Team(TeamBuilder builder){
        this.urlName = builder.urlName;
        this.abbreviation = builder.abbreviation;
    }

    public static Team create(TeamBuilder builder){
        return new Team(builder);
    }

    public String getUrlName() {
        return urlName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
