package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class PlayerParser {

    public double parse(InputStream stream){
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        JSONArray teamsArray = JsonPath.read(document, "$..teams.*");
        //noinspection unchecked
        Map<String, JSONObject> statsMap = (Map<String, JSONObject>) teamsArray.get(0);
        Object object = statsMap.get("ppg");
        return Double.parseDouble(object.toString());

    }
}
