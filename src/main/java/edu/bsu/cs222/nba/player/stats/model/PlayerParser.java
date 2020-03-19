package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.InputStream;
import java.util.List;

public class PlayerParser {

    public static PlayerParserBuilder withStream(InputStream stream) {
        return new PlayerParserBuilder(stream);
    }

    public static final class PlayerParserBuilder{
        private InputStream stream;
        private Integer year;

        public PlayerParserBuilder(InputStream stream){
            this.stream = stream;
        }

        public PlayerParser andYear(Integer year) {
            this.year = year;
            return new PlayerParser(this);
        }
    }
    private InputStream stream;
    private Integer year;
    private JSONArray array = new JSONArray();

    public PlayerParser(PlayerParserBuilder builder) {
        this.stream = builder.stream;
        this.year = builder.year;
    }

    public PlayerStats parse(){
        List<String> statList = Stat.create().getAllStats();
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        for (String stat : statList) {
            array.add(JsonPath.read(document, makeQuery(stat)));
        }
        return PlayerStats.withPointsPerGame(getFloat(Stat.PPG)).andAssistsPerGame(getFloat(Stat.APG))
                .andReboundsPerGame(getFloat(Stat.RPG)).andTurnOversPerGame(getFloat(Stat.TOPG)).andStealsPerGame(getFloat(Stat.SPG))
                .andBlocksPerGame(getFloat(Stat.BPG)).andFieldGoalPercentage(getFloat(Stat.FGP))
                .andFreeThrowPercentage(getFloat(Stat.FTP)).andThreePointPercentage(getFloat(Stat.TPP));
    }

    private String makeQuery(String stat){
        return "$..season[?(@.seasonYear==" + year + ")].total." + stat;
    }

    private float getFloat(Integer index){
        return Float.parseFloat(((JSONArray) array.get(index)).get(0).toString());
    }
}
