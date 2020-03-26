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
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        for (Stat stat : Stat.values()) {
            array.add(JsonPath.read(document, makeQuery(stat.getDataSourceKey())));
        }
        return build();
    }
    private PlayerStats build() {
        return PlayerStats.withPoints(getFloat(Stat.PPG.getIndex()))
                .assists(getFloat(Stat.APG.getIndex()))
                .rebounds(getFloat(Stat.RPG.getIndex()))
                .turnovers(getFloat(Stat.TOPG.getIndex()))
                .steals(getFloat(Stat.SPG.getIndex()))
                .blocks(getFloat(Stat.BPG.getIndex()))
                .fieldGoalPercentage(getFloat(Stat.FGP.getIndex()))
                .freeThrowPercentage(getFloat(Stat.FTP.getIndex()))
                .andThreePointers(getFloat(Stat.TPM.getIndex()));
    }

    private String makeQuery(String stat){
        return "$..season[?(@.seasonYear==" + year + ")].total." + stat;
    }

    private float getFloat(Integer index){
        return Float.parseFloat(((JSONArray) array.get(index)).get(0).toString());
    }
}
