package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.InputStream;

public class PlayerParser {

    public static PlayerParserBuilder withStream(InputStream stream) {
        return new PlayerParserBuilder(stream);
    }

    public static class PlayerParserBuilder{
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

    public PlayerParser(PlayerParserBuilder builder) {
        this.stream = builder.stream;
        this.year = builder.year;
    }

    public PlayerStats parse(){
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        JSONArray pointsPerGameArray = JsonPath.read(document, "$..season[?(@.seasonYear==" + year + ")].total.ppg");
        JSONArray assistsPerGameArray = JsonPath.read(document, "$..season[?(@.seasonYear==" + year + ")].total.apg");
        JSONArray reboundsPerGameArray = JsonPath.read(document, "$..season[?(@.seasonYear==" + year + ")].total.rpg");
        float pointsPerGame = Float.parseFloat(pointsPerGameArray.get(0).toString());
        float assistsPerGame = Float.parseFloat(assistsPerGameArray.get(0).toString());
        float reboundsPerGame = Float.parseFloat(reboundsPerGameArray.get(0).toString());
        return PlayerStats.withPointsPerGame(pointsPerGame)
                .andAssistsPerGame(assistsPerGame).andReboundsPerGame(reboundsPerGame);
    }
}
