package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.InputStream;

public class PlayerParser {

    public static PlayerParserBuilder isCareer(boolean isCareer) {
        return new PlayerParserBuilder(isCareer);
    }

    public static final class PlayerParserBuilder{
        private final boolean isCareer;
        private InputStream stream;
        private Integer year;

        public PlayerParserBuilder(boolean isCareer){
            this.isCareer = isCareer;
        }

        public PlayerParserBuilder withStream(InputStream stream){
            this.stream = stream;
            return this;
        }

        public PlayerParser andYear(Integer year) {
            this.year = year;
            return new PlayerParser(this);
        }
    }
    private final InputStream stream;
    private final Integer year;
    private final JSONArray array = new JSONArray();
    private final boolean isCareer;

    public PlayerParser(PlayerParserBuilder builder) {
        this.isCareer = builder.isCareer;
        this.stream = builder.stream;
        this.year = builder.year;
    }

    public PlayerStats parseForStats(){
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        for (Statistic statistic : Statistic.values()) {
            if (isCareer){
                if(!statistic.getDataSourceKey().equals("topg")){
                    array.add(JsonPath.read(document, makeStatsQuery(statistic.getDataSourceKey())));
                }
            } else {
                array.add(JsonPath.read(document, makeStatsQuery(statistic.getDataSourceKey())));
            }
        }
        return buildStats();
    }

    private PlayerStats buildStats() {
        float turnovers;
        if (!isCareer){
            turnovers = getFloat(Statistic.SEASON_TOPG.getIndex());
        } else {
            turnovers = getFloat(Statistic.CAREER_TURNOVERS.getIndex()) / getFloat(Statistic.GAMES_PLAYED.getIndex());
        }
        return PlayerStats.withPoints(getFloat(Statistic.PPG.getIndex()))
                .assists(getFloat(Statistic.APG.getIndex()))
                .rebounds(getFloat(Statistic.RPG.getIndex()))
                .turnovers(turnovers)
                .steals(getFloat(Statistic.SPG.getIndex()))
                .blocks(getFloat(Statistic.BPG.getIndex()))
                .fieldGoalPercentage(getFloat(Statistic.FGP.getIndex()))
                .freeThrowPercentage(getFloat(Statistic.FTP.getIndex()))
                .andThreePointers(getFloat(Statistic.TPM.getIndex()) / getFloat(Statistic.GAMES_PLAYED.getIndex()));
    }

    private String makeStatsQuery(String stat){
        if(isCareer){
            return "$..careerSummary." + stat;
        } else {
            return "$..season[?(@.seasonYear==" + year + ")].total." + stat;
        }
    }

    private float getFloat(Integer index){
        return Float.parseFloat(((JSONArray) array.get(index)).get(0).toString());
    }
}
