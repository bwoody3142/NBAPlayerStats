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
        List<String> statList = Stat.createObject().getAllStats();
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        for (String stat : statList) {
            array.add(JsonPath.read(document, makeQuery(stat)));
        }
        return build();
    }
    private PlayerStats build() {
        return PlayerStats.withPoints(getFloat(Stat.PPG)).assists(getFloat(Stat.APG)).rebounds(getFloat(Stat.RPG))
                .turnovers(getFloat(Stat.TOPG)).steals(getFloat(Stat.SPG)).blocks(getFloat(Stat.BPG))
                .fieldGoalPercentage(getFloat(Stat.FGP)).freeThrowPercentage(getFloat(Stat.FTP))
                .andThreePointers(getFloat(Stat.TPM));
    }

    private String makeQuery(String stat){
        return "$..season[?(@.seasonYear==" + year + ")].total." + stat;
    }

    private float getFloat(Integer index){
        return Float.parseFloat(((JSONArray) array.get(index)).get(0).toString());
    }
}
