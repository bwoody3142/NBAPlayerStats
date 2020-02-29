package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ListOfPlayers {

    public Map<String, String> createFullListOfPlayers(String year) throws Exception {
        URLFactory urlFactory = URLFactory.createEmptyURLFactory();
        InputStream stream = urlFactory.createPlayerListUrl(year);
        Object playerListDoc = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        Map<String, String> playerMap = new HashMap<>();
        JSONArray array = JsonPath.read(playerListDoc, "$..standard.*");
        for (Object unCastedMap : array) {
            //Had to suppress due to the api
            @SuppressWarnings("unchecked") Map<String, JSONObject> map = (Map<String, JSONObject>) unCastedMap;
            Object firstName = map.get("firstName");
            Object lastName = map.get("lastName");
            String fullName = firstName.toString() + " " + lastName.toString();
            Object object = map.get("personId");
            String playerID = object.toString();
            playerMap.put(fullName, playerID);
        }
        return playerMap;
    }

    public static ListOfPlayers createEmptyListOfPlayers(){
        return new ListOfPlayers();
    }

}



