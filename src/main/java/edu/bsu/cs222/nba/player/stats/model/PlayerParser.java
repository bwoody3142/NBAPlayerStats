package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.InputStream;

public class PlayerParser {

    public static PlayerParserBuilder withStream(InputStream stream) {
        return new PlayerParserBuilder(stream);
    }

    public static final class PlayerParserBuilder{
        private final InputStream stream;
        private Integer year;

        public PlayerParserBuilder(InputStream stream){
            this.stream = stream;
        }

        public PlayerParser andYear(Integer year) {
            this.year = year;
            return new PlayerParser(this);
        }
    }
    private final InputStream stream;
    private final Integer year;
    private final JSONArray array = new JSONArray();

    public PlayerParser(PlayerParserBuilder builder) {
        this.stream = builder.stream;
        this.year = builder.year;
    }

    public PlayerStats parseForSeasonStats(){
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        for (Statistic statistic : Statistic.values()) {
            array.add(JsonPath.read(document, makeSeasonStatsQuery(statistic.getDataSourceKey())));
        }
        return buildSeasonStats();
    }

    public PlayerStats parseForCareerStats(){
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        for (Statistic statistic : Statistic.values()) {
            if(!statistic.getDataSourceKey().equals("topg")){
                array.add(JsonPath.read(document, makeCareerStatsQuery(statistic.getDataSourceKey())));
            }
        }
        return buildCareerStats();
    }

    private PlayerStats buildSeasonStats() {
        return PlayerStats.withPoints(getFloat(Statistic.PPG.getIndex()))
                .assists(getFloat(Statistic.APG.getIndex()))
                .rebounds(getFloat(Statistic.RPG.getIndex()))
                .seasonTurnovers(getFloat(Statistic.SEASON_TOPG.getIndex()))
                .steals(getFloat(Statistic.SPG.getIndex()))
                .blocks(getFloat(Statistic.BPG.getIndex()))
                .fieldGoalPercentage(getFloat(Statistic.FGP.getIndex()))
                .freeThrowPercentage(getFloat(Statistic.FTP.getIndex()))
                .andThreePointers(getFloat(Statistic.TPM.getIndex()) / getFloat(Statistic.GAMES_PLAYED.getIndex()));
    }

    private PlayerStats buildCareerStats() {
        return PlayerStats.withPoints(getFloat(Statistic.PPG.getIndex()))
                .assists(getFloat(Statistic.APG.getIndex()))
                .rebounds(getFloat(Statistic.RPG.getIndex()))
                .careerTurnovers(getFloat(Statistic.CAREER_TURNOVERS.getIndex()) / getFloat(Statistic.GAMES_PLAYED.getIndex()))
                .steals(getFloat(Statistic.SPG.getIndex()))
                .blocks(getFloat(Statistic.BPG.getIndex()))
                .fieldGoalPercentage(getFloat(Statistic.FGP.getIndex()))
                .freeThrowPercentage(getFloat(Statistic.FTP.getIndex()))
                .andThreePointers(getFloat(Statistic.TPM.getIndex()) / getFloat(Statistic.GAMES_PLAYED.getIndex()));
    }

    private String makeSeasonStatsQuery(String stat){
        return "$..season[?(@.seasonYear==" + year + ")].total." + stat;
    }

    private String makeCareerStatsQuery(String stat){
        return "$..careerSummary." + stat;
    }

    private float getFloat(Integer index){
        return Float.parseFloat(((JSONArray) array.get(index)).get(0).toString());
    }
}
