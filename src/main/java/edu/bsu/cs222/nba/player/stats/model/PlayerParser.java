package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.InputStream;

public class PlayerParser {
    private PlayerParser() {}

    public PlayerStats parse(InputStream stream){
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        JSONArray pointsPerGameArray = JsonPath.read(document, "$..season[?(@.seasonYear==2018)].total.ppg");
        JSONArray assistsPerGameArray = JsonPath.read(document, "$..season[?(@.seasonYear==2018)].total.apg");
        JSONArray reboundsPerGameArray = JsonPath.read(document, "$..season[?(@.seasonYear==2018)].total.rpg");
        float pointsPerGame = Float.parseFloat(pointsPerGameArray.get(0).toString());
        float assistsPerGame = Float.parseFloat(assistsPerGameArray.get(0).toString());
        float reboundsPerGame = Float.parseFloat(reboundsPerGameArray.get(0).toString());
        return PlayerStats.withPointsPerGame(pointsPerGame)
                .andAssistsPerGame(assistsPerGame).andReboundsPerGame(reboundsPerGame);
    }
    public static PlayerParser createEmptyPlayerParserObject(){
        return new PlayerParser();
    }
}
