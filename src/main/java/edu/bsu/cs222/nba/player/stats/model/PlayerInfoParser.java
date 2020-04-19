package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.InputStream;

public class PlayerInfoParser {

    public static PlayerInfoParserBuilder withStream(InputStream stream) {
        return new PlayerInfoParserBuilder(stream);
    }

    public static final class PlayerInfoParserBuilder{
        private final InputStream stream;
        private int personID;

        public PlayerInfoParserBuilder(InputStream stream){
            this.stream = stream;
        }
        public PlayerInfoParser andPersonID(int personID){
            this.personID = personID;
            return new PlayerInfoParser(this);
        }
    }

    private final InputStream stream;
    private final int personID;
    private final JSONArray array = new JSONArray();

    public PlayerInfoParser(PlayerInfoParserBuilder builder) {
        this.stream = builder.stream;
        this.personID = builder.personID;
    }

    public PlayerInfo parseForPlayerInfo(){
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        for (PlayerInfoData data : PlayerInfoData.values()) {
            array.add(JsonPath.read(document, makePlayerInfoQuery(data.getDataSourceKey())));
        }
        return buildPlayerInfo();
    }

    private PlayerInfo buildPlayerInfo() {
        String fullName = makeString(PlayerInfoData.FIRST_NAME.getIndex())
                + " " + makeString(PlayerInfoData.LAST_NAME.getIndex());
        return PlayerInfo.withName(fullName)
                .jersey(makeInteger(PlayerInfoData.JERSEY.getIndex()))
                .position(makeString(PlayerInfoData.POSITION.getIndex()))
                .yearsPro(makeInteger(PlayerInfoData.YEARS_PRO.getIndex()))
                .heightFeet(makeInteger(PlayerInfoData.HEIGHT_FEET.getIndex()))
                .heightInches(makeInteger(PlayerInfoData.HEIGHT_INCHES.getIndex()))
                .andWeight(makeInteger(PlayerInfoData.WEIGHT.getIndex()));
    }

    private String makePlayerInfoQuery(String data){
        return "$..standard[?(@.personId==" + personID + ")]." + data;
    }

    private int makeInteger(int index){
        return Integer.parseInt(((JSONArray) array.get(index)).get(0).toString());
    }

    private String makeString (int index){
        return ((JSONArray) array.get(index)).get(0).toString();
    }
}
